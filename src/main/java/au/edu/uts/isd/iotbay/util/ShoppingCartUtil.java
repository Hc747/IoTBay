package au.edu.uts.isd.iotbay.util;

import au.edu.uts.isd.iotbay.cart.ShoppingCart;

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
}
