package au.edu.uts.isd.iotbay.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActionProcessor {

    public void process(HttpServletRequest request, HttpServletResponse response) {
        final String requested = request.getParameter(Action.KEY);

        if (requested == null) {
            return;
        }

        final HttpSession session = request.getSession();
        Action action = ActionRegistry.get(requested);

        if (action == null) {
            action = (Action) session.getAttribute(Action.KEY);
            session.removeAttribute(Action.KEY);
        }

        request.setAttribute(Action.KEY, action);

        if (action != null) {
            handle(action, session, request, response);
        }
    }

    protected void handle(Action action, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        final ServletContext application = request.getServletContext();
        action.process(application, session, request, response);
    }
}
