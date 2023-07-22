package swm_nm.morandi.auth.service;

import org.springframework.stereotype.Service;
import swm_nm.morandi.auth.response.GoogleUserDto;

//Test

@Service
public class GithubService implements OAuthService {
    @Override
    public String getType() {
        return "github";
    }

    @Override
    public String getAccessToken(String authorization_code) {
        System.out.println("GithubService.getAccessToken");
        return null;
    }

    @Override
    public GoogleUserDto getMemberInfo(String accessToken) {
        System.out.println("GithubService.getMemberInfo");
        return null;
    }
}
