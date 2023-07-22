package swm_nm.morandi.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swm_nm.morandi.auth.response.GoogleUserDto;
import swm_nm.morandi.auth.response.TokenDto;
import swm_nm.morandi.member.service.MemberService;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final OAuthServiceFactory oAuthServiceFactory;
    private final MemberService memberService;

    public TokenDto login(String type, String authorization_code){

        OAuthService oAuthService = oAuthServiceFactory.getServiceByType(type);
        if (oAuthService == null) {
            throw new IllegalArgumentException("지원되지 않는 OAuth provider 입니다 : " + type);
        }
        String accessToken = oAuthService.getAccessToken(authorization_code);

        GoogleUserDto googleUserDto = oAuthService.getMemberInfo(accessToken);


        return memberService.registerMember(googleUserDto);


    }
    public TokenDto OAuthJoinOrLogin(String type, String accessToken){
        OAuthService oAuthService = oAuthServiceFactory.getServiceByType(type);
        if (oAuthService == null) {
            throw new IllegalArgumentException("지원되지 않는 OAuth provider 입니다 : " + type);
        }
        System.out.println("oAuthService.getClass() = " + oAuthService.getClass());
        System.out.println("oAuthService = " + oAuthService);
        GoogleUserDto googleUserDto = oAuthService.getMemberInfo(accessToken);
        
        return memberService.registerMember(googleUserDto);


    }
}
