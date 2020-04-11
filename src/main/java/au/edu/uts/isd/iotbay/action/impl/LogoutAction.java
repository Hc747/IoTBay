package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final boolean valid = AuthenticationUtil.unauthenticate(session);
        
        if (valid) {
            type = MessageType.SUCCESS;
            message = "Logout successful.";
        } else {
            throw new ActionException("You cannot do this while logged out.");
        }
    }
    
}
