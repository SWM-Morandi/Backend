package swm_nm.morandi.testResult.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swm_nm.morandi.auth.security.SecurityUtils;
import swm_nm.morandi.member.domain.Member;
import swm_nm.morandi.testResult.request.AttemptProblemDto;
import swm_nm.morandi.member.repository.MemberRepository;
import swm_nm.morandi.problem.domain.Algorithm;
import swm_nm.morandi.problem.domain.AlgorithmProblemList;
import swm_nm.morandi.problem.domain.Problem;
import swm_nm.morandi.problem.repository.ProblemRepository;
import swm_nm.morandi.test.domain.Test;
import swm_nm.morandi.test.repository.TestRepository;
import swm_nm.morandi.testResult.entity.AttemptProblem;
import swm_nm.morandi.member.dto.GraphDto;
import swm_nm.morandi.member.dto.GrassDto;
import swm_nm.morandi.problem.repository.AlgorithmProblemListRepository;
import swm_nm.morandi.problem.repository.AlgorithmRepository;
import swm_nm.morandi.member.repository.AttemptProblemRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttemptProblemService {

    private final AttemptProblemRepository attemptProblemRepository;

    private final AlgorithmProblemListRepository algorithmProblemListRepository;

    private final AlgorithmRepository algorithmRepository;

    private final MemberRepository memberRepository;

    private final TestRepository testRepository;

    private final ProblemRepository problemRepository;

    public void saveAttemptedProblemResult(Long testId, List<AttemptProblemDto> attemptProblemDtos) {
        Long memberId = SecurityUtils.getCurrentUserId();
        //Long memberId = 1L;
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다."));
        Test test = testRepository.findById(testId).orElseThrow(()-> new RuntimeException("테스트를 찾을 수 없습니다."));

        List<AttemptProblem> attemptProblems = attemptProblemDtos.stream().map(attemptProblemDto -> {
            Problem problem = problemRepository.findById(attemptProblemDto.getProblemId()).orElseThrow(()-> new RuntimeException("문제를 찾을 수 없습니다."));
            return AttemptProblem.builder()
                    .isSolved(attemptProblemDto.getIsSolved())
                    .testDate(attemptProblemDto.getTestDate())
                    .member(member)
                    .test(test)
                    .problem(problem)
                    .build();

        }).collect(Collectors.toList());
        attemptProblemRepository.saveAll(attemptProblems);

    }
    public List<GrassDto> getGrassDtosByMemberId(Long memberId) {
        List<AttemptProblem> attemptProblems
                = attemptProblemRepository.findAllByMember_MemberId(memberId);
        Map<LocalDate, Integer> grassMap = new HashMap<>();
        for (AttemptProblem attemptProblem : attemptProblems) {
            LocalDate testDate = attemptProblem.getTestDate();
            if (attemptProblem.getIsSolved()) {
                Integer currentValue = grassMap.getOrDefault(testDate, 0);
                Integer newValue = currentValue + 1;
                grassMap.put(testDate, newValue);
            }
        }

        List<GrassDto> grassDtos = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : grassMap.entrySet()) {
            LocalDate testDate = entry.getKey();
            Integer solvedCount = entry.getValue();
            GrassDto grassDto = GrassDto.builder()
                    .testDate(testDate)
                    .solvedCount(solvedCount)
                    .build();
            grassDtos.add(grassDto);
        }
        return grassDtos;
    }

    public List<GraphDto> getGraphDtosByMemberId(Long memberId) {
        Map<String, Integer> TotalCount = new HashMap<>();
        Map<String, Integer> Count = new HashMap<>();
        List<Algorithm> algorithms = algorithmRepository.findAll();

        for (Algorithm algorithm : algorithms) {
            String algorithmName = algorithm.getAlgorithmName();
            TotalCount.put(algorithmName, 0);
            Count.put(algorithmName, 0);
        }

        List<AttemptProblem> attemptProblems
                = attemptProblemRepository.findAllByMember_MemberId(memberId);
        for (AttemptProblem attemptProblem : attemptProblems) {
            Long problemId = attemptProblem.getProblem().getProblemId();
            List<AlgorithmProblemList> algorithmProblemLists =
                    algorithmProblemListRepository.findByProblem_ProblemId(problemId);
            for (AlgorithmProblemList algorithmProblemList : algorithmProblemLists) {
                String algorithmName = algorithmProblemList.getAlgorithm().getAlgorithmName();
                int currentTotalCount = TotalCount.getOrDefault(algorithmName, 0);
                int currentCount = Count.getOrDefault(algorithmName, 0);
                TotalCount.put(algorithmName, currentTotalCount + 1);
                if (attemptProblem.getIsSolved())
                    Count.put(algorithmName, currentCount + 1);
            }
        }

        List<GraphDto> graphDtos = new ArrayList<>();

        for (Algorithm algorithm : algorithms) {
            String algorithmName = algorithm.getAlgorithmName();
            Double solvedRate = (double) Count.get(algorithmName) / (double) TotalCount.get(algorithmName) * 100;
            GraphDto graphDto = GraphDto.builder()
                    .algorithmName(algorithmName)
                    .solvedRate(solvedRate)
                    .build();

            graphDtos.add(graphDto);
        }

        return graphDtos;
    }
}
