package au.edu.uts.isd.iotbay.util;

import au.edu.uts.isd.iotbay.cart.ShoppingCart;
import au.edu.uts.isd.iotbay.model.invoice.Invoice;
import au.edu.uts.isd.iotbay.model.user.User;

import javax.servlet.http.HttpSession;

/**
 * A utility class for interacting with the {@code User}s {@code ShoppingCart} across sessions.
 */
public class ShoppingCartUtil {

    private static final String SESSION_KEY = "shopping-cart";

    //Creating an instance of the shopping cart object
    public static ShoppingCart get(HttpSession session) {
        Object cart = session.getAttribute(SESSION_KEY);
        if (cart instanceof ShoppingCart) {
            return (ShoppingCart) cart;
        }
        ShoppingCart instance = new ShoppingCart();
        set(session, instance);
        return instance;
    }

    //sets the shopping cart object with the session attribute
    public static void set(HttpSession session, ShoppingCart cart) {
        session.setAttribute(SESSION_KEY, cart);
    }

    //Creates an invoice based on teh shopping cart and the user that is registered
    public static Invoice invoice(ShoppingCart cart, User user) {
        return invoice(cart, user.getUsername(), user.getName());
    }

    //Overrides the invoice function with and the user that is not registered
    public static Invoice invoice(ShoppingCart cart, String email, String name) {
        return new Invoice(cart.totalCost(), email, name);
    }
}
