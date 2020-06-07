package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.model.user.User;
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
import static au.edu.uts.isd.iotbay.util.Validator.Patterns.OBJECT_DESCRIPTION_PATTERN;
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
        validate(user);

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String quantityString = request.getParameter("quantity");
        String priceString = request.getParameter("price");

        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(quantityString) || isNullOrEmpty(priceString)) {
            reject("You must supply a name, description, quantity and priceString in order to create a product.");
        }

        if (!matches(DECIMAL_PATTERN, priceString)) {
            reject("The input priceString did not meet the required format.");
        }

        if (!matches(WHOLE_NUMBER_PATTERN, quantityString)) {
            reject("The input quantity was not a valid whole number.");
        }

        if (!(matches(OBJECT_NAME_PATTERN, name))) {
            reject("The input name was not a valid name for the product.");
        }

        if (!(matches(OBJECT_DESCRIPTION_PATTERN, description))) {
            reject("The input description was not a valid description for the product.");
        }

        double price = Double.parseDouble(priceString);
        int quantity = Integer.parseInt(quantityString);

        //TODO: get input parameters
        //TODO: validate input parameters

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

        final ProductRepository repository = ctx.getProducts();
        final Product product = repository.create(new Product().create(name, description, quantity, price));

        if (product == null) {
            reject("Unable to create product.");
        }

        message = "Successfully created product.";
        //TODO: Account for catagory's images, etc.
        //TODO::Return to product page with ProductID
    }

    @SneakyThrows
    private void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final String identifier = request.getParameter("productId");

        if (isNullOrEmpty(identifier)) {
            reject("No Product Id found");
        }

        if (!matches(WHOLE_NUMBER_PATTERN, identifier)) {
            reject("The Product Id was not a valid whole number");
        }

        if (!ObjectId.isValid(identifier)) {
            reject("Product Id was not valid.");
        }

        final ObjectId id = new ObjectId(identifier);

        final ProductRepository repository = ctx.getProducts();
        final Product product = repository.findById(id);

        if (product == null) {
            reject("Could not find product to delete.");
        }

        Product deleted = repository.delete(product);

        if (deleted == null) {
            reject("Unable to delete the product.");
        }

        message = "Successfully deleted the product.";
        //TODO::Return to a page. Indicate the status of the deletion
    }

    @SneakyThrows
    private void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final String identifier = request.getParameter("id");

        if (isNullOrEmpty(identifier)) {
            reject("No Product Id found");
        }

        if (!matches(WHOLE_NUMBER_PATTERN, identifier)) {
            reject("The Product Id was not a valid whole number");
        }

        if (!ObjectId.isValid(identifier)) {
            reject("Product Id was not valid.");
        }

        final ObjectId id = new ObjectId(identifier);

        final ProductRepository repository = ctx.getProducts();
        final Product product = repository.findById(id);

        if (product == null) {
            reject("Could not find product to delete.");
        }
        //Get the input parameters
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String quantityString = request.getParameter("quantity");
        String priceString = request.getParameter("price");

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

        if (!(matches(OBJECT_NAME_PATTERN, name))) {
            reject("The input name was not a valid name for the product.");
        }

        if (!(matches(OBJECT_DESCRIPTION_PATTERN, description))) {
            reject("The input description was not a valid description for the product.");
        }

        DecimalFormat priceFormat = new DecimalFormat("##.00");
        double price = Double.parseDouble(priceFormat.format(request.getParameter("productPrice")));
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

        //TODO: Account for catagory's images, etc.
        
        product.setName(name);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setPrice(price);

        final Product updated = repository.update(product);

        if (updated == null) {
            reject("Unable to update product.");
        }
        message = "Successfully updated product.";
        //TODO::Return to product page with ProductID
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
