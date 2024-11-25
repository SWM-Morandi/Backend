package swm_nm.morandi.domain.codeSubmit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import swm_nm.morandi.domain.codeSubmit.service.TestService;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/hello-world/{value}")
    public String getData(@PathVariable("value") Integer value) throws InterruptedException {
        return testService.test(value);
    }
}
