<%@page import="au.edu.uts.wsd.action.impl.LogoutAction"%>
<%@page import="au.edu.uts.wsd.action.Action"%>
<%@page import="au.edu.uts.wsd.Constants"%>
<%
    Action action = new LogoutAction();
    action.invoke(application, session, request, response);
%>
