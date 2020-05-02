<%@page import="au.edu.uts.isd.iotbay.model.user.User"%>
<%@page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil"%>
<%@ page import="static au.edu.uts.isd.iotbay.Constants.BASE_URL" %>
<%
    final User user = AuthenticationUtil.user(session);
%>
<strong>
    Welcome, <a href="<%= BASE_URL %>profile/"><%= user.getUsername() %></a>
</strong>
<a href="<%= BASE_URL%>main/">Main Page</a>
<a href="<%= BASE_URL %>logout/">Logout</a>