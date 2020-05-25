package au.edu.uts.isd.iotbay.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import au.edu.uts.isd.iotbay.model.user.User;
import javax.servlet.http.HttpSession;

public class AuthenticationUtil {
    
    private static final String SESSION_KEY = "authenticated-user";
    private static final BCrypt.Hasher BCRYPT = BCrypt.with(BCrypt.Version.VERSION_2B);
    private static final int BCRYPT_LOG_ROUNDS = 12;
    
    public static boolean isAuthenticated(HttpSession session) {
        return user(session) != null;
    }
    
    public static User user(HttpSession session) {
        Object principle = session.getAttribute(SESSION_KEY);
        if (principle instanceof User) {
            return (User) principle;
        }
        return null;
    }
    
    public static User authenticate(HttpSession session, User user) {
        session.setAttribute(SESSION_KEY, user);
        return user;
    }
    
    public static boolean unauthenticate(HttpSession session) {
        boolean result = isAuthenticated(session);
        session.invalidate();
        return result;
    }

    public static String hash(String password) {
        return BCRYPT.hashToString(BCRYPT_LOG_ROUNDS, password.toCharArray());
    }

    public static boolean verify(String password, String hash) {
        final BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hash);
        return result.verified;
    }
}
