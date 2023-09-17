package swm_nm.morandi.domain.auth.constants;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecurityConstants {

    //임시로
    public static final long JWT_EXPIRATION = 600000000;
    public static final long REFRESH_TOKEN_EXPIRATION = 36000000;
    public static final Key JWT_KEY =Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static final Boolean FOR_DEVELOPER= true;

    public static final Boolean FOR_SERVICE= false;
}