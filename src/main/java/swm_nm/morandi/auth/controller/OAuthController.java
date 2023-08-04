package swm_nm.morandi.auth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swm_nm.morandi.auth.constants.SecurityConstants;
import swm_nm.morandi.auth.response.TokenDto;
import swm_nm.morandi.auth.service.LoginService;
import swm_nm.morandi.logging.Logging;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Logging
@RestController
@RequestMapping("/oauths")
@RequiredArgsConstructor
public class OAuthController {


    private final LoginService loginService;

    //Authorization Code도 모두 가져오는 경우에 사용하는 callback api
    @GetMapping("/{type}/callback")
    public ResponseEntity<String> googleLogin(@PathVariable String type, @RequestParam String code, HttpServletResponse response) {
        String accessToken = loginService.login(type, code, SecurityConstants.FOR_SERVICE).getAccessToken();

        Cookie jwtCookie = new Cookie("accessToken", accessToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
        jwtCookie.setMaxAge(24 * 60 * 60); //쿠키 24시간
        jwtCookie.setSecure(true); // secure flag (HTTPS)

        response.addCookie(jwtCookie);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:3000/dashboard")).build();
    }

    //개발자 모드에서는 redirect를 하지 않고 access Token을 가져오는 api
    @GetMapping("/{type}/callback/dev")
    public TokenDto googleLoginforDevelop(@PathVariable String type, @RequestParam String code) {
        return loginService.login(type, code,SecurityConstants.FOR_DEVELOPER);
    }




    //만약 이미 access Toekn을 가지고 있는 경우에는 이 api를 사용
    @GetMapping("/{type}/login")
    public TokenDto login(@PathVariable String type, @RequestParam String accessToken) {
        return loginService.OAuthJoinOrLogin(type, accessToken);
    }


}
