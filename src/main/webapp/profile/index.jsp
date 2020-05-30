<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    User user = AuthenticationUtil.user(session);
    request.setAttribute("user", user);
%>
<t:layout>
    <jsp:body>
        <div class="container d-flex justify-content-between">
            <a href="payment/">View payment details.</a>
        </div>
        <div class="container d-flex justify-content-between">
            <a href="userlogs/">Your logs.</a>
        </div>
        <br>

        <div class="container d-flex justify-content-between">
            <form action="?action=user&type=update" method="post">
                <div class="form-group">
                    <label for="name">Full Name: </label>
                    <input type="text" class="form-control" name="name" id="name" value="${user.name}">
                </div>
                <div class="form-group">
                    <label for="username">Email Address: </label>
                    <input type="email" class="form-control" name="username" id="username" value="${user.username}">
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number: </label>
                    <input type="tel" class="form-control" name="phone" id="phone" value="${user.phone}">
                </div>
                <div class="form-group">
                    <label for="role">Role: </label>
                    <input type="tel" class="form-control" id="role" readonly value="${user.role}">
                </div>
                <div class="form-group">
                    <p><small><b>Account created: </b>${user.created}</small></p>
                </div>
                <div class="form-group">
                    <p><small><b>Account verified: </b>${user.verified}</small></p>
                </div>
                <div class="form-group">
                    <label for="disable">Disable account: </label>
                    <input type="checkbox" name="disable" id="disable">
                </div>
                <div class="form-group">
                    <input class="submit" type="submit">
                </div>
            </form>
        </div>
    </jsp:body>
</t:layout>
