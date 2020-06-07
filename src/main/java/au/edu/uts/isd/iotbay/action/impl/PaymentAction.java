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
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final String type = request.getParameter("type");

        if (isNullOrEmpty(type)) {
            return;
        }

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        //TODO(mathew): dispatch the other actions
        switch (type.toLowerCase()) {
            case "delete":  delete(ctx, session, request); break;
            case "update": break;
            case "create": create(ctx, session, request); break;
            default: break;
        }
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
            reject("Unable to update account. Please try again.");
        }

        message = "Payment method with ID " + identifier + " successfully removed from your account.";
    }

    @SneakyThrows
    private void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
//        final User user = authenticate(session);

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
                String date = request.getParameter("date");
                method = new CreditCardPaymentMethod(null, number, holder, cvv, LocalDate.parse(date));
                break;
            case PAYPAL:
                String token = request.getParameter("token");
                method = new PaypalPaymentMethod(null, token);
                break;
            default: reject("Unhandled payment method type."); return;
        }

        final PaymentMethodRepository repository = ctx.getPayments();
        final PaymentMethod record = repository.create(method);

        if (record == null) {
            reject("Unable to create payment method.");
        }

        //TODO: implement
//        final UserPaymentMethod association = repository.associate(user, record);

//        if (association == null) {
//            reject("Unable to link payment method to your account.");
//        }

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
