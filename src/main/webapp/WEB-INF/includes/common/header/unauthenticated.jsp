<%@page import="au.edu.uts.isd.iotbay.Constants"%>
<%
    final String greeting = "Welcome, Guest";
%>

<strong>
    <%= greeting %>
</strong>
&nbsp;
<a href="<%= Constants.path(true, "login") %>">Login</a>
&nbsp;
<a href="<%= Constants.path(true, "register") %>">Register</a>