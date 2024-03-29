package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.UnauthenticatedAction;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

public class LoginAction extends UnauthenticatedAction {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.invoke(application, session, request, response);

        //inputted data
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //validation
        if (isNullOrEmpty(username) || isNullOrEmpty(password)) {
            reject("You must supply a username and password in order to login.");
        }

        //finds the user
        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        final User user = ctx.getUsers().findByUsername(username);

        //checks if the user is found
        if (user == null) {
            request.setAttribute("username", username);
            reject("Incorrect username or password");
        }

        //checks if the user is enabled or disabled
        if (!user.isEnabled()) {
            request.setAttribute("username", username);
            reject("This account has been disabled.");
        }

        //verifies if the inputted password matches the hashed password
        if (!AuthenticationUtil.verify(password, user.getPassword())) {
            request.setAttribute("username", username);
            reject("Incorrect username or password.");
        }

        //adds action to the log
        ctx.log(user, "Login");
        //sets the session
        AuthenticationUtil.authenticate(session, user);
        
        message = String.format("Login successful. Welcome, %s", user.getUsername());
        //TODO: add login redirection
//
//        final String encoded = request.getParameter("redirect_url");
//
//        if (encoded != null) {
//            final String decoded = decode(encoded);
//
////            if (decoded.startsWith(Constants.path(false))) {
//                response.sendRedirect(decoded);
////            }
//        }
    }
//
//    private static String decode(String encoded) {
//        try {
//            return URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString());
//        } catch (UnsupportedEncodingException ex) {
//            throw new RuntimeException(ex.getCause());
//        }
//    }
}
