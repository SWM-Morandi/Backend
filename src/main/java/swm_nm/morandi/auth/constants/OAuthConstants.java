package swm_nm.morandi.auth.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


public class OAuthConstants {

    public static final String GOOGLE_REDIRECT_URL = "https://accounts.google.com/o/oauth2/v2/auth?"
            + "scope=https://www.googleapis.com/auth/userinfo.email&"
            + "access_type=offline&"
            + "include_granted_scopes=true&"
            + "response_type=code&"
            + "state=state_parameter_passthrough_value&"
            +"redirect_uri=http://localhost:8080/users/google/callback&"
            +"client_id=1047762864117-c4i1inqqugoesvruk7gkjv0itdnm9mov.apps.googleusercontent.com";
    public static final String GOOGLE_BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth?";
    public static final String GOOGLE_SCOPE = "scope=https://www.googleapis.com/auth/userinfo.email&";

    public static final String GOOGLE_ANOTHER_FIELD = "access_type=offline&" +
            "include_granted_scopes=true&" +
            "response_type=code&" +
            "state=state_parameter_passthrough_value&";

    public static final String GOOGLE_CLIENT_CALLBACK_REDIRECT = "redirect_uri=http://localhost:8080/users/google/callback&";

    public static final String GOOGLE_CLIENT = "client_id=";


    public static final String GOOGLE_USERINFO_REQUEST_URL="https://www.googleapis.com/userinfo/v2/me";

}
