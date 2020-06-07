package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.payment.CreditCardPaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaypalPaymentMethod;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.payment.PaymentMethodRepository;
import au.edu.uts.isd.iotbay.repository.user.UserRepository;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import au.edu.uts.isd.iotbay.util.Misc;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

public class PaymentAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final String type = request.getParameter("type");

        if (isNullOrEmpty(type)) {
            return;
        }

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        switch (type.toLowerCase()) {
            case "delete": delete(ctx, session, request); break;
            case "update": update(ctx, session, request); break;
            case "create": create(ctx, session, request); break;
            default: break;
        }
    }

    @SneakyThrows
    private PaymentMethod build(HttpServletRequest request) {
        final String impl = request.getParameter("impl");
        final PaymentMethod.Type type = PaymentMethod.Type.findByName(impl);

        if (type == null) {
            reject("Unknown payment method type.");
        }

        final PaymentMethod method;

        //TODO: validation of inputs

        switch (type) {
            case CREDIT_CARD:
                String number = request.getParameter("number");
                String holder = request.getParameter("holder");
                String cvv = request.getParameter("cvv");
                String date = request.getParameter("expiration");
                method = new CreditCardPaymentMethod(null, number, holder, cvv, LocalDate.parse(date));
                break;
            case PAYPAL:
                String token = request.getParameter("token");
                method = new PaypalPaymentMethod(null, token);
                break;
            default: reject("Unhandled payment method type: " + type); return null;
        }

        return method;
    }

    @SneakyThrows
    private void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final User user = authenticate(session);
        final String identifier = request.getParameter("id");

        if (isNullOrEmpty(identifier) || !ObjectId.isValid(identifier)) {
            reject("Invalid ID provided.");
        }

        final PaymentMethod method = Misc.findById(user.getPayments(), identifier);

        if (method == null) {
            reject("Unable to find payment method with ID: " + identifier);
        }

        final UserRepository users = ctx.getUsers();

        user.getPayments().remove(method);

        if (users.update(user) == null) {
            reject("Unable to unlink payment method from your account.");
        }

        message = "Successfully removed payment method.";
    }

    @SneakyThrows
    private void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final User user = authenticate(session);
        final String identifier = request.getParameter("id");

        if (isNullOrEmpty(identifier) || !ObjectId.isValid(identifier)) {
            reject("Invalid ID provided.");
        }

        final PaymentMethod method = Misc.findById(user.getPayments(), identifier);

        if (method == null) {
            reject("Unable to find payment method with ID: " + identifier);
        }

        final PaymentMethod updated = build(request);
        updated.setId(method.getId());

        user.getPayments().set(user.getPayments().indexOf(method), updated);

        final PaymentMethodRepository payments = ctx.getPayments();
        final UserRepository users = ctx.getUsers();

        if (payments.update(updated) == null || users.update(user) == null) {
            reject("Unable to update your payment method details.");
        }

        message = "Successfully updated payment method.";
    }

    @SneakyThrows
    private void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final PaymentMethodRepository payments = ctx.getPayments();
        final PaymentMethod method = payments.create(build(request));

        if (method == null) {
            reject("Unable to create payment method.");
        }

        final boolean authenticated = AuthenticationUtil.isAuthenticated(session);

        if (authenticated) {
            final User user = authenticate(session);
            final UserRepository users = ctx.getUsers();
            user.getPayments().add(method);

            if (users.update(user) == null) {
                reject("Unable to link payment method to your account.");
            }
        }

        message = "Successfully created payment method.";
    }

    @SneakyThrows
    private User authenticate(HttpSession session) {
        User user = AuthenticationUtil.user(session);
        if (user == null) {
            reject("You cannot do this while logged out.");
        }
        return user;
    }
}
