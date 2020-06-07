package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.action.UnauthenticatedAction;
import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.order.OrderRepository;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;

import static au.edu.uts.isd.iotbay.util.Validator.Patterns.*;
import static au.edu.uts.isd.iotbay.util.Validator.Patterns.OBJECT_DESCRIPTION_PATTERN;
import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;
import static au.edu.uts.isd.iotbay.util.Validator.matches;

public class OrderAction extends Action {
    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String identifier = request.getParameter("id");

        if (isNullOrEmpty(identifier)) {
            reject("The Order ID was not found");
        }

        IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        OrderRepository orders = ctx.getOrders();
        Order order = orders.findById(identifier);
        //do business logic here
        orders.update(order);

    }

    @SneakyThrows
    private void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request)
    {
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
}
