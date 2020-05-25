<%@ page import="au.edu.uts.isd.iotbay.Constants" %>
<%    
    final String action = String.format("%s?action=logout", Constants.path(false));
    response.sendRedirect(action);
%>