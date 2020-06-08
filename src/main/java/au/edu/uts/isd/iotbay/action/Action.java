package au.edu.uts.isd.iotbay.action;

import lombok.SneakyThrows;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

/**
 * Represents functionality that would typically be exposed via servlets; simpler to implement and dispatch,
 * can be registered / de-registered dynamically, and provides greater flexibility in terms of how they may be invoked.
 */
public abstract class Action implements Serializable {
    
    public static final String KEY = "action";
    
    protected MessageType type = MessageType.SUCCESS;
    protected String message;

    @SneakyThrows
    public final void process(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            invoke(application, session, request, response);
            type = MessageType.SUCCESS;
        } catch (ActionException e) {
            message = e.getMessage();
            type = MessageType.WARNING;
        } catch (Exception e) {
            message = e.getClass().getSimpleName() + ": " + e.getMessage();
            type = MessageType.DANGER;
        }
    }
    
    protected abstract void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception;

    protected static void reject(String reason) throws ActionException {
        throw new ActionException(reason);
    }
    
    public String render() {
        if (isNullOrEmpty(message) || type == null) {
            return "";
        }

        return String.format("<div class=\"alert alert-%s\" role=\"alert\">", type.name().toLowerCase()) + message + "</div>";
    }
    
    public static class ActionException extends Exception {
    
        public ActionException(String message) {
            super(message);
        }
        
        public ActionException(String message, Exception inner) {
            super(message, inner);
        }
        
    }
    
    protected enum MessageType {
        SUCCESS,
        WARNING,
        DANGER
    }
}
