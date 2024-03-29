<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    request.setAttribute("user", AuthenticationUtil.user(session));
    request.setAttribute("endpoint", Constants.path(true, "profile", "payment"));
    request.setAttribute("back", Constants.path(true, "profile", "payment"));
%>
<t:layout>
    <jsp:body>
        <div class="container d-flex justify-content-between">
            <form action="${endpoint}?action=payment&type=create" method="post">
                <h1 class="jumbotron-heading" style="text-align: center">Create Credit Card Payment Method</h1>
                <input type="hidden" name="impl" value="CREDIT_CARD"/>
                <div class="form-group">
                    <label for="number">Card Number: </label>
                    <input type="text" class="form-control" name="number" id="number" required>
                </div>
                <div class="form-group">
                    <label for="cvv">CVV: </label>
                    <input type="text" class="form-control" name="cvv" id="cvv" required>
                </div>
                <div class="form-group">
                    <label for="holder">Holder Name: </label>
                    <input type="text" class="form-control" name="holder" id="holder" value="${user.name}" required>
                </div>
                <div class="form-group">
                    <label for="expiration">Expiration Date: </label>
                    <input type="date" class="form-control" name="expiration" id="expiration" required>
                </div>
                <div class="form-group">
                    <input class="submit" type="submit">
                </div>
            </form>

            <form action="${endpoint}?action=payment&type=create" method="post">
                <h1 class="jumbotron-heading" style="text-align: center">Create PayPal Payment Method</h1>
                <input type="hidden" name="impl" value="PAYPAL"/>
                <div class="form-group">
                    <label for="token">Paypal Token: </label>
                    <input type="text" class="form-control" name="token" id="token" required>
                </div>
                <div class="form-group">
                    <input class="submit" type="submit">
                </div>
            </form>
        </div>
        <div class="container d-flex justify-content-between">
            <a href="${back}" type="button">View Payment Methods</a>
        </div>
    </jsp:body>
</t:layout>
