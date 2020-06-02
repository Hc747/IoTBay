<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.payment.PaymentMethodRepository" %>
<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final PaymentMethodRepository repository = context.getPayments();
    final User user = AuthenticationUtil.user(session);
    request.setAttribute("methods", repository.findAllByUser(user));
%>
<t:layout>
    <jsp:body>
        <div class="container d-flex justify-content-between">
            <c:if test="${methods.isEmpty()}">
                You don't have any payment methods.
            </c:if>
            <c:if test="${not methods.isEmpty()}">
                <div class="container">
                    <div class="row">
                        <div class="col-1">ID</div>
                        <div class="col-1">Type</div>
                        <div class="col-9">Details</div>
                        <div class="col-1">Actions</div>
                    </div>
                    <c:forEach var="method" items="${methods}">
                        <div class="row">
                            <div class="col-1">${method.id}</div>
                            <div class="col-1">${method.type()}</div>
                            <div class="col-9">${method.inline()}</div>
                            <div class="col-1"><a href="?action=payment&type=edit&id=${method.id}">Edit</a> | <a href="?action=payment&type=delete&id=${method.id}">Delete</a></div>
                        </div>
                    </c:forEach>
                </div>
            </c:if>
        </div>
    </jsp:body>
</t:layout>
