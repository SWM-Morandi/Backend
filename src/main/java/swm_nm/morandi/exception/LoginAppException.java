package swm_nm.morandi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginAppException extends RuntimeException{
    private LoginErrorCode errorCode;
    private String message;


}
