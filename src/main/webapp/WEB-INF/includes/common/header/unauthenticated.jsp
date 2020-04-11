<%@page import="au.edu.uts.isd.iotbay.Constants"%>
<%
    final String greeting = "Welcome, Guest";
%>
<strong>
    <%= greeting %>
</strong>
&nbsp;
<a href="<%= Constants.BASE_URL %>login/">Login</a>
&nbsp;
<a href="<%= Constants.BASE_URL %>register/">Register</a>