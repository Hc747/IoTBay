package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.UnauthenticatedAction;
import au.edu.uts.isd.iotbay.model.user.Role;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class RegisterAction extends UnauthenticatedAction {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.invoke(application, session, request, response);
        
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (name == null || username == null || password == null) {
            reject("You must supply a name, username and password in order to login.");
        }

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        final Optional<User> existing = ctx.getUsers().findByUsername(username);
        
        if (existing.isPresent()) {
            reject("Sorry, that username is already taken.");
        }

        final User user = ctx.getUsers().save(new User(null, name, username, AuthenticationUtil.hash(password), Role.USER, true, Timestamp.from(Instant.now()), null));

        if (user == null) {
            reject("Unable to register an account; please try again.");
        }
        
        AuthenticationUtil.authenticate(session, user);
        
        message = String.format("Registration successful. Welcome %s", user.getUsername());
    }
}
