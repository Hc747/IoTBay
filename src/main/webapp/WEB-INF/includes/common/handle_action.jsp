<%@page import="au.edu.uts.wsd.action.impl.LogoutAction"%>
<%@page import="au.edu.uts.wsd.action.Action"%>
<% 
    Action action = (Action) request.getAttribute(Action.KEY);
    
    if (action == null) {
        action = (Action) session.getAttribute(Action.KEY);
        
        request.setAttribute(Action.KEY, action);
        
        session.removeAttribute(Action.KEY);
    }
    
    if (action != null) {
        action.process(application, session, request, response);
    }
%>