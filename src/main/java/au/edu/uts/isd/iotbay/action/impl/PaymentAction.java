package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.payment.CreditCardPaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaypalPaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.UserPaymentMethod;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.payment.PaymentMethodRepository;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;

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
            case "create": create(ctx, session, request); break;
            default: break;
        }
    }

    @SneakyThrows
    private void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final User user = authenticate(session);

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
                String date = request.getParameter("date"); //TODO: convert to date
                method = new CreditCardPaymentMethod(null, number, holder, cvv, new Date(System.currentTimeMillis())); //TODO: use converted date
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

        final UserPaymentMethod association = repository.associate(user, record);

        if (association == null) {
            reject("Unable to link payment method to your account.");
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
