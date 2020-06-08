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

//Order inherits from abstract class Action and provides implementation
public class OrderAction extends Action {
    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Gets the post request from the view
        //Determines what function is invoked
        String type = request.getParameter("type");
        //Validate the post data from the type
        if (isNullOrEmpty(type)) {
            return;
        }
        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        //Check what action is the user trying to perform
        //Convert all submission to lower case and convert
        switch (type.toLowerCase()) {
            //When the type post value is create than invoke create method
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
    //Method to create an order in the database
    private void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        //Create a shopping cart object with the session data
        final ShoppingCart cart = ShoppingCartUtil.get(session);

        //Check whether the cart has products (is it empty?)
        if (cart.isEmpty()) {
            //Don't submit any order if the cart is empty
            //Display error message
            reject("Your cart is empty.");
        }

        //Authenticates the user action
        final User user = AuthenticationUtil.user(session);
        final boolean guest = user == null;
        //Store posted values from the view to be validated
        final String email = request.getParameter("email");
        final String name = request.getParameter("name");
        final String method = request.getParameter("payment_method");

        //Validate the input from the user, whether they have input an email, payment method
        if (isNullOrEmpty(name) || isNullOrEmpty(email) || isNullOrEmpty(method) || !ObjectId.isValid(method)) {
            //Display error message
            reject("You must supply an email address, name and payment method id.");
        }

        //Create an invoice object using the ShoppingCartUtil
        final Invoice invoice = ShoppingCartUtil.invoice(cart, email, name);
        //Create payment method object
        final PaymentMethod payment;
        //if the the user is a guest
        if (guest) {
            payment = ctx.getPayments().findById(method);
        } else {
            //If the user is a registered than get existing payment methods
            payment = Misc.findById(user.getPayments(), method);
        }

        if (payment == null) {
            reject("Unable to find payment method.");
        }

        boolean modified = false;
        //Check for each item in the cart
        for (OrderProduct item : cart.products()) {
            final Product product = ctx.getProducts().findById(item.getProduct().getId());

            if (product == null) {
                modified = true;
                cart.delete(item.getProduct());
                //Check if the product quantity in the cart is less than the product quantity in the product
                //Make sure the customer does not order too many items
            } else if (product.getQuantity() < cart.quantity(product)) {
                modified = true;
                cart.set(product, product.getQuantity());
            }
        }

        //If the cart is empty and the product order amount is too much than prevent the order
        if (modified || cart.isEmpty()) {
            //Display error message
            reject("Some of the products you selected are not currently available at the requested quantities.");
        }

        //List of products in the cart
        final List<Product> products = cart.products().stream().map(item -> {
            //decrement available products in the database by the requested amount by customer
            final Product product = ctx.getProducts().findById(item.getProduct().getId());
            //Set the quantity of the product (product InStock - product amount requested by customer
            product.setQuantity(product.getQuantity() - item.getQuantity());
            ctx.getProducts().update(product);

            //update quantity of product in cart to requested amount
            item.getProduct().setQuantity(item.getQuantity());
            return item.getProduct();
        }).collect(Collectors.toList());

        //Order object with values
        final Order order = ctx.getOrders().create(new Order(invoice, payment, Order.Status.CREATED, products, LocalDate.now()));

        //if the the order object is null than do not place order and display error message
        if (order == null) {
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

        //success message with the orderId
        message = "Your order was placed. ID: " + order.getId();
    }
}
