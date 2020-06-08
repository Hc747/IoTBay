package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.AuthenticatedAction;
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

public class UpdatePasswordAction extends AuthenticatedAction {

        @Override
        protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
            super.invoke(application, session, request, response);

            String cPass = request.getParameter("cPass");
            String nPass = request.getParameter("nPass");

            if (isNullOrEmpty(cPass) || isNullOrEmpty(nPass)) {
                reject("You must supply a password to update");
            }

            if ((!matches(Validator.Patterns.PASSWORD_PATTERN, cPass) || !matches(Validator.Patterns.PASSWORD_PATTERN, nPass))) {
                //TODO: improve UX; provide specific errors
                reject("Your nominated password does not meet validation requirements.");
            }

            final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
            final User existing = AuthenticationUtil.user(session);

            if (!AuthenticationUtil.verify(cPass, existing.getPassword())) {
                reject("Incorrect password.");
            }

            existing.setPassword(AuthenticationUtil.hash(nPass));
            ctx.getUsers().update(existing);
            ctx.log(existing, "Password changed");
            AuthenticationUtil.authenticate(session, existing);

            message = String.format("Updating password was successful");
        }
    }
