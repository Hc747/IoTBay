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
        //make type to store data that indicates what action will be executed

        if (isNullOrEmpty(type)) {
            return;
        }
        //check if the type has been selected

        final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
        //make object of IoTBayApplicationContext

        //make switch to catch the action from type input
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
        //make variable to catch and store data from index page
        String name = request.getParameter("name");
        String method = request.getParameter("method");
        String address = request.getParameter("address");
        String postcode = request.getParameter("postcode");

        //make object of address, to store variable of address
        Address a = new Address(address, postcode);

        //make days that depends on the shipment method the user have chosen
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

        //catch the current date and plus days
        LocalDate delivery = LocalDate.now().atStartOfDay().plus(days, ChronoUnit.DAYS).toLocalDate();

        //check if any variable is empty, validate all information
        if (isNullOrEmpty(name) || isNullOrEmpty(method) || isNullOrEmpty(address) || isNullOrEmpty(postcode)) {
            reject("Please fill in the blank");
        }

        //make shipment object to store caught information from index page
        final ShipmentRepository repository = ctx.getShipments();
        final Shipment shipment = repository.create(new Shipment(name, a, method, delivery));

        session.setAttribute("shipment", shipment);
        //display id and date of the shipment
        message = "Shipment has been saved, the id is " + shipment.getId() + "the estimated date is " + shipment.getDate();
    }

    @SneakyThrows
    protected void update(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) {
        //make variables to store data
        final String identifier = request.getParameter("id");
        String name = request.getParameter("name");
        String method = request.getParameter("method");
        String address = request.getParameter("address");
        String postcode = request.getParameter("postcode");

        //make object of address, to store variable of address
        Address a = new Address(address, postcode);

        //make days that depends on the shipment method the user have chosen
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

        //catch the current date and plus days
        LocalDate delivery = LocalDate.now().atStartOfDay().plus(days, ChronoUnit.DAYS).toLocalDate();

        //validation and exception handling
        if (isNullOrEmpty(identifier)) {
            reject("Shipment ID is not found");
        }

        if (!ObjectId.isValid(identifier)) {
            reject("Shipment ID is not valid");
        }

        //make shipment object to store caught information from index page
        final ShipmentRepository repository = ctx.getShipments();
        final Shipment shipment = repository.findById(identifier);

        //exception handling
        if (shipment == null) {
            reject("Shipment is not found");
        }

        //set date to current input
        a.setAddress(address);
        a.setPostcode(postcode);
        shipment.setName(name);
        shipment.setAddress(a);
        shipment.setDate(delivery);

        //update and display
        repository.update(shipment);
        message="Shipment has been updated";
    }

    @SneakyThrows
    protected void delete(IoTBayApplicationContext ctx, HttpSession session, HttpServletRequest request) throws ActionException {
        //make variable to store id
        final String identifier = request.getParameter("id");

        //validation and exception handling
        if (isNullOrEmpty(identifier)) {
            reject("Shipment ID is not found");
        }

        if (!ObjectId.isValid(identifier)) {
            reject("Shipment ID is not valid");
        }

        //make shipment object to store caught information from index page
        final ShipmentRepository repository = ctx.getShipments();
        final Shipment shipment = repository.findById(identifier);

        //exception handling
        if (shipment == null) {
            reject("Shipment is not found");
        }

        //delete and display
        repository.delete(shipment);
        message="Shipment has been deleted";
    }
}

