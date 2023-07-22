package swm_nm.morandi.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import swm_nm.morandi.auth.constants.OAuthConstants;

@Controller
@PropertySource("classpath:application-oauth.properties")
@RequestMapping("/users")
public class OAuthURIController {

    @Value("${oauth.google.client-id}")
    private String google_client_id;

    @Value("${oauth.google.redirect-uri}")
    private String google_redirect_uri;

    @GetMapping("/google/redirect")
    public String googleRedirect() {

        //이것도 service로 빼기
        return "redirect:" + OAuthConstants.GOOGLE_REDIRECT_URL;

    }


}