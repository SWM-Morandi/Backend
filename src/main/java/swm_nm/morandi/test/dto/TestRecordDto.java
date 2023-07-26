package swm_nm.morandi.test.dto;

import lombok.*;
import swm_nm.morandi.problem.dto.DifficultyLevel;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestRecordDto {
    private LocalDateTime testDate;
    private Integer testTime;
    private Integer problemCount;
    private DifficultyLevel startDifficulty;
    private DifficultyLevel endDifficulty;
    private String testTypename;
    private Long testRating;
    private Map<Integer, Boolean> solvedInfo = new HashMap<>();
}
