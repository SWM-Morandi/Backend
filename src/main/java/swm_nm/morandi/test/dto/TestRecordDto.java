package swm_nm.morandi.test.dto;

import lombok.*;
import swm_nm.morandi.problem.dto.DifficultyLevel;
import swm_nm.morandi.testResult.request.AttemptProblemDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TestRecordDto {
    private LocalDateTime testDate;
    private Long testTime;
    private Integer problemCount;
    private DifficultyLevel startDifficulty;
    private DifficultyLevel endDifficulty;
    private String testTypename;
    private Long testRating;
    private List<AttemptProblemDto> attemptProblemDto;
}
