package swm_nm.morandi.auth.constants;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecurityConstants {

    //임시로
    public static final long JWT_EXPIRATION = 600000;
    public static final long REFRESH_TOKEN_EXPIRATION = 36000000;
    public static final Key JWT_KEY =Keys.secretKeyFor(SignatureAlgorithm.HS512);

}
