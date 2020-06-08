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

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        switch (type.toLowerCase()) {
            case "add": add(ctx, session, request); break;
            case "remove": remove(ctx, session, request); break;
            case "delete": delete(ctx, session, request); break;
            default: break;
        }
    }

    @SneakyThrows
    private Product getProduct(IoTBayApplicationContext ctx, HttpServletRequest request) {
        final String identifier = request.getParameter("product");

        if (isNullOrEmpty(identifier) || !ObjectId.isValid(identifier)) {
            reject("Invalid product ID supplied.");
        }

        return ctx.getProducts().findById(identifier);
    }

    @SneakyThrows
    private void add(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final Product product = getProduct(ctx, request);

        if (product == null) {
            reject("Unable to find product to add to your cart.");
        }

        final ShoppingCart cart = ShoppingCartUtil.get(session);

        cart.add(product);

        ShoppingCartUtil.set(session, cart);

        message = "1 of " + cart.quantity(product) + " " + product.getName() + " was added to your cart.";
    }

    @SneakyThrows
    private void remove(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final Product product = getProduct(ctx, request);

        if (product == null) {
            reject("Unable to find product to remove from your cart.");
        }

        final ShoppingCart cart = ShoppingCartUtil.get(session);

        if (!cart.contains(product)) {
            reject("Cart does not contain item.");
        }

        cart.remove(product);

        ShoppingCartUtil.set(session, cart);

        message = "1 of " + cart.quantity(product) + " " + product.getName() + " was removed from your cart.";
    }

    @SneakyThrows
    private void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final Product product = getProduct(ctx, request);

        if (product == null) {
            reject("Unable to find product to remove delete your cart.");
        }

        final ShoppingCart cart = ShoppingCartUtil.get(session);

        if (!cart.contains(product)) {
            reject("Cart does not contain item.");
        }

        cart.remove(product);

        ShoppingCartUtil.set(session, cart);

        message = "All " + cart.quantity(product) + " " + product.getName() + " were removed from your cart.";
    }
}
