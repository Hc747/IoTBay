package au.edu.uts.wsd.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface Action {
    
    void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception;
    
}
