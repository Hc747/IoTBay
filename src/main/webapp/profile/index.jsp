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
            <table>
                <thead>
                <tr>
                    <td>Username</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${user.username}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </jsp:body>
</t:layout>
