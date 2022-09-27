package rmit.sec.webstorepmicroservice.security;

public class SecurityConstant {
    // Secret key
    public static final String SECRET ="jwt.security.constant.secretkey";

    // time of which the JWT will remain valid for (in ms) 86400000 -> 1 day
    public static final long EXPIRATION_TIME = 86400000;
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";

}
