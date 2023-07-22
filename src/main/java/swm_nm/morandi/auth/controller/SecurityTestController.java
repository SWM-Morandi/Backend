package swm_nm.morandi.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import swm_nm.morandi.auth.security.SecurityUtils;

@RestController
public class SecurityTestController {

    @GetMapping("test")
    public String test(){
        Long id = SecurityUtils.getCurrentUserId();
        return id.toString();
    }
}
