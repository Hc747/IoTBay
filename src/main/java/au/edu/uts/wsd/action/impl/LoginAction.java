package au.edu.uts.wsd.action.impl;

import au.edu.uts.wsd.Person;
import au.edu.uts.wsd.PetManager;
import au.edu.uts.wsd.action.Action;
import java.util.Optional;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAction implements Action {

    @Override
    public void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (session.getAttribute(Person.KEY) != null) {
            throw new Exception("You cannot do this while logged in.");
        }
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || password == null) {
            throw new Exception("You must supply an email address and password in order to login.");
        }
        
        PetManager app = PetManager.getInstance(application);
        
        Optional<Person> person = app.getPeople().lookup(p -> p.getEmail().equals(email) && p.getPassword().equals(password));
        
        session.setAttribute(Person.KEY, person.orElseThrow(() -> new Exception("Incorrect email address or password.")));
    }
    
}
