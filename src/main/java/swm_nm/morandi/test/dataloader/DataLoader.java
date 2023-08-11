package swm_nm.morandi.test.dataloader;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import swm_nm.morandi.test.domain.TestType;
import swm_nm.morandi.problem.dto.DifficultyLevel;
import swm_nm.morandi.problem.dto.DifficultyRange;
import swm_nm.morandi.test.repository.TestTypeRepository;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final TestTypeRepository testTypeRepository;
    @Override
    public void run(String... args) {
        List<TestType> testTypes = testTypeRepository.findAll();
        if (testTypes.size() == 0)
            insertTestTypeData();
    }
    private void insertTestTypeData() {
        // PK 1번 : 코딩테스트 대비 초급 (Small)
        List<DifficultyRange> difficultyRanges1 = new ArrayList<>();
        difficultyRanges1.add(new DifficultyRange(DifficultyLevel.B3, DifficultyLevel.B2));
        difficultyRanges1.add(new DifficultyRange(DifficultyLevel.B1, DifficultyLevel.S5));
        difficultyRanges1.add(new DifficultyRange(DifficultyLevel.S4, DifficultyLevel.S3));

        TestType testType1 = TestType.builder()
                .testTypename("코딩테스트 대비 초급 (Small)")
                .testTime(60L)
                .problemCount(3)
                .startDifficulty(DifficultyLevel.B5)
                .endDifficulty(DifficultyLevel.S3)
                .difficultyRanges(difficultyRanges1)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType1);

        // PK 2번 : 코딩테스트 대비 중급 (Small)
        List<DifficultyRange> difficultyRanges2 = new ArrayList<>();
        difficultyRanges2.add(new DifficultyRange(DifficultyLevel.B2, DifficultyLevel.S5));
        difficultyRanges2.add(new DifficultyRange(DifficultyLevel.S4, DifficultyLevel.S2));
        difficultyRanges2.add(new DifficultyRange(DifficultyLevel.S1, DifficultyLevel.G4));

        TestType testType2 = TestType.builder()
                .testTypename("코딩테스트 대비 중급 (Small)")
                .testTime(60L)
                .problemCount(3)
                .startDifficulty(DifficultyLevel.B2)
                .endDifficulty(DifficultyLevel.G4)
                .difficultyRanges(difficultyRanges2)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType2);

        // PK 3번 : 코딩테스트 대비 고급 (Small)
        List<DifficultyRange> difficultyRanges3 = new ArrayList<>();
        difficultyRanges3.add(new DifficultyRange(DifficultyLevel.S4, DifficultyLevel.S1));
        difficultyRanges3.add(new DifficultyRange(DifficultyLevel.G5, DifficultyLevel.G3));
        difficultyRanges3.add(new DifficultyRange(DifficultyLevel.G2, DifficultyLevel.P5));

        TestType testType3 = TestType.builder()
                .testTypename("코딩테스트 대비 고급 (Small)")
                .testTime(60L)
                .problemCount(3)
                .startDifficulty(DifficultyLevel.S4)
                .endDifficulty(DifficultyLevel.P5)
                .difficultyRanges(difficultyRanges3)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType3);

        // PK 4번 : 코딩테스트 대비 초급 (Large)
        List<DifficultyRange> difficultyRanges4 = new ArrayList<>();
        difficultyRanges4.add(new DifficultyRange(DifficultyLevel.B4, DifficultyLevel.B4));
        difficultyRanges4.add(new DifficultyRange(DifficultyLevel.B3, DifficultyLevel.B3));
        difficultyRanges4.add(new DifficultyRange(DifficultyLevel.B2, DifficultyLevel.S5));
        difficultyRanges4.add(new DifficultyRange(DifficultyLevel.S4, DifficultyLevel.S4));
        difficultyRanges4.add(new DifficultyRange(DifficultyLevel.S3, DifficultyLevel.S3));

        TestType testType4 = TestType.builder()
                .testTypename("코딩테스트 대비 초급 (Large)")
                .testTime(120L)
                .problemCount(5)
                .startDifficulty(DifficultyLevel.B5)
                .endDifficulty(DifficultyLevel.S3)
                .difficultyRanges(difficultyRanges4)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType4);

        // PK 5번 : 코딩테스트 대비 중급 (Large)
        List<DifficultyRange> difficultyRanges5 = new ArrayList<>();
        difficultyRanges5.add(new DifficultyRange(DifficultyLevel.B2, DifficultyLevel.S5));
        difficultyRanges5.add(new DifficultyRange(DifficultyLevel.S4, DifficultyLevel.S3));
        difficultyRanges5.add(new DifficultyRange(DifficultyLevel.S2, DifficultyLevel.S1));
        difficultyRanges5.add(new DifficultyRange(DifficultyLevel.G5, DifficultyLevel.G5));
        difficultyRanges5.add(new DifficultyRange(DifficultyLevel.G4, DifficultyLevel.G4));

        TestType testType5 = TestType.builder()
                .testTypename("코딩테스트 대비 중급 (Large)")
                .testTime(120L)
                .problemCount(5)
                .startDifficulty(DifficultyLevel.B2)
                .endDifficulty(DifficultyLevel.G4)
                .difficultyRanges(difficultyRanges5)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType5);

        // PK 6번 : 코딩테스트 대비 고급 (Large)
        List<DifficultyRange> difficultyRanges6 = new ArrayList<>();
        difficultyRanges6.add(new DifficultyRange(DifficultyLevel.S4, DifficultyLevel.S2));
        difficultyRanges6.add(new DifficultyRange(DifficultyLevel.S1, DifficultyLevel.G5));
        difficultyRanges6.add(new DifficultyRange(DifficultyLevel.G4, DifficultyLevel.G3));
        difficultyRanges6.add(new DifficultyRange(DifficultyLevel.G2, DifficultyLevel.G1));
        difficultyRanges6.add(new DifficultyRange(DifficultyLevel.G1, DifficultyLevel.P5));

        TestType testType6 = TestType.builder()
                .testTypename("코딩테스트 대비 고급 (Large)")
                .testTime(120L)
                .problemCount(5)
                .startDifficulty(DifficultyLevel.S4)
                .endDifficulty(DifficultyLevel.P5)
                .difficultyRanges(difficultyRanges6)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType6);

        // 삼성
        List<DifficultyRange> difficultyRanges7 = new ArrayList<>();
        difficultyRanges7.add(new DifficultyRange(DifficultyLevel.S1, DifficultyLevel.G5));
        difficultyRanges7.add(new DifficultyRange(DifficultyLevel.G4, DifficultyLevel.G1));

        TestType testType7 = TestType.builder()
                .testTypename("삼성")
                .testTime(180L)
                .problemCount(2)
                .startDifficulty(DifficultyLevel.S1)
                .endDifficulty(DifficultyLevel.G1)
                .difficultyRanges(difficultyRanges7)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();
        testTypeRepository.save(testType7);

        // 네이버
        List<DifficultyRange> difficultyRanges8 = new ArrayList<>();
        difficultyRanges8.add(new DifficultyRange(DifficultyLevel.S2, DifficultyLevel.G5));
        difficultyRanges8.add(new DifficultyRange(DifficultyLevel.G4, DifficultyLevel.G4));
        difficultyRanges8.add(new DifficultyRange(DifficultyLevel.G3, DifficultyLevel.G2));
        difficultyRanges8.add(new DifficultyRange(DifficultyLevel.G1, DifficultyLevel.P5));

        TestType testType8 = TestType.builder()
                .testTypename("네이버")
                .testTime(120L)
                .problemCount(4)
                .startDifficulty(DifficultyLevel.S2)
                .endDifficulty(DifficultyLevel.P5)
                .difficultyRanges(difficultyRanges8)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType8);

        // 카카오
        List<DifficultyRange> difficultyRanges9 = new ArrayList<>();
        difficultyRanges9.add(new DifficultyRange(DifficultyLevel.S5, DifficultyLevel.S4));
        difficultyRanges9.add(new DifficultyRange(DifficultyLevel.S3, DifficultyLevel.S2));
        difficultyRanges9.add(new DifficultyRange(DifficultyLevel.S1, DifficultyLevel.G5));
        difficultyRanges9.add(new DifficultyRange(DifficultyLevel.G4, DifficultyLevel.G4));
        difficultyRanges9.add(new DifficultyRange(DifficultyLevel.G3, DifficultyLevel.G3));
        difficultyRanges9.add(new DifficultyRange(DifficultyLevel.G2, DifficultyLevel.G1));
        difficultyRanges9.add(new DifficultyRange(DifficultyLevel.G1, DifficultyLevel.P5));

        TestType testType9 = TestType.builder()
                .testTypename("카카오")
                .testTime(300L)
                .problemCount(7)
                .startDifficulty(DifficultyLevel.S3)
                .endDifficulty(DifficultyLevel.P5)
                .difficultyRanges(difficultyRanges9)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType9);

        // 라인
        List<DifficultyRange> difficultyRanges10 = new ArrayList<>();
        difficultyRanges10.add(new DifficultyRange(DifficultyLevel.S4, DifficultyLevel.S3));
        difficultyRanges10.add(new DifficultyRange(DifficultyLevel.S2, DifficultyLevel.S1));
        difficultyRanges10.add(new DifficultyRange(DifficultyLevel.G5, DifficultyLevel.G5));
        difficultyRanges10.add(new DifficultyRange(DifficultyLevel.G4, DifficultyLevel.G4));
        difficultyRanges10.add(new DifficultyRange(DifficultyLevel.G3, DifficultyLevel.G2));
        difficultyRanges10.add(new DifficultyRange(DifficultyLevel.G1, DifficultyLevel.G1));

        TestType testType10 = TestType.builder()
                .testTypename("라인")
                .testTime(180L)
                .problemCount(6)
                .startDifficulty(DifficultyLevel.S4)
                .endDifficulty(DifficultyLevel.G1)
                .difficultyRanges(difficultyRanges10)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType10);

        // 쿠팡
        List<DifficultyRange> difficultyRanges11 = new ArrayList<>();
        difficultyRanges11.add(new DifficultyRange(DifficultyLevel.S5, DifficultyLevel.S3));
        difficultyRanges11.add(new DifficultyRange(DifficultyLevel.S2, DifficultyLevel.S1));
        difficultyRanges11.add(new DifficultyRange(DifficultyLevel.G5, DifficultyLevel.G5));
        difficultyRanges11.add(new DifficultyRange(DifficultyLevel.G4, DifficultyLevel.G3));
        TestType testType11 = TestType.builder()
                .testTypename("쿠팡")
                .testTime(180L)
                .problemCount(4)
                .startDifficulty(DifficultyLevel.S5)
                .endDifficulty(DifficultyLevel.G3)
                .difficultyRanges(difficultyRanges11)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType11);

        // 우아한형제들
        List<DifficultyRange> difficultyRanges12 = new ArrayList<>();
        difficultyRanges12.add(new DifficultyRange(DifficultyLevel.S4, DifficultyLevel.S3));
        difficultyRanges12.add(new DifficultyRange(DifficultyLevel.S2, DifficultyLevel.G5));
        difficultyRanges12.add(new DifficultyRange(DifficultyLevel.G4, DifficultyLevel.G4));
        difficultyRanges12.add(new DifficultyRange(DifficultyLevel.G3, DifficultyLevel.G3));
        TestType testType12 = TestType.builder()
                .testTypename("우아한형제들")
                .testTime(180L)
                .problemCount(4)
                .startDifficulty(DifficultyLevel.S4)
                .endDifficulty(DifficultyLevel.G3)
                .difficultyRanges(difficultyRanges12)
                .numberOfTestTrial(0)
                .averageCorrectAnswerRate(0.0)
                .build();

        testTypeRepository.save(testType12);
    }
}
