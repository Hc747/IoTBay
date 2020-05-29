package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.AuthenticatedAction;
import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class LogoutAction extends AuthenticatedAction {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.invoke(application, session, request, response);
        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        User user = AuthenticationUtil.user(session);

        ctx.getUserLogs().create(new UserLog(null, user.getId(), "Logout", Timestamp.from(Instant.now())));
        AuthenticationUtil.unauthenticate(session);

        type = MessageType.SUCCESS;
        message = "Logout successful.";
    }
}
