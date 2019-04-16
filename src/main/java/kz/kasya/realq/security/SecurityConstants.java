package kz.kasya.realq.security;

/**
 * @author Assylkhan
 * on 15.03.2019
 * @project realq
 */
public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/workers";
    public static final String ADD_TASK_URL = "/api/tasks";
    public static final String LOGIN_URL = "/login";
    public static final String SOCKET_URL = "/socket/**";
}
