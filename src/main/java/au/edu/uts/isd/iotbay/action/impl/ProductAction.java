package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
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
import java.util.Optional;

import static au.edu.uts.isd.iotbay.util.Validator.Patterns.DECIMAL_PATTERN;
import static au.edu.uts.isd.iotbay.util.Validator.Patterns.WHOLE_NUMBER_PATTERN;
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

        String name = request.getParameter("productName");
        String description = request.getParameter("productDescription");
        String quantity = request.getParameter("productQuantity");
        String price = request.getParameter("productPrice");

        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(quantity) || isNullOrEmpty(price)) {
            reject("You must supply a name, description, quantity and price in order to create a product.");
        }

        if (!matches(DECIMAL_PATTERN, price)) {
            reject("The input price did not meet the required format.");
        }

        if (!matches(WHOLE_NUMBER_PATTERN, quantity)) {
            reject("The input quantity was not a valid whole number");
        }

        double productPrice = Double.parseDouble(price);
        int productQuantity = Integer.parseInt(quantity);

        //TODO: get input parameters
        //TODO: validate input parameters

        if (name.length() < 4) {
            reject("Product name must be at least 4 characters.");
        }

        if (description.length() < 10) {
            reject("Product description must be at least 10 characters.");
        }

        if (productQuantity < 0) {
            reject("Product quantity cannot be negative.");
        }

        if (productPrice < 0) {
            reject("Product price cannot be negative.");
        }
        //TODO: Account for catagory's images, etc.


        final ProductRepository repository = ctx.getProducts();
        //TODO: create product
        final Product product = repository.create(new Product(null, name, description, productQuantity, productPrice));

        if (product == null) {
            reject("Unable to create product.");
        }

        message = "Successfully created product.";
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
        Optional<Product> product = repository.findByProductId(id);

        if (!(product.isPresent())) {
            reject("Could not find product to delete. Id may have been incorrect.");
        }

        Product deleted = repository.delete(product.get());

        if (deleted == null) {
            reject("Unable to delete the product.");
        }

        message = "Successfully deleted the product.";
        //TODO::Return to a page.
    }

    @SneakyThrows
    private void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
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
        Optional<Product> product = repository.findByProductId(id);

        if (!(product.isPresent())) {
            reject("Could not find product to delete. Id may have been incorrect.");
        }

        String name = request.getParameter("productName");
        String description = request.getParameter("productDescription");
        String quantity = request.getParameter("productQuantity");
        String price = request.getParameter("productPrice");

        if (isNullOrEmpty(name) || isNullOrEmpty(description) || isNullOrEmpty(quantity) || isNullOrEmpty(price)) {
            reject("One or more inputs was empty.");
        }

        if (!(matches(DECIMAL_PATTERN, price))) {
            reject("The input price did not meet the required format.");
        }

        if (!(matches(WHOLE_NUMBER_PATTERN, quantity))) {
            reject("The input quantity was not a valid whole number");
        }

        DecimalFormat priceFormat = new DecimalFormat("##.00");
        double productPrice = Double.parseDouble(priceFormat.format(request.getParameter("productPrice")));
        int productQuantity = Integer.parseInt(quantity);

        //TODO: get input parameters
        //TODO: validate input parameters

        if (name.length() < 4) {
            reject("Product name was not long enough");
        }

        if (description.length() < 10) {
            reject("Product description was not long enough");
        }

        if (productQuantity < 0) {
            reject("Product Quantity was not valid. Can't be a negative value");
        }

        if (productPrice < 0) {
            reject("Product Price was not valid Can't be a negative value");
        }
        //TODO: Account for catagory's images, etc.


        //TODO: create product
        final Product updated_product = repository.update(new Product(id, name, description, productQuantity, productPrice));


        if (updated_product == null) {
            reject("Unable to create product.");
        }
        message = "Successfully created product.";
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
