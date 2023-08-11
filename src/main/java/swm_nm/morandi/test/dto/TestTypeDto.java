package swm_nm.morandi.test.dto;

import lombok.*;
import swm_nm.morandi.problem.dto.DifficultyLevel;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestTypeDto {
    private Long testTypeId;
    private String testTypename;
    private Long testTime;
    private Integer problemCount;
    private DifficultyLevel startDifficulty;
    private DifficultyLevel endDifficulty;
    private Double averageCorrectAnswerRate;
    private Integer numberOfTestTrial;
}
