package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.category.CategoryRepository;
import au.edu.uts.isd.iotbay.repository.product.ProductRepository;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.List;

import static au.edu.uts.isd.iotbay.util.Validator.Patterns.*;
import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;
import static au.edu.uts.isd.iotbay.util.Validator.matches;

public class ProductAction extends Action {

    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("###,##0.00");

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String type = request.getParameter("type");

        if (isNullOrEmpty(type)) {
            return;
        }

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        //TODO(mathew): dispatch the other actions
        switch (type.toLowerCase()) {
            case "create": create(ctx, session, request); break;
            case "delete": delete(ctx, session, request); break;
            case "update": update(ctx, session, request); break;
            default: break;
        }
    }

    //TODO(mathew): modularise the validation / instance creation / updating logic
    //TODO(mathew): make the validation messages consistent
    //TODO(mathew): remove product from the parameter / variable names; just refer to them by their attribute name (productName => name)
    //TODO(mathew): use primitive types where possible

    @SneakyThrows
    private void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final User user = authenticate(session);
        //Validation of user
        validate(user);

        //Fetch Input Parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String quantityString = request.getParameter("quantity");
        String priceString = request.getParameter("price");

        //Check if input parameters meet specifications
        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(quantityString) || isNullOrEmpty(priceString)) {
            reject("You must supply a name, description, quantity and priceString in order to create a product.");
        }

        if (!matches(DECIMAL_PATTERN, priceString)) {
            reject("The input price did not meet the required format.");
        }

        if (!matches(WHOLE_NUMBER_PATTERN, quantityString)) {
            reject("The input quantity was not a valid whole number.");
        }

        double price = Double.parseDouble(priceString);
        int quantity = Integer.parseInt(quantityString);

        if (name.length() < 4) {
            reject("Product name must be at least 4 characters.");
        }

        if (description.length() < 10) {
            reject("Product description must be at least 10 characters.");
        }

        if (quantity < 0) {
            reject("Product quantity cannot be negative.");
        }

        if (price < 0) {
            reject("Product priceString cannot be negative.");
        }

        //Create the product in the database.
        final ProductRepository repository = ctx.getProducts();
        final Product product = Product.create(name, description, quantity, price);
        final Product newProduct = repository.create(product);

        //Check that the product was successfully added to the db
        if (newProduct == null) {
            reject("Unable to create product.");
        }
        //Return confirmation message that product created
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
            reject("No Product Id found");
        }

        if (!ObjectId.isValid(identifier)) {
            reject("Product Id was not valid.");
        }

        //Create a objectID object with the input ObjectID
        final ObjectId id = new ObjectId(identifier);

        //Initialise the ProductRepository and find the product with the object id
        final ProductRepository repository = ctx.getProducts();
        final Product product = repository.findById(id);

        //Check the product at id exists
        if (product == null) {
            reject("Could not find product to delete.");
        }
        //Delete the product from the database.
        Product deleted = repository.delete(product);

        //Confirm the product deleted from the database
        if (deleted == null) {
            reject("Unable to delete the product.");
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
            reject("No Product Id found");
        }

        if (!ObjectId.isValid(identifier)) {
            reject("Product Id was not valid.");
        }

        //Initialise the ProductRepository and find the product with the object id
        final ProductRepository repository = ctx.getProducts();
        final Product product = repository.findById(identifier);

        //Check the product at id exists
        if (product == null) {
            reject("Could not find product to delete.");
        }
        //Get the input parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String quantityString = request.getParameter("quantity");
        String priceString = request.getParameter("price");
        String[] categories = request.getParameterValues("categories");

        //Validate the input parameters
        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(quantityString) || isNullOrEmpty(priceString)) {
            reject("One or more inputs was empty.");
        }

        if (!(matches(DECIMAL_PATTERN, priceString))) {
            reject("The input price did not meet the required format.");
        }

        if (!(matches(WHOLE_NUMBER_PATTERN, quantityString))) {
            reject("The input quantity was not a valid whole number");
        }

        double price = Double.parseDouble(priceString);
        int quantity = Integer.parseInt(quantityString);


        if (name.length() < 4) {
            reject("Product name was not long enough");
        }

        if (description.length() < 10) {
            reject("Product description was not long enough");
        }

        if (quantity < 0) {
            reject("Product Quantity was not valid. Can't be a negative value");
        }

        if (price < 0) {
            reject("Product Price was not valid Can't be a negative value");
        }
        //Update object product
        product.setName(name);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setPrice(price);

        //Add selected categories to the product. Verify's the id is safe to add before adding
        final CategoryRepository categoryRepository = ctx.getCategories();
        for (String category : categories) {
            if (!(isNullOrEmpty(category))) {
                if (ObjectId.isValid(category)) {
                    ObjectId id = new ObjectId(category);
                    Category newCategory = categoryRepository.findById(id);
                    if (newCategory != null) {
                        product.addCategories(id);
                    }
                }
            }
        }

        //Update the object in the database with the object updated
        final Product updated = repository.update(product);

        //Check that the update was applied
        if (updated == null) {
            reject("Unable to update product.");
        }
        //Confirmation message
        message = "Successfully updated product.";
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
