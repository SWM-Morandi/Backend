package swm_nm.morandi.auth.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TokenResponseDto {
    public String token_type;
    public String access_token;

    public String id_token;

    public Integer expires_in;

    public String refresh_token;

    public Integer refresh_token_expires_in;

    public String scope;

    @Override
    public String toString() {
        return "TokenResponseDto{" +
                "token_type='" + token_type + '\'' +
                ", access_token='" + access_token + '\'' +
                ", id_token='" + id_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", refresh_token_expires_in=" + refresh_token_expires_in +
                ", scope='" + scope + '\'' +
                '}';
    }
}
