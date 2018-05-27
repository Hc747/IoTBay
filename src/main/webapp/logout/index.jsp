<%@page import="au.edu.uts.wsd.action.impl.LogoutAction"%>
<%@page import="au.edu.uts.wsd.action.Action"%>
<%@page import="au.edu.uts.wsd.Constants"%>
s
<%    
    session.setAttribute(Action.KEY, new LogoutAction());
    
    response.sendRedirect(Constants.BASE_URL);
%>