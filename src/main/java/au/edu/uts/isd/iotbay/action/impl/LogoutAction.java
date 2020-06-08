package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.AuthenticatedAction;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutAction extends AuthenticatedAction {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.invoke(application, session, request, response);
        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        //gets the current session
        User user = AuthenticationUtil.user(session);

        //adds action to the log + ends the session
        ctx.log(user, "Logout");
        AuthenticationUtil.unauthenticate(session);

        type = MessageType.SUCCESS;
        message = "Logout successful.";
    }
}
