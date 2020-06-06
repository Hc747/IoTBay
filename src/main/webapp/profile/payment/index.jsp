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
%>
<t:layout>
    <jsp:body>
        <div class="container d-flex justify-content-between">
            <c:if test="${(methods == null) or (methods.isEmpty())}">
                You don't have any payment methods.
            </c:if>
            <c:if test="${not (methods == null) and (not methods.isEmpty())}">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Type</th>
                            <th scope="col">Details</th>
                            <th scope="col">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="method" items="${methods}">
                            <tr class="row">
                                <th scope="row">${method.id}</th>
                                <td class="col-1">${method.type()}</td>
                                <td class="col-9">${method.inline()}</td>
                                <td class="col-1"><a href="?action=payment&type=edit&id=${method.id}">Edit</a> | <a href="?action=payment&type=delete&id=${method.id}">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </jsp:body>
</t:layout>
