<%@ page import="au.edu.uts.isd.iotbay.model.user.User" %>
<%@ page import="java.util.Collection" %>
<%@ page import="au.edu.uts.isd.iotbay.IoTBayApplicationContext" %>
<%@ page import="au.edu.uts.isd.iotbay.repository.user.UserRepository" %>
<%@ page import="java.util.Arrays" %>
<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%
    final IoTBayApplicationContext context = IoTBayApplicationContext.getInstance(application);
    final UserRepository repository = context.getUsers();
    final Collection<User> users = repository.all();
    final String output = Arrays.toString(users.toArray());
    request.setAttribute("users", output);
%>

<%= output %>
<t:layout>
    <jsp:body>
        ${users}
    </jsp:body>
</t:layout>
