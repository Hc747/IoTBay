package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.user.Role;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import au.edu.uts.isd.iotbay.util.UUIDGenerator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class RegisterAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (AuthenticationUtil.isAuthenticated(session)) {
            throw new ActionException("You cannot do this while logged in.");
        }
        
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (name == null || username == null || password == null) {
            throw new ActionException("You must supply a name, username and password in order to login.");
        }

        //TODO: password hashing
        
        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        final Optional<User> existing = ctx.getUsers().findByUsername(username);
        
        if (existing.isPresent()) {
            throw new ActionException("Sorry, that username is already taken.");
        }
        
        final User user = ctx.getUsers().save(new User(UUIDGenerator.generate(), name, username, password, Role.USER));
        
        AuthenticationUtil.authenticate(session, user);
        
        message = String.format("Registration successful. Welcome %s", user.getUsername());
    }
    
}
