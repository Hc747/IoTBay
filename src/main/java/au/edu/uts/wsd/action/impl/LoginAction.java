package au.edu.uts.wsd.action.impl;

import au.edu.uts.wsd.model.Person;
import au.edu.uts.wsd.PetManager;
import au.edu.uts.wsd.action.Action;
import java.util.Optional;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (session.getAttribute(Person.KEY) != null) {
            throw new ActionException("You cannot do this while logged in.");
        }
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (email == null || password == null) {
            throw new ActionException("You must supply an email address and password in order to login.");
        }
        
        if (password.length() < 8) {
            throw new ActionException("Your password must be atleast 8 characters.");
        }
        
        PetManager app = PetManager.getInstance(application);
        
        Optional<Person> person = app.getPeople().lookup(p -> p.getEmail().equals(email) && p.getPassword().equals(password));
        
        session.setAttribute(Person.KEY, person.orElseThrow(() -> new ActionException("Incorrect email address or password.")));
        
        message = String.format("Login successful. Welcome, %s", person.get().getName());
    }
    
}
