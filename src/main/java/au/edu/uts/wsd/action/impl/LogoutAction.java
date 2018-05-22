package au.edu.uts.wsd.action.impl;

import au.edu.uts.wsd.Constants;
import au.edu.uts.wsd.action.Action;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction implements Action {

    @Override
    public void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        session.invalidate();
        response.sendRedirect(Constants.BASE_URL);
    }
    
}
