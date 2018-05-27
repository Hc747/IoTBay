package au.edu.uts.wsd.action;

import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class Action implements Serializable {
    
    public static final String KEY = "action";
    
    protected MessageType type = MessageType.SUCCESS;
    protected String message;
    
    public final void process(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            invoke(application, session, request, response);
        } catch (ActionException e) {
            type = MessageType.WARNING;
            message = e.getMessage();
        } catch (Exception e) {
            type = MessageType.DANGER;
            message = "An unexpected error occurred. Unable to process your request.";
            
            e.printStackTrace();
        }
    }
    
    protected abstract void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    public String render() {
        if (message == null || message.isEmpty() || type == null) {
            return "";
        }
        
        StringBuilder builder = new StringBuilder();
        
        builder
                .append(String.format("<div class=\"alert alert-%s\" role=\"alert\">", type.name().toLowerCase()))
                .append(message)
                .append("</div>");
        
        return builder.toString();
    }
    
    public class ActionException extends Exception {
    
        public ActionException(String message) {
            super(message);
        }
        
        public ActionException(String message, Exception inner) {
            super(message, inner);
        }
        
    }
    
    public enum MessageType {
        SUCCESS,
        WARNING,
        DANGER
    }
    
}
