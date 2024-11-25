package swm_nm.morandi.domain.codeSubmit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {
    public String test(int value) throws InterruptedException {
        System.out.println("Response Message SSEID : " + 1 + " Count : " + value);
        return "hello";
    }
}
