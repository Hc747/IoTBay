package au.edu.uts.isd.iotbay.util;

import au.edu.uts.isd.iotbay.model.user.User;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Harrison
 */
public class AuthenticationUtil {
    
    private static final String SESSION_KEY = "authenticated-user";
    
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
//        Object principle = session.get
    }
    
}
