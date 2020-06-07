package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.action.UnauthenticatedAction;
import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.order.OrderRepository;
import au.edu.uts.isd.iotbay.repository.payment.PaymentMethodRepository;
import au.edu.uts.isd.iotbay.repository.product.ProductRepository;
import au.edu.uts.isd.iotbay.repository.shipment.ShipmentRepository;
import au.edu.uts.isd.iotbay.util.AuthenticationUtil;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.time.LocalDate;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

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
    private void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request){
        /*final User user = authenticate(session);
        LocalDate date = LocalDate.now();
        final ShipmentRepository shipmentRepository= ctx.getShipments();
        Shipment shipment = shipmentRepository.findById(ObjectId);
        final PaymentMethodRepository paymentMethodRepository = ctx.getPayments();
        PaymentMethod paymentMethod = paymentMethodRepository.findById(ObjectId);*/

    }

}
