package au.edu.uts.wsd.action.impl;

import au.edu.uts.wsd.Constants;
import au.edu.uts.wsd.model.Person;
import au.edu.uts.wsd.action.Action;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean loggedIn = session.getAttribute(Person.KEY) != null;
        
        session.invalidate();
        
        if (loggedIn) {
            type = MessageType.SUCCESS;
            message = "Logout successful.";
        } else {
            type = MessageType.WARNING;
            message = "You were already logged out.";
        }
        
        response.sendRedirect(Constants.BASE_URL);
    }
    
}
