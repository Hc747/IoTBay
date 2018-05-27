<%@page import="au.edu.uts.wsd.action.Action"%>
<%
    Action action = (Action) request.getAttribute(Action.KEY);
    
    if (action != null) {
%>
<%= action.render() %>
<% } %>