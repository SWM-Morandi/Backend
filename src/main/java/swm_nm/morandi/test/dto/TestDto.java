package swm_nm.morandi.test.dto;

import lombok.*;
import swm_nm.morandi.problem.dto.DifficultyLevel;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestDto {
    private Long testId;
    private LocalDateTime testDate;
    private Long testTime;
    private Integer problemCount;
    private DifficultyLevel startDifficulty;
    private DifficultyLevel endDifficulty;
    private String testTypename;
    private Long testRating;
}
