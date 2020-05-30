package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.AuthenticatedAction;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

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
        //todo; verify

        user.setName(name);
        user.setUsername(username);
        user.setPhone(phone);
        user.setEnabled(enabled);

        ctx.getUsers().update(user);
        ctx.log(user, "Updated Account");

        message = "Successfully updated account";
    }
}