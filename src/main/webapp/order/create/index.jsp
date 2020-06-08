<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    final User user = AuthenticationUtil.user(session);
    request.setAttribute("user", user);
%>
<t:layout>
    <jsp:body>
        <h1 class="jumbotron-heading" style="text-align: center">Create Order</h1>
        <div class="container">
            <form action="?action=order&type=create" method="POST">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" value="${user.username}" required/>
                </div>
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" name="name" id="name" value="${user.name}" required/>
                </div>
                <c:if test="${user == null}">
                    <div class="form-group">
                        <label for="payment_method">Payment Method ID</label>
                        <input type="text" name="payment_method" id="payment_method" required/>
                    </div>
                </c:if>
                <c:if test="${user != null}">
                    <div class="form-group">
                        <label for="payment_method">Select a Payment Method</label>
                        <select id="payment_method" name="payment_method" required>
                            <c:forEach var="method" items="${user.payments}">
                                <option value="${method.id}">${method.type()} - <c:if test="${method.type().name().equalsIgnoreCase('PAYPAL')}">${method.token}</c:if><c:if test="${method.type().name().equalsIgnoreCase('CREDIT_CARD')}">${method.number}</c:if></option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Submit Order</button>
                </div>
            </form>
            <a href="/iotbay/shipment"><button style="background-color: dodgerblue; border: white; color: white; padding: 5px; border-radius: 2px">Go to Shipment</button></a>
            <a href="/iotbay/order/delete"><button style="background-color: dodgerblue; border: white; color: white; padding: 5px; border-radius: 2px">Delete Order</button></a>
            <a href="/iotbay/order/read"><button style="background-color: dodgerblue; border: white; color: white; padding: 5px; border-radius: 2px">Display Order</button></a>
        </div>
    </jsp:body>
</t:layout>