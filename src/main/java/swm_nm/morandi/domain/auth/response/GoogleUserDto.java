package swm_nm.morandi.domain.auth.response;


import lombok.Getter;
import lombok.Setter;
import swm_nm.morandi.domain.member.entity.SocialType;

@Getter
@Setter
public class GoogleUserDto {

    private String id;
    private String email;
    private String verified_email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;
    private SocialType type;
    }