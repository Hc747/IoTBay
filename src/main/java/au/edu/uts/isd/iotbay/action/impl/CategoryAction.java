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

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;


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
        //Validation of user
        final User user = authenticate(session);
        validate(user);
        //Fetch Input Parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        //Check if input parameters meet specifications
        if (isNullOrEmpty(name) || isNullOrEmpty(description)) {
            reject("You must supply a name and description in order to create a category.");
        }

        if (name.length() < 4) {
            reject("Product name must be at least 4 characters.");
        }

        if (description.length() < 10) {
            reject("Product description must be at least 10 characters.");
        }
        //Create the category in the database.
        final CategoryRepository repository = ctx.getCategories();
        final Category category = repository.create(Category.create(name, description));

        //Check that the category was successfully added to the db
        if (category == null) {
            reject("Unable to create the category.");
        }
        //Return confirmation message that category created
        message = "Successfully created product.";
    }

    @SneakyThrows
    private void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        //Validation of the user
        final User user = authenticate(session);
        validate(user);

        //Get the input object id for the object to be deleted
        final String identifier = request.getParameter("id");
        //Check the input object id is valid
        if (isNullOrEmpty(identifier)) {
            reject("No Category Id found");
        }

        if (!ObjectId.isValid(identifier)) {
            reject(" Category Id was not valid.");
        }

        //Create a objectID object with the input ObjectID
        final ObjectId id = new ObjectId(identifier);
        //Initialise the CategoryRepository and find the category with the object id
        final CategoryRepository repository = ctx.getCategories();
        final Category category = repository.findById(id);

        //Check the category at id exists
        if (category == null) {
            reject("Could not find category to delete.");
        }
        //Delete the category from the database.
        Category deleted = repository.delete(category);

        //Confirm the product deleted from the database
        if (deleted == null) {
            reject("Unable to delete the category.");
        }
        //Return confirmation message
        message = "Successfully deleted the product.";
    }

    @SneakyThrows
    private void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        //Validation of the user
        final User user = authenticate(session);
        validate(user);

        //Get the input object id for the object to be deleted
        final String identifier = request.getParameter("id");
        //Check the input object id is valid
        if (isNullOrEmpty(identifier)) {
            reject("No Category Id found");
        }

        if (!ObjectId.isValid(identifier)) {
            reject(" Category Id was not valid.");
        }
        //Create a objectID object with the input ObjectID
        final ObjectId id = new ObjectId(identifier);

        //Initialise the CategoryRepository and find the category with the object id
        final CategoryRepository repository = ctx.getCategories();
        final Category category = repository.findById(id);

        //Check the category at id exists
        if (category == null) {
            reject("Could not find category to delete.");
        }
        //Fetch Input Parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        //Check if input parameters meet specifications
        if (isNullOrEmpty(name) || isNullOrEmpty(description) ) {
            reject("You must supply a name and description in order to create a category.");
        }

        if (name.length() < 4) {
            reject("Product name must be at least 4 characters.");
        }

        if (description.length() < 10) {
            reject("Product description must be at least 10 characters.");
        }

        //Update object category
        category.setName(name);
        category.setDescription(description);
        //Update the object in the database with the object updated
        final Category updated = repository.update(category);
        //Check that the update was applied
        if (updated == null) {
            reject("Unable to update category.");
        }
        //Confirmation message
        message = "Successfully updated category.";
    }

    @SneakyThrows
    private User authenticate(HttpSession session) { //Ensures the user is logged in
        User user = AuthenticationUtil.user(session);
        if (user == null) {
            reject("You cannot do this while logged out.");
        }
        return user;
    }

    @SneakyThrows
    private void validate(User user) { //Checks that the user is a staff member
        if (!user.getRole().isStaff()) {
            reject("You don't have permission to perform this action.");
        }
    }
}
