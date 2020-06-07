package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.category.CategoryRepository;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

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
        final Category category = repository.create(Category.create(name, description, enabled));

        if (category == null) {
            reject("Unable to create the category.");
        }
        message = "Successfully created product.";
        //TODO::Return to a page.
        session.setAttribute("newCategory", category);
    }

    @SneakyThrows
    private void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final String identifier = request.getParameter("productId");

        if (isNullOrEmpty(identifier)) {
            reject("No Category Id found");
        }

        if (!matches(WHOLE_NUMBER_PATTERN, identifier)) {
            reject("The Category Id was not a valid whole number");
        }

        if (!ObjectId.isValid(identifier)) {
            reject(" Category Id was not valid.");
        }

        final ObjectId id = new ObjectId(identifier);

        final CategoryRepository repository = ctx.getCategories();
        final Category category = repository.findById(id);

        if (category == null) {
            reject("Could not find category to delete.");
        }

        //TODO: Account for products with Category Assigned to them. Do we delete categories with products assigned to the category?
        Category deleted = repository.delete(category);

        if (deleted == null) {
            reject("Unable to delete the category.");
        }

        message = "Successfully deleted the product.";
        //TODO::Return to a page.
        session.setAttribute("deletedCategory", deleted);
    }

    @SneakyThrows
    private void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final String identifier = request.getParameter("id");

        if (isNullOrEmpty(identifier)) {
            reject("No Category Id found");
        }

        if (!matches(WHOLE_NUMBER_PATTERN, identifier)) {
            reject("The Category Id was not a valid whole number");
        }

        if (!ObjectId.isValid(identifier)) {
            reject(" Category Id was not valid.");
        }

        final ObjectId id = new ObjectId(identifier);

        final CategoryRepository repository = ctx.getCategories();
        final Category category = repository.findById(id);

        if (category == null) {
            reject("Could not find category to delete.");
        }
        //TODO: Account for products with Category Assigned to them. If the category is being changed to disabled. How will this affect products with this category?
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String enabledString = request.getParameter("enabled").toLowerCase();

        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(enabledString)) {
            reject("You must supply a name, description and an enable status in order to create a category.");
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
        final Category updated = repository.update(category);

        if (updated == null) {
            reject("Unable to update category.");
        }
        message = "Successfully updated category.";
        //TODO::Return to a page.
        session.setAttribute("updatedCategory", updated);
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
