package au.edu.uts.isd.iotbay.util;

import au.edu.uts.isd.iotbay.cart.ShoppingCart;
import au.edu.uts.isd.iotbay.model.invoice.Invoice;
import au.edu.uts.isd.iotbay.model.user.User;

import javax.servlet.http.HttpSession;

public class ShoppingCartUtil {

    private static final String SESSION_KEY = "shopping-cart";

    public static ShoppingCart get(HttpSession session) {
        Object cart = session.getAttribute(SESSION_KEY);
        if (cart instanceof ShoppingCart) {
            return (ShoppingCart) cart;
        }
        ShoppingCart instance = new ShoppingCart();
        set(session, instance);
        return instance;
    }

    public static void set(HttpSession session, ShoppingCart cart) {
        session.setAttribute(SESSION_KEY, cart);
    }

    public static Invoice invoice(ShoppingCart cart, User user) {
        return invoice(cart, user.getUsername(), user.getName());
    }

    public static Invoice invoice(ShoppingCart cart, String email, String name) {
        return new Invoice(cart.totalCost(), email, name);
    }
}
