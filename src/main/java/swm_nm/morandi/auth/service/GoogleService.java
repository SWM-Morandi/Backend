package swm_nm.morandi.auth.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import swm_nm.morandi.auth.constants.OAuthConstants;
import swm_nm.morandi.auth.response.GoogleUserDto;
import swm_nm.morandi.auth.response.TokenResponseDto;
import swm_nm.morandi.exception.MorandiException;
import swm_nm.morandi.exception.errorcode.AuthErrorCode;
import swm_nm.morandi.member.domain.SocialType;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleService implements OAuthService{
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${oauth.google.client-id}")
    private String google_client_id;

    @Value("${oauth.google.client-secret}")
    private String google_client_secret;

    @Value("${oauth.google.redirect-uri}")
    private String google_client_redirect_uri;


    //키 값
    @Override
    public String getType() {
        return "google";
    }

    @Override
    public String getAccessToken(String authorization_code, Boolean isDev){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", authorization_code);
            params.add("client_id", google_client_id);
            params.add("client_secret", google_client_secret);
            params.add("grant_type", "authorization_code");
            if(isDev)
                params.add("redirect_uri", google_client_redirect_uri+"/dev");
            else
                params.add("redirect_uri", google_client_redirect_uri);


        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);


        //오류 발생 시 Response Entity 반환하려면 어쩔 수 없이 아래처럼 초기화 해야 함
        ResponseEntity<String> responseEntity=null;
        try {
           responseEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token", requestEntity, String.class);
            return objectMapper.readValue(responseEntity.getBody(), TokenResponseDto.class).getAccess_token();
        }
        catch(RestClientException e){
            log.error("params = {}, requestEntity = {}",params, requestEntity);
            throw new MorandiException(AuthErrorCode.SSO_SERVER_ERROR);
        }
        catch (JsonProcessingException  | NullPointerException e){
            log.error("params = {}, requestEntity = {}, responseEntity = {}",params, requestEntity,responseEntity);
            throw new MorandiException(AuthErrorCode.SSO_ACCESS_TOKEN);
        }


    }

    public GoogleUserDto getMemberInfo(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+accessToken);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity(headers);

        ResponseEntity<String> responseEntity = null;
        try{
            responseEntity =restTemplate.exchange(OAuthConstants.GOOGLE_USERINFO_REQUEST_URL, HttpMethod.GET,requestEntity,String.class);
            GoogleUserDto googleUserDto = objectMapper.readValue(responseEntity.getBody(), GoogleUserDto.class);
            googleUserDto.setType(SocialType.GOOGLE);
            return googleUserDto;
        }
        catch (RestClientException e){
            log.error("requestEntity = {}",requestEntity);
            throw new MorandiException(AuthErrorCode.SSO_SERVER_ERROR);
        }
        catch (JsonProcessingException | NullPointerException e)
        {
            log.error("responseEntity = {}",responseEntity);
            throw new MorandiException(AuthErrorCode.SSO_USERINFO);
        }



    }


}
