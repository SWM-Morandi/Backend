package swm_nm.morandi.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swm_nm.morandi.auth.security.SecurityUtils;
import swm_nm.morandi.exception.MorandiException;
import swm_nm.morandi.exception.errorcode.MemberErrorCode;
import swm_nm.morandi.exception.errorcode.ProblemErrorCode;
import swm_nm.morandi.exception.errorcode.TestErrorCode;
import swm_nm.morandi.exception.errorcode.TestTypeErrorCode;
import swm_nm.morandi.member.domain.Member;
import swm_nm.morandi.member.dto.CurrentRatingDto;
import swm_nm.morandi.member.repository.MemberRepository;
import swm_nm.morandi.member.service.MemberService;
import swm_nm.morandi.problem.domain.Problem;
import swm_nm.morandi.problem.dto.BojProblem;
import swm_nm.morandi.test.domain.TestType;
import swm_nm.morandi.test.dto.*;
import swm_nm.morandi.test.repository.TestTypeRepository;
import swm_nm.morandi.testResult.entity.AttemptProblem;
import swm_nm.morandi.test.domain.Test;
import swm_nm.morandi.test.mapper.TestRecordMapper;
import swm_nm.morandi.member.repository.AttemptProblemRepository;
import swm_nm.morandi.test.repository.TestRepository;
import swm_nm.morandi.testResult.request.AttemptProblemDto;
import swm_nm.morandi.testResult.service.AttemptProblemService;
import swm_nm.morandi.testResult.service.TestResultService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final MemberRepository memberRepository;

    private final TestRepository testRepository;

    private final TestTypeRepository testTypeRepository;

    private final AttemptProblemRepository attemptProblemRepository;

    private final MemberService memberService;

    private final TestTypeService testTypeService;

    private final TestResultService testResultService;
    public TestStartResponseDto getTestStartsData(Long testTypeId) {
        Long memberId = SecurityUtils.getCurrentMemberId();
        Long testId = startTestByTestTypeId(testTypeId, memberId);
        String bojId = memberService.getBojId(memberId);

        List<BojProblem> bojProblems = new ArrayList<>();
        testTypeService.getProblemsByTestType(testTypeId, bojProblems);
        testTypeService.getProblemsByApi(testTypeId, bojId, bojProblems);

        List<Long> attemptProblemIds = testResultService.saveAttemptProblems(memberId, testId, bojProblems);

        TestStartResponseDto testStartResponseDto
                = TestStartResponseDto.builder()
                .testId(testId)
                .attemptProblemIds(attemptProblemIds)
                .bojProblems(bojProblems)
                .build();

        return testStartResponseDto;
    }


    @Transactional
    public Long startTestByTestTypeId(Long testTypeId, Long memberId) {
        TestType testType = testTypeRepository.findById(testTypeId).orElseThrow(()-> new MorandiException(TestTypeErrorCode.TEST_TYPE_NOT_FOUND));
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new MorandiException(MemberErrorCode.MEMBER_NOT_FOUND));

        Test test = Test.builder()
                .testDate(LocalDateTime.now())
                .testTime(testType.getTestTime())
                .problemCount(testType.getProblemCount())
                .startDifficulty(testType.getStartDifficulty())
                .endDifficulty(testType.getEndDifficulty())
                .testTypename(testType.getTestTypename())
                .testRating(null)
                .testStatus(TestStatus.IN_PROGRESS)
                .member(member)
                .build();

        testRepository.save(test);
        return test.getTestId();
    }
    public List<TestRecordDto> getTestRecordDtosLatest() {
        Long memberId = SecurityUtils.getCurrentMemberId();
        Pageable pageable = PageRequest.of(0, 5);
        List<Test> recentTests = testRepository.findAllByMember_MemberIdOrderByTestDateDesc(memberId, pageable);
        List<TestRecordDto> testRecordDtos = recentTests.stream().map(TestRecordMapper::convertToDto).collect(Collectors.toList());
        return testRecordDtos;
    }

    public CurrentRatingDto getCurrentRatingDto() {
        Long memberId = SecurityUtils.getCurrentMemberId();
        Pageable pageable = PageRequest.of(0, 1);
        List<Test> recentTests = testRepository.findAllByMember_MemberIdOrderByTestDateDesc(memberId, pageable);
        CurrentRatingDto currentRatingDto = new CurrentRatingDto();
        if (recentTests.isEmpty()) currentRatingDto.setRating(1000L);
        else currentRatingDto.setRating(recentTests.get(0).getTestRating());
        if (currentRatingDto.getRating() == null) currentRatingDto.setRating(1000L);
        return currentRatingDto;
    }

    public TestRecordDto getTestRecordDtoByTestId(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(()-> new MorandiException(TestErrorCode.TEST_NOT_FOUND));
        TestRecordDto testRecordDto = TestRecordMapper.convertToDto(test);
        List<AttemptProblem> attemptProblems
                = attemptProblemRepository.findAllByTest_TestId(testId);
        if (!attemptProblems.isEmpty()) {
            long index = 1;
            for (AttemptProblem attemptProblem : attemptProblems) {
                AttemptProblemDto attemptProblemDto =
                        AttemptProblemDto.builder()
                                .testProblemId(index++)
                                .bojProblemId(attemptProblem.getProblem().getBojProblemId())
                                .isSolved(attemptProblem.getIsSolved())
                                .executionTime(attemptProblem.getExecutionTime())
                                .build();
                testRecordDto.getAttemptProblemDto().add(attemptProblemDto);
            }
        }
        return testRecordDto;
    }

    public List<TestRatingDto> getTestRatingDtos() {
        Long memberId = SecurityUtils.getCurrentMemberId();
        List<Test> tests = testRepository.findAllByMember_MemberId(memberId);
        List<TestRatingDto> testRatingDtos = new ArrayList<>();
        tests.stream()
                .filter(test -> test.getTestStatus() != TestStatus.IN_PROGRESS)
                .forEach(test -> {
            LocalDate testDate = LocalDate.from(test.getTestDate());
            String testTypename = test.getTestTypename();
            Long testRating = test.getTestRating();
            Long testId = test.getTestId();
            TestRatingDto testRatingDto = TestRatingDto.builder()
                    .testId(testId)
                    .testTypeName(testTypename)
                    .testDate(testDate)
                    .testRating(testRating)
                    .build();
            testRatingDtos.add(testRatingDto);
        });

        if (testRatingDtos.size() == 0) {
            TestRatingDto testRatingDto = TestRatingDto.builder()
                    .testId(null)
                    .testTypeName(null)
                    .testDate(null)
                    .testRating(null)
                    .build();
            testRatingDtos.add(testRatingDto);
        }

        return testRatingDtos;
    }

}
