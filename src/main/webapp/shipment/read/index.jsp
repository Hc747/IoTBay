<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/06/2020
  Time: 3:40 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="au.edu.uts.isd.iotbay.model.shipment.Shipment" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.shipment.ShipmentRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="org.bson.types.ObjectId" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%
final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
final ShipmentRepository repository = ctx.getShipments();
final String id = request.getParameter("id");
final Shipment shipment;

if(isNullOrEmpty(id) || !ObjectId.isValid(id))
{
    shipment = null;
} else {
    shipment = repository.findById(id);
}

        request.setAttribute("shipment", shipment);
%>
<t:layout>
    <jsp:body>
<div class ="container">
    <form method="POST">
        <label>
            Please enter shipment ID
        </label>
        <input type="text" id="id", name="id", placeholder="Enter ID">
        <input type="submit" id="submit" name="submit" value="submit">
    </form>
        <c:if test="${shipemnt == null}">
            Shipment ID is not found !
        </c:if>
    <c:if test="${shipment != null}">
        <div class="form-group">
            Shipment ID: ${shipment.id}
        </div>
        <div class="form-group">
            Shipment method: ${shipment.method}
        </div>
        <div class="form-group">
            Shipment address: ${shipment.address.address}
        </div>
        <div class="form-group">
            Shipment postcode: ${shipment.address.postcode}
        </div>
        <div class="form-group">
            Estimated date: ${shipment.date}
        </div>
    </c:if>
        </div>
    </jsp:body>
</t:layout>
