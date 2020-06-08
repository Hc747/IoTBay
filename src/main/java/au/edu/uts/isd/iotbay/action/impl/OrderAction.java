package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.cart.ShoppingCart;
import au.edu.uts.isd.iotbay.model.invoice.Invoice;
import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.order.OrderRepository;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import au.edu.uts.isd.iotbay.util.Misc;
import au.edu.uts.isd.iotbay.util.ShoppingCartUtil;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

public class OrderAction extends Action {
    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");

        if (isNullOrEmpty(type)) {
            return;
        }
        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        switch (type.toLowerCase()) {
            case "create": create(ctx, session, request);break;
            default: break;
        }
    }

    @SneakyThrows
    private void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final String identifier = request.getParameter("id");
        if (isNullOrEmpty(identifier)) {
            reject("Order Id not found");
        }
        if (!ObjectId.isValid(identifier)) {
            reject("Order Id invalid.");
        }
        final OrderRepository repository= ctx.getOrders();
        final Order order = repository.findById(identifier);

        if(order == null) {
            reject("Order Id Invalid.");
        }

        repository.delete(order);
        message = "Order was successfully  deleted";
    }

    @SneakyThrows
    private void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final String identifier = request.getParameter("id");

        if (isNullOrEmpty(identifier)) {
            reject("No Order Id found");
        }

        if (!ObjectId.isValid(identifier)) {
            reject("Order Id was not valid.");
        }

        final OrderRepository repository = ctx.getOrders();
        final Order order = repository.findById(identifier);

    }

    @SneakyThrows
    private void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final ShoppingCart cart = ShoppingCartUtil.get(session);
        final User user = AuthenticationUtil.user(session);
        final boolean guest = user == null;
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String paymentMethod = request.getParameter("payment_method");

        if (cart.isEmpty()) {
            reject("Your cart is empty.");
        }

        //TODO: validate input
        if (isNullOrEmpty(name) || isNullOrEmpty(email)) {
            reject("You must supply an email address or name.");
        }
        final Invoice invoice = ShoppingCartUtil.invoice(cart, request.getParameter("email"), request.getParameter("name"));

        final PaymentMethod payment;

        if (guest) {
            payment = ctx.getPayments().findById(request.getParameter("payment_method"));//TODO: validate input
        } else {
            payment = Misc.findById(user.getPayments(), request.getParameter("payment_method"));//TODO: validate input
        }

        if (payment == null) {
            reject("Unable to find payment method.");
        }

        boolean modified = false;

        for (OrderProduct item : cart.products()) {
            final Product product = ctx.getProducts().findById(item.getProduct().getId());

            if (product == null) {
                modified = true;
                cart.delete(item.getProduct());
            } else if (product.getQuantity() < cart.quantity(product)) {
                modified = true;
                cart.set(product, product.getQuantity());
            }
        }

        if (modified || cart.isEmpty()) {
            reject("Some of the products you selected are not currently available at the requested quantities.");
        }

        final List<Product> products = cart.products().stream().map(item -> {
            //decrement available products in the database by the requested amount
            final Product product = ctx.getProducts().findById(item.getProduct().getId());
            product.setQuantity(product.getQuantity() - item.getQuantity());
            ctx.getProducts().update(product);

            //update quantity of product in cart to requested amount
            item.getProduct().setQuantity(item.getQuantity());
            return item.getProduct();
        }).collect(Collectors.toList());

        final Order order = ctx.getOrders().create(new Order(invoice, payment, Order.Status.CREATED, products, LocalDate.now()));

        if (order == null) {
            //TODO: increment products by requested amount in case of failure // rollback
            reject("Unable to place your order.");
        }

        if (!guest) {
            //save the order to the user profile if not a guest
            user.getOrders().add(order);
            if (ctx.getUsers().update(user) == null) {
                reject("Unable to place your order.");
            }

            ctx.log(user, "Placed an order.");
        }

        ShoppingCartUtil.set(session, new ShoppingCart());

        message = "Your order was placed. ID: " + order.getId();
    }
}
