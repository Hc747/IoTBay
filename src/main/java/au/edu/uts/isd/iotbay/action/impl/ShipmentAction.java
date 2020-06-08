package au.edu.uts.isd.iotbay.action.impl;

import au.edu.uts.isd.iotbay.IoTBayApplicationContext;
import au.edu.uts.isd.iotbay.action.Action;
import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.repository.shipment.ShipmentRepository;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
            case "create":
                create(ctx, session, request);
                break;
            case "update":
                update(ctx, session, request);
                break;
            case "delete":
                delete(ctx, session, request);
                break;
            default:
                break;
        }
    }

    @SneakyThrows
    protected void create(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {

        String name = request.getParameter("name");
        String method = request.getParameter("method");
        String address = request.getParameter("address");
        String postcode = request.getParameter("postcode");

        Address a = new Address(address, postcode);

        int days = 0;
        switch (method) {
            case "free":
                days = 10;
                break;
            case "standard":
                days = 5;
                break;
            case "express":
                days = 2;
                break;
            default:
                break;
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
    protected void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        final String identifier = request.getParameter("id");
        String name = request.getParameter("name");
        String method = request.getParameter("method");
        String address = request.getParameter("address");
        String postcode = request.getParameter("postcode");

        Address a = new Address(address, postcode);

        int days = 0;
        switch (method) {
            case "free":
                days = 10;
                break;
            case "standard":
                days = 5;
                break;
            case "express":
                days = 2;
                break;
            default:
                break;
        }

        LocalDate delivery = LocalDate.now().atStartOfDay().plus(days, ChronoUnit.DAYS).toLocalDate();

        if (isNullOrEmpty(identifier)) {
            reject("Shipment ID is not found");
        }

        if (!ObjectId.isValid(identifier)) {
            reject("Shipment ID is not valid");
        }

        final ShipmentRepository repository = ctx.getShipments();
        final Shipment shipment = repository.findById(identifier);

        if (shipment == null) {
            reject("Shipment is not found");
        }

        a.setAddress(address);
        a.setPostcode(postcode);
        shipment.setName(name);
        shipment.setAddress(a);

        final Shipment update = repository.update(shipment);

        message="Shipment has been updated";
        session.setAttribute("update", update);
    }

    @SneakyThrows
    protected void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) throws ActionException {

        final String identifier = request.getParameter("id");

        if (isNullOrEmpty(identifier)) {
            reject("Shipment ID is not found");
        }

        if (!ObjectId.isValid(identifier)) {
            reject("Shipment ID is not valid");
        }

        final ShipmentRepository repository = ctx.getShipments();
        final Shipment shipment = repository.findById(identifier);

        if (shipment == null) {
            reject("Shipment is not found");
        }

        final Shipment delete = repository.delete(shipment);
        message="Shipment has been deleted";
        session.setAttribute("delete", delete);
    }
}

