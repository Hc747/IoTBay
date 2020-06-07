<%@ page import="au.edu.uts.isd.iotbay.model.payment.PaymentMethod" %>
<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    final User user = AuthenticationUtil.user(session);
    final List<PaymentMethod> payments = user.getPayments();
    request.setAttribute("methods", payments);
    //TODO: link for creating payment methods
%>
<t:layout>
    <jsp:body>
        <div class="container">
            <c:if test="${(methods == null) or (methods.isEmpty())}">
                You don't have any payment methods.
            </c:if>
            <c:if test="${not (methods == null) and (not methods.isEmpty())}">
                <table class="table table-bordered" style="text-align: center">
                    <thead>
                        <tr>
                            <th scope="col">Type</th>
                            <th scope="col">Details</th>
                            <th scope="col">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="method" items="${methods}">
                            <tr>
                                <td>${method.type()}</td>
                                <td>
                                    <table class="table table-bordered">
                                        <c:if test="${method.type().name().equalsIgnoreCase('PAYPAL')}">
                                            <thead>
                                                <th colspan="4">Token</th>
                                            </thead>
                                            <tbody>
                                                <td>${method.token}</td>
                                            </tbody>
                                        </c:if>
                                        <c:if test="${method.type().name().equalsIgnoreCase('CREDIT_CARD')}">
                                            <thead>
                                                <th>Number</th>
                                                <th>Holder</th>
                                                <th>CVV</th>
                                                <th>Expiration</th>
                                            </thead>
                                            <tbody>
                                                <td>${method.number}</td>
                                                <td>${method.holder}</td>
                                                <td>${method.cvv}</td>
                                                <td>${method.expiration}</td>
                                            </tbody>
                                        </c:if>
                                    </table>
                                </td>
                                <td><a href="edit/?id=${method.id}">Edit</a> | <a href="?action=payment&type=delete&id=${method.id}">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </jsp:body>
</t:layout>
