<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 7/06/2020
  Time: 3:40 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="au.edu.uts.isd.iotbay.model.order.Order" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.order.OrderRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="org.bson.types.ObjectId" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%
final IoTBayApplicationContext ctx = IoTBayApplicationContext.getInstance(application);
final OrderRepository repository = ctx.getOrders();
final String id = request.getParameter("id");
final Order order;

if(isNullOrEmpty(id) || !ObjectId.isValid(id))
{
    order = null;
} else {
    order = repository.findById(id);
}

        request.setAttribute("order", order);
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
        <h4>Your Shipment details are below: </h4>
    </form>
    <c:if test="${order != null}">
        <div class="form-group">
            Shipment ID: ${order.id}
        </div>
        <div class="form-group">
            Order Status: ${order.status}
        </div>
        <div class="form-group">
            Order Amount: ${order.invoice.amount}
        </div>
        <div class="form-group">
            Order Placed On: ${order.date}
        </div>
    </c:if>
        </div>
    </jsp:body>
</t:layout>
