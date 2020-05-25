<%@page import="au.edu.uts.isd.iotbay.Constants"%>
<%@page import="au.edu.uts.isd.iotbay.model.user.User"%>
<%@ page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil" %>
<%
    final User user = AuthenticationUtil.user(session);
%>
<strong>
    Welcome, <a href="<%= Constants.path(true, "profile") %>"><%= user.getUsername() %></a>
</strong>
<a href="<%= Constants.path(true, "main")%>">Main Page</a>
<a href="<%= Constants.path(true, "logout") %>">Logout</a>