<%@page import="au.edu.uts.isd.iotbay.action.Action"%>
<% 
    final Action action = (Action) request.getAttribute(Action.KEY);
    
    if (action != null) {
        out.println(action.render());
    }
%>