package au.edu.uts.isd.iotbay.util;

import au.edu.uts.isd.iotbay.cart.ShoppingCart;
import au.edu.uts.isd.iotbay.model.invoice.Invoice;
import au.edu.uts.isd.iotbay.model.user.User;

import javax.servlet.http.HttpSession;

public class ShoppingCartUtil {

    private static final String SESSION_KEY = "shopping-cart";

    public static ShoppingCart cart(HttpSession session) {
        Object cart = session.getAttribute(SESSION_KEY);
        if (cart instanceof ShoppingCart) {
            return (ShoppingCart) cart;
        }
        ShoppingCart instance = new ShoppingCart();
        session.setAttribute(SESSION_KEY, instance);
        return instance;
    }

    public static Invoice invoice(ShoppingCart cart, User user) {
        final String[] names = user.names();
        return invoice(cart, user.getUsername(), names[0], names[1]);
    }

    public static Invoice invoice(ShoppingCart cart, String email, String firstname, String lastname) {
        return new Invoice(cart.totalCost(), email, firstname, lastname);
    }
}
