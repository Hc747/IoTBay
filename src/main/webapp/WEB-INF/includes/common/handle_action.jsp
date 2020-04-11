<%@page import="au.edu.uts.isd.iotbay.action.ActionRegistry"%>
<%@page import="au.edu.uts.isd.iotbay.action.Action"%>
<% 
    request.setAttribute(Action.KEY, ActionRegistry.get(request.getParameter("action")));
    
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