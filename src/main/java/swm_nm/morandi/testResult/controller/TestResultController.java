package swm_nm.morandi.testResult.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swm_nm.morandi.testResult.request.AttemptProblemDto;
import swm_nm.morandi.testResult.service.AttemptProblemService;

import java.util.List;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class TestResultController {


    private final AttemptProblemService attemptProblemService;

    @PostMapping("/{test-id}/attempt-problems")
    public ResponseEntity<List<AttemptProblemDto>> saveAttemptedProblemResult
            (@PathVariable("test-id") Long testId, @RequestBody List<AttemptProblemDto> attemptProblemDtos) {
        attemptProblemService.saveAttemptedProblemResult(testId, attemptProblemDtos);
        return new ResponseEntity<>(attemptProblemDtos, HttpStatus.OK);
    }

}
