package swm_nm.morandi.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import swm_nm.morandi.auth.constants.OAuthConstants;
import swm_nm.morandi.logging.Logging;

@Controller
@Logging
@RequestMapping("/oauths")
@RequiredArgsConstructor
public class OAuthURIController {

    private final OAuthConstants oAuthConstants;

    @ResponseBody
    @GetMapping("/google")
    public String googleRedirect() {

        //이것도 service로 빼기
        return oAuthConstants.GOOGLE_REDIRECT_URL;

    }

    //백엔드 개발 시 사용하는 API
    @GetMapping("/google/dev")
    public String googleRedirectforDevelop() {

        return "redirect:" + oAuthConstants.GOOGLE_REDIRECT_URL_DEV;

    }



}