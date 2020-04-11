package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (AuthenticationUtil.isAuthenticated(session)) {
            throw new ActionException("You cannot do this while logged in.");
        }
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username == null || password == null) {
            throw new ActionException("You must supply an username and password in order to login.");
        }
        
        //TODO: password hashing
        

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        final Optional<User> candidate = ctx.getUsers().findByUsername(username);
        final User user = candidate.orElseThrow(() -> new ActionException("Incorrect username or password."));

        if (!user.getPassword().equals(password)) {
            throw new ActionException("Incorrect username or password.");
        }

        AuthenticationUtil.authenticate(session, user);
        
        message = String.format("Login successful. Welcome, %s", user.getUsername());
    }
    
}
