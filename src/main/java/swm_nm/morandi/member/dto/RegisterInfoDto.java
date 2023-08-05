package swm_nm.morandi.member.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInfoDto {
    @NotBlank
    private String bojId;
}
