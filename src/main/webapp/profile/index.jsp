<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    User user = AuthenticationUtil.user(session);
    request.setAttribute("name", user.getName());
    request.setAttribute("email", user.getUsername());
    request.setAttribute("number", user.getPhone());
    request.setAttribute("role", user.getRole());
    request.setAttribute("created", user.getCreated());
    request.setAttribute("verfied", user.getCreated());
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
            <form action="?action=update" method="post">
                <div class="form-group">
                    <label for="name">Full Name: </label>
                    <input type="text" class="form-control" id="name" value="${name}">
                </div>
                <div class="form-group">
                    <label for="email">Email Address: </label>
                    <input type="email" class="form-control" id="email" value="${email}">
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number: </label>
                    <input type="tel" class="form-control" id="phone" value="${number}">
                </div>
                <div class="form-group">
                    <label for="role">Role: </label>
                    <input type="tel" class="form-control" id="role" readonly value="${role}">
                </div>
                <div class="form-group">
                    <p><small><b>Account created: </b>${created}</small></p>
                </div>
                <div class="form-group">
                    <p><small><b>Account verified: </b>${verfied}</small></p>
                </div>
                <div class="form-group">
                    <input class="submit" type="submit">
                </div>
            </form>
        </div>
    </jsp:body>
</t:layout>
