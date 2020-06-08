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
<html>
<style>
    table {
        font-family: Arial;
        border-collapse: collapse;
        width: 30%;
    }
    td{
        border: 1px solid;
        text-align: left;
        padding: 2px;
    }
    tr:nth-child(even)
    {
        background-color: darkgrey;
    }
    tr:nth-child(odd)
    {
        background-color: burlywood;
    }
    p
    {
        color: white;
        font-size: 16px;
    }
    input[type=submit]
    {
        background-color: dodgerblue;
        color: white;
        border: none;
    }
</style>
<body>
    <form method="POST">
        <label>
            <h3>Please enter valid shipment ID</h3>
        </label>
        <br>
        <input type="text" id="id", name="id", placeholder="Enter ID">
        <input type="submit" id="submit" name="submit" value="submit">
    </form>
    <br>
<table>
    <tr><td><p>Shipment ID: ${shipment.id}</p></td></tr>
    <tr><td><p>Shipment method: ${shipment.method}</p></td></tr>
    <tr><td><p>Shipment address: ${shipment.address.address}</p></td></tr>
    <tr><td><p>Shipment postcode: ${shipment.address.postcode}</p></td></tr>
    <tr><td><p>Estimated date: ${shipment.date}</p></td></tr>
</table>
</body>
</html>
</t:layout>
