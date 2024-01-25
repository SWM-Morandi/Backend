package swm_nm.morandi.domain.auth.service;

import swm_nm.morandi.domain.auth.response.UserDto;

public interface OAuthService {
    String getType();
    String getAccessToken(String authorizationCode, Boolean isDev);
    UserDto getMemberInfo(String accessToken);

//    String getAccessTokenDev(String authorization_code);
//
//    GoogleUserDto getMemberInfoDev(String accessToken);
}
