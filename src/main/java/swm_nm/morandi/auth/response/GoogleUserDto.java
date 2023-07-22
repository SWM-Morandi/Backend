package swm_nm.morandi.auth.response;


import lombok.Getter;
import lombok.Setter;
import swm_nm.morandi.member.domain.SocialType;

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