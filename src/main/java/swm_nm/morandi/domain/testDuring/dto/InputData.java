package swm_nm.morandi.domain.testDuring.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InputData {
    private Long testId;
    private Long problemNumber;
    private String language;
    private String code;
    private String input;
}
