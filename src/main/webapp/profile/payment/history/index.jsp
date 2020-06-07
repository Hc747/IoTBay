<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.model.order.Order" %>
<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    final User user = AuthenticationUtil.user(session);
    final List<Order> orders = user.getOrders();
    request.setAttribute("orders", orders);
    request.setAttribute("back", Constants.path(true, "profile", "payment"));
%>
<t:layout>
    <jsp:body>
        <div class="container">
            <c:if test="${(orders == null) or (orders.isEmpty())}">
                <h1 class="jumbotron-heading" style="text-align: center">You don't have an order history.</h1>
            </c:if>
            <c:if test="${not (orders == null) and (not orders.isEmpty())}">
                <h1 class="jumbotron-heading" style="text-align: center">Your payment history</h1>
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
