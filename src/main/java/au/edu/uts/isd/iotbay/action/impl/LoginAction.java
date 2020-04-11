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
        
        if (password.length() < 8) {
            throw new ActionException("Your password must be atleast 8 characters.");
        }
        
        
        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        final Optional<User> user = ctx.getUsers().find(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
        
        AuthenticationUtil.authenticate(session, user.orElseThrow(() -> new ActionException("Incorrect username or password.")));
        
        message = String.format("Login successful. Welcome, %s", user.get().getUsername());
    }
    
}
