<%@page import="au.edu.uts.isd.iotbay.model.user.User"%>
<%@page import="au.edu.uts.isd.iotbay.Constants"%>
<%@page import="au.edu.uts.isd.iotbay.util.AuthenticationUtil"%>
<%
    final User user = AuthenticationUtil.user(session);
%>
<strong>
    Welcome, <a href="<%= Constants.BASE_URL %>profile/"><%= user.getUsername() %></a>
</strong>
&nbsp;
<a href="<%= Constants.BASE_URL %>logout/">Logout</a>