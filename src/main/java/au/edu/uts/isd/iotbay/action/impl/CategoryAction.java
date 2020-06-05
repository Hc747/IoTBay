package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.category.CategoryRepository;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static au.edu.uts.isd.iotbay.util.Validator.Patterns.*;
import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;
import static au.edu.uts.isd.iotbay.util.Validator.matches;

public class CategoryAction extends Action {
    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String type = request.getParameter("type");

        if (isNullOrEmpty(type)){
            return;
        }

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        switch (type.toLowerCase()){
            case "create": create(ctx, session, request); break;
            case "delete": delete(ctx, session, request); break;
            case "update": update(ctx, session, request); break;
            default: break;
        }
    }

    @SneakyThrows
    private void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final User user = authenticate(session);
        validate(user);

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String enabledString = request.getParameter("enabled").toLowerCase();

        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(enabledString)) {
            reject("You must supply a name, description and an enable status in order to create a category.");
        }

        if (!(matches(OBJECT_NAME_PATTERN, name))) {
            reject("The input name was not a valid name for the category.");
        }
        if (!(matches(OBJECT_DESCRIPTION_PATTERN, description))) {
            reject("The input description was not a valid description for the category.");
        }
        if (name.length() < 4) {
            reject("Product name must be at least 4 characters.");
        }

        if (description.length() < 10) {
            reject("Product description must be at least 10 characters.");
        }

        if (!(enabledString.equals("false")) || (enabledString.equals("true"))) {
            reject("The input enabled status was not a valid boolean.");
        }
        boolean enabled = Boolean.getBoolean(enabledString);
        final CategoryRepository repository = ctx.getCategories();
        final Category category = repository.create(new Category(name, description, enabled));

        if (category == null) {
            reject("Unable to create the category.");
        }
        message = "Successfully created product.";
        //TODO::Return the category id to caller.
    }
    private void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {

    }
    private void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {

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
