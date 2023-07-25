package swm_nm.morandi.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String nickname;
    private String bojId;
    private byte[] thumbPhoto;
}
