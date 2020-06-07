package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.repository.shipment.ShipmentRepository;
import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.model.address.Address;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

public class ShipmentAction extends Action {


    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String type = request.getParameter("type");

        if (isNullOrEmpty(type)) {
            return;
        }
        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);

        switch (type.toLowerCase()) {
            case "create": create(ctx, session, request);break;
            case "update": create(ctx, session, request);break;
            case "delete": create(ctx, session, request);break;
        }


    }

    @SneakyThrows
    protected void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request)
    {

        String name = request.getParameter("name");
        String method = request.getParameter("method");
        String address = request.getParameter("address");
        String postcode = request.getParameter("postcode");

        Address a =  new Address(address, postcode);

        int days = 0;
        switch(method)
        {
            case "free": days = 10;
            break;
            case "standard": days = 5;
            break;
            case "express": days = 2;
            break;
            default : break;
        }

        LocalDate delivery = LocalDate.now().atStartOfDay().plus(days, ChronoUnit.DAYS).toLocalDate();


        if (isNullOrEmpty(name) || isNullOrEmpty(method) || isNullOrEmpty(address) || isNullOrEmpty(postcode)) {
            reject("Please fill in the blank");
        }


        final ShipmentRepository repository = ctx.getShipments();
        final Shipment shipment = repository.create(new Shipment(name, a, method, delivery));
        session.setAttribute("shipment", shipment);

        message = "Shipment has been saved";

    }
@SneakyThrows
    protected void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request)
    {
        String name = request.getParameter("name");
        String method = request.getParameter("method");
        String address = request.getParameter("address");
        String postcode = request.getParameter("postcode");

        Address a =  new Address(address, postcode);

        int days = 0;
        switch(method)
        {
            case "free": days = 10;
                break;
            case "standard": days = 5;
                break;
            case "express": days = 2;
                break;
            default : break;
        }

        LocalDate delivery = LocalDate.now().atStartOfDay().plus(days, ChronoUnit.DAYS).toLocalDate();


        if (isNullOrEmpty(name) || isNullOrEmpty(method) || isNullOrEmpty(address) || isNullOrEmpty(postcode)) {
            reject("Please fill in the blank");
        }


        final ShipmentRepository repository = ctx.getShipments();
        final Shipment shipment = repository.create(new Shipment(name, a, method, delivery));
        session.setAttribute("shipment", shipment);

        message = "Shipment has been updated";
    }

    @SneakyThrows
    protected void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) throws ActionException {
        final String id = request.getParameter("id");

        if (isNullOrEmpty(id)) {
            reject("Shipment not found");
        }
        if (!ObjectId.isValid(id)) {
            reject("Shipment invaild");
        }

        final ShipmentRepository repository= ctx.getShipments();
        final Shipment shipment = repository.findById(id);

        if(shipment == null) {
            reject("Shipment invaild");
        }

        repository.delete(shipment);
        message = "Shipment was deleted";
    }

@SneakyThrows
    protected void search(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) throws ActionException {
        final String id = request.getParameter("sid");

        if (isNullOrEmpty(id)) {
            reject("Shipment not found");
        }
        if (!ObjectId.isValid(id)) {
            reject("Shipment invaild");
        }

        final ShipmentRepository repository= ctx.getShipments();
        final Shipment shipment = repository.findById(id);

        if(shipment == null) {
            reject("Shipment invaild");
        }
    }
}

