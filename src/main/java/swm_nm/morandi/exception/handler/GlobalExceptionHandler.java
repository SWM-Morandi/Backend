package swm_nm.morandi.exception.handler;

import io.sentry.Scope;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import swm_nm.morandi.exception.errorcode.CommonErrorCode;
import swm_nm.morandi.exception.errorcode.ErrorCode;
import swm_nm.morandi.exception.MorandiException;
import swm_nm.morandi.exception.response.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * MorandiException 핸들러
     */
    @ExceptionHandler(MorandiException.class)
    public ResponseEntity<?> MorandiExceptionHandler(MorandiException e){

        String message = e.getMessage();
        //log.error(message, e);
        Sentry.configureScope(Scope::clear);
        return handleExceptionInternal(e.getErrorCode(),message);


    }

    //나중에 @Valid용도
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        log.error("IllegalArgument handling",e);
        ErrorCode errorCode = CommonErrorCode.INVALID_PARAMETER;
        Sentry.configureScope(Scope::clear);
        return handleExceptionInternal(errorCode, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleAllException(Exception e) {
        log.error("INTERNAL_SERVER_ERROR", e);
        ErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;
        Sentry.configureScope(Scope::clear);
        return handleExceptionInternal(errorCode);
    }

    /////////////////////////////////////////////////////////////////////////////

    private ResponseEntity<?> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    private ResponseEntity<?> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }




}
