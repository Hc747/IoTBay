package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.product.ProductRepository;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

public class ProductAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String type = request.getParameter("type");

        if (isNullOrEmpty(type)) {
            return;
        }

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        switch (type.toLowerCase()) {
            case "create": create(ctx, session, request); break;
            default: break;
        }
    }

    private void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final User user = authenticate(session);
        validate(user);

        //TODO: get input parameters
        //TODO: validate input parameters

        final ProductRepository repository = ctx.getProducts();
        //TODO: create product

        message = "Successfully created product.";
    }

    @SneakyThrows
    private User authenticate(HttpSession session) {
        User user = AuthenticationUtil.user(session);
        if (user == null) {
            reject("You cannot do this while logged out.");
        }
        return user;
    }

    @SneakyThrows
    private void validate(User user) {
        if (!user.getRole().isStaff()) {
            reject("You don't have permission to perform this action.");
        }
    }
}
