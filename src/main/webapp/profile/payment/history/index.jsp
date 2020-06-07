<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.model.order.Order" %>
<%@ page import="au.edu.uts.isd.iotbay.model.payment.PaymentMethod" %>
<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="org.bson.types.ObjectId" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty" %>
<%@ page import="java.util.stream.Stream" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    final User user = AuthenticationUtil.user(session);
    Stream<Order> orders = user.getOrders().stream();
    final Set<PaymentMethod> methods = user.getOrders().stream().map(Order::getPayment).collect(Collectors.toSet());
    final Set<LocalDate> dates = user.getOrders().stream().map(Order::getDate).collect(Collectors.toSet());

    final String identifier = request.getParameter("id");

    if (!isNullOrEmpty(identifier) && ObjectId.isValid(identifier)) {
        final ObjectId id = new ObjectId(identifier);
        orders = orders.filter(order -> order.getPayment().getId().equals(id));
    }

    if (!isNullOrEmpty(request.getParameter("date"))) {
        final LocalDate date;
        LocalDate date1;
        try {
            date1 = LocalDate.parse(request.getParameter("date"));
        } catch (Exception e) {
            date1 = null;
        }

        date = date1;
        if (date != null) {
            orders = orders.filter(order -> order.getDate().atStartOfDay().equals(date.atStartOfDay()));
        }
    }

    request.setAttribute("orders", orders.collect(Collectors.toList()));
    request.setAttribute("methods", methods);
    request.setAttribute("dates", dates);
    request.setAttribute("back", Constants.path(true, "profile", "payment"));
%>
<t:layout>
    <jsp:body>
        <div class="container">
            <c:if test="${(orders == null)}">
                <h1 class="jumbotron-heading" style="text-align: center">You don't have an order history.</h1>
            </c:if>
            <c:if test="${orders != null}">
                <h1 class="jumbotron-heading" style="text-align: center">Your payment history</h1>

                <form action="" method="GET">
                    <div class="form-group">
                        <label for="id">Payment ID</label>
                        <select name="id" id="id">
                            <option disabled selected value> -- select an option -- </option>
                            <c:forEach var="method" items="${methods}">
                                <option value="${method.id}">${method.type()} - <c:if test="${method.type().name().equalsIgnoreCase('CREDIT_CARD')}">${method.number}</c:if><c:if test="${method.type().name().equalsIgnoreCase('PAYPAL')}">${method.token}</c:if></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="date">Date</label>
                        <input type="date" class="form-control" id="date" name="date" placeholder="Date">
                    </div>
                    <button type="submit" class="btn btn-primary">Filter</button>
                </form>

                <table class="table table-bordered" style="text-align: center">
                    <thead>
                    <tr>
                        <th scope="col">Type</th>
                        <th scope="col">Details</th>
                        <th scope="col">Invoice</th>
                        <th scope="col">Date</th>
                        <th scope="col">Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr>
                            <td>${order.payment.type()}</td>
                            <td>
                                <table class="table table-bordered">
                                    <c:if test="${order.payment.type().name().equalsIgnoreCase('PAYPAL')}">
                                        <thead>
                                            <th colspan="4">Token</th>
                                        </thead>
                                        <tbody>
                                            <td>${order.payment.token}</td>
                                        </tbody>
                                    </c:if>
                                    <c:if test="${order.payment.type().name().equalsIgnoreCase('CREDIT_CARD')}">
                                        <thead>
                                            <th>Number</th>
                                            <th>Holder</th>
                                            <th>CVV</th>
                                            <th>Expiration</th>
                                        </thead>
                                        <tbody>
                                            <td>${order.payment.number}</td>
                                            <td>${order.payment.holder}</td>
                                            <td>${order.payment.cvv}</td>
                                            <td>${order.payment.expiration}</td>
                                        </tbody>
                                    </c:if>
                                </table>
                            </td>
                            <td>
                                <table class="table table-bordered">
                                    <thead>
                                        <th>Amount</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                    </thead>
                                    <tbody>
                                        <td>${order.invoice.amount}</td>
                                        <td>${order.invoice.name}</td>
                                        <td>${order.invoice.emailAddress}</td>
                                    </tbody>
                                </table>
                            </td>
                            <td>${order.date}</td>
                            <td>${order.status}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <div class="container d-flex justify-content-between">
                <a href="${back}" type="button">View Payment Methods</a>
            </div>
        </div>
    </jsp:body>
</t:layout>
