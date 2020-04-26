<%@ page import="static au.edu.uts.isd.iotbay.Constants.BASE_URL" %>
<%    
    final String action = String.format("%s?action=logout", BASE_URL);
    response.sendRedirect(action);
%>