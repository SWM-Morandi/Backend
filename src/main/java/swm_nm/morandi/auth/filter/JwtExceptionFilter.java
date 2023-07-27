package swm_nm.morandi.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import swm_nm.morandi.exception.errorcode.ErrorCode;
import swm_nm.morandi.exception.errorcode.AuthErrorCode;
import swm_nm.morandi.exception.MorandiException;
import swm_nm.morandi.exception.response.ErrorResponse;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    //필터레벨에서는 생성자로 주입받아야함
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (MorandiException e) {
            //JwtProvider에서 발생하는 예외 처리
            setErrorResponse(response, e.getErrorCode());
        }
        catch (Exception e) {
            setErrorResponse(response, AuthErrorCode.UNKNOWN_ERROR);
        }


    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode)    {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = makeErrorResponse(errorCode);
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
