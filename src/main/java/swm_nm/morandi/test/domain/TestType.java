package swm_nm.morandi.test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swm_nm.morandi.problem.domain.Algorithm;
import swm_nm.morandi.problem.dto.DifficultyLevel;
import swm_nm.morandi.problem.dto.DifficultyRange;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testTypeId;
    private String testTypename;
    private Long testTime;
    private Integer problemCount;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel startDifficulty;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel endDifficulty;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<DifficultyRange> difficultyRanges;

    private Double averageCorrectAnswerRate;

    private Integer numberOfTestTrial;


    public void updateAverageCorrectAnswerRate(Double newTrialCorrectAnswerRate){
        this.averageCorrectAnswerRate =
                (((this.averageCorrectAnswerRate * this.numberOfTestTrial) + newTrialCorrectAnswerRate)
                        /(++this.numberOfTestTrial));

    }
}
