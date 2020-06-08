package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.AuthenticatedAction;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import au.edu.uts.isd.iotbay.util.Validator;
import lombok.SneakyThrows;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;
import static au.edu.uts.isd.iotbay.util.Validator.matches;

public class UserAction extends AuthenticatedAction {

    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.invoke(application, session, request, response);

        final String type = request.getParameter("type");

        if (isNullOrEmpty(type)) {
            return;
        }

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        final User user = AuthenticationUtil.user(session);

        switch (type.toLowerCase()) {
            case "update": update(ctx, user, request); break;
            default: break;
        }
    }

    @SneakyThrows
    private void update(IoTBayApplicationContext ctx, User user, HttpServletRequest request) {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        boolean enabled = isNullOrEmpty(request.getParameter("disable"));

        //validation
        if (isNullOrEmpty(name) || isNullOrEmpty(username) || isNullOrEmpty(phone)) {
            reject("You must supply a name, username and phone number in order to register.");
        }

        //validation
        if (!matches(Validator.Patterns.NAME_PATTERN, name) || !matches(Validator.Patterns.USERNAME_PATTERN, username) || !matches(Validator.Patterns.PHONE_NUMBER_PATTERN, phone)) {
            reject("Your nominated name, username or phone number do not meet validation requirements.");
        }

        //updates the object
        user.setName(name);
        user.setUsername(username);
        user.setPhone(phone);
        user.setEnabled(enabled);

        //updates MongoDB
        ctx.getUsers().update(user);
        //Logs the action
        ctx.log(user, "Updated Account");

        message = "Successfully updated account";
    }
}