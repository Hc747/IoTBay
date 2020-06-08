package au.edu.uts.isd.iotbay.action;

import au.edu.uts.isd.iotbay.util.AuthenticationUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * An {@code Action} that may only be invoked by authenticated {@code User}s.
 */
public class AuthenticatedAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!AuthenticationUtil.isAuthenticated(session)) {
            reject("You cannot do this while logged out.");
        }
    }
}
