package swm_nm.morandi.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import swm_nm.morandi.auth.security.SecurityUtils;
import swm_nm.morandi.member.dto.GraphDto;
import swm_nm.morandi.member.dto.GrassDto;
import swm_nm.morandi.member.dto.MemberDto;
import swm_nm.morandi.member.dto.RegisterInfoDto;
import swm_nm.morandi.member.service.AttemptProblemService;
import swm_nm.morandi.member.service.MemberService;
import swm_nm.morandi.test.dto.TestRatingDto;
import swm_nm.morandi.test.dto.TestRecordDto;
import swm_nm.morandi.test.service.TestService;

import java.util.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AttemptProblemService attemptProblemService;
    private final TestService testService;

    @PostMapping("/register-info")
    public ResponseEntity<RegisterInfoDto> memberInitialize(@RequestBody RegisterInfoDto registerInfoDto)
    {
        return ResponseEntity.ok(memberService.memberInitialize(registerInfoDto));

    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Map<String, Object>> memberInfo(@PathVariable Long memberId) {
        List<GrassDto> grassDtos =
                attemptProblemService.getGrassDtosByMemberId(memberId);
        List<TestRecordDto> testRecordDtos
                = testService.getTestRecordDtosByMemberId(memberId);
        List<GraphDto> graphDtos =
                attemptProblemService.getGraphDtosByMemberId(memberId);
        List<TestRatingDto> testRatingDtos
                = testService.getTestRatingDtosByMemberId(memberId);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("grassDto", grassDtos);
        responseData.put("testRecordDto", testRecordDtos);
        responseData.put("graphDto", graphDtos);
        responseData.put("testRatingDto", testRatingDtos);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity editProfile(@PathVariable Long memberId,
                                      @RequestBody MemberDto memberDto) {
        memberService.editProfile(memberId, memberDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{memberId}/test-info")
    public ResponseEntity<List<TestRecordDto>> testRecordInfo(@PathVariable Long memberId) {
        List<TestRecordDto> testRecordDtos
                = testService.getTestRecordDtosByMemberId(memberId);

        return new ResponseEntity<>(testRecordDtos, HttpStatus.OK);
    }
}
