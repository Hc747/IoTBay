package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.cart.ShoppingCart;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.util.ShoppingCartUtil;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

public class ShoppingCartAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String type = request.getParameter("type");

        if (isNullOrEmpty(type)) {
            return;
        }

        //Creates an instance of the shopping cart object
        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        //Check the type of input posted from the view
        switch (type.toLowerCase()) {
            //Invoke the appropriate function based function
            case "add": add(ctx, session, request); break;
            case "remove": remove(ctx, session, request); break;
            case "delete": delete(ctx, session, request); break;
            default: break;
        }
    }

    @SneakyThrows
    //Get a product to the shopping cart
    private Product getProduct(IoTBayApplicationContext ctx, HttpServletRequest request) {
        final String identifier = request.getParameter("product");
        //Validate if the product id is valid
        if (isNullOrEmpty(identifier) || !ObjectId.isValid(identifier)) {
            reject("Invalid product ID supplied.");
        }

        return ctx.getProducts().findById(identifier);
    }

    @SneakyThrows
    //Add a product to the shopping cart
    private void add(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final Product product = getProduct(ctx, request);

        //validate the product you need to add to the shopping cart
        if (product == null) {
            //Display error message and prevent the product from being added
            reject("Unable to find product to add to your cart.");
        }


        final ShoppingCart cart = ShoppingCartUtil.get(session);

        cart.add(product);

        ShoppingCartUtil.set(session, cart);

        message = "1 of " + cart.quantity(product) + " " + product.getName() + " was added to your cart.";
    }

    @SneakyThrows
    //removes an object from the shopping cart
    private void remove(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final Product product = getProduct(ctx, request);

        //Checks the product in the shopping cart
        if (product == null) {
            //Prevent from removing product because there are no product
            reject("Unable to find product to remove from your cart.");
        }


        final ShoppingCart cart = ShoppingCartUtil.get(session);

        //Validate if the product is in the cart
        if (!cart.contains(product)) {
            reject("Cart does not contain " + product.getName() + ".");
        }

        final int quantity = cart.quantity(product);

        //invoke remove cart
        cart.remove(product);

        ShoppingCartUtil.set(session, cart);

        message = "1 of " + quantity + " " + product.getName() + " was removed from your cart.";
    }

    @SneakyThrows
    //Removes a product from the shopping cart
    private void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final Product product = getProduct(ctx, request);

        if (product == null) {
            reject("Unable to find product to remove delete your cart.");
        }

        final ShoppingCart cart = ShoppingCartUtil.get(session);

        if (!cart.contains(product)) {
            reject("Cart does not contain " + product.getName() + ".");
        }

        //Quantity of the product in the cart
        final int quantity = cart.quantity(product);

        cart.remove(product);

        ShoppingCartUtil.set(session, cart);

        //Display message
        message = "All " + quantity + " " + product.getName() + " were removed from your cart.";
    }
}
