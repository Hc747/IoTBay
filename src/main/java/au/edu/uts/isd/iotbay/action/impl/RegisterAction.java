package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.UnauthenticatedAction;
import au.edu.uts.isd.iotbay.model.user.Role;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import au.edu.uts.isd.iotbay.util.Validator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;
import static au.edu.uts.isd.iotbay.util.Validator.matches;

public class RegisterAction extends UnauthenticatedAction {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.invoke(application, session, request, response);
        
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        
        if (isNullOrEmpty(name) || isNullOrEmpty(username) || isNullOrEmpty(password) || isNullOrEmpty(phone)) {
            reject("You must supply a name, username, password and phone number in order to register.");
        }

        if (!matches(Validator.Patterns.NAME_PATTERN, name) || !matches(Validator.Patterns.USERNAME_PATTERN, username) || !matches(Validator.Patterns.PASSWORD_PATTERN, password) || !matches(Validator.Patterns.PHONE_NUMBER_PATTERN, phone)) {
            //TODO: improve UX; provide specific errors
            reject("Your nominated name, username, password and / or phone number do not meet validation requirements.");
        }

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        final User existing = ctx.getUsers().findByUsername(username);
        
        if (existing != null) {
            request.setAttribute("username", username);
            request.setAttribute("name", name);
            reject("Sorry, that username is already taken.");
        }

        final User user = ctx.getUsers().create(new User(name, username, AuthenticationUtil.hash(password), phone, Role.USER, true, LocalDate.now(), null, new ArrayList<>(), new ArrayList<>()));

        if (user == null) {
            request.setAttribute("username", username);
            request.setAttribute("name", name);
            reject("Unable to register an account; please try again.");
        }

        ctx.log(user, "Register");

        AuthenticationUtil.authenticate(session, user);
        
        message = String.format("Registration successful. Welcome %s", user.getUsername());
    }
}
