package swm_nm.morandi.auth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swm_nm.morandi.auth.response.TokenDto;
import swm_nm.morandi.auth.service.LoginService;


@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class OAuthController {


    private final LoginService loginService;

    //개발 테스트용
    @GetMapping("/{type}/callback")
    public TokenDto googleLogin(@PathVariable String type, @RequestParam String code) {
        return loginService.login(type, code);


    }

    //실제로 사용할 용도
    @GetMapping("/login/{type}")
    public TokenDto login(@PathVariable String type, @RequestParam String code) {
        return loginService.OAuthJoinOrLogin(type, code);
    }


}
