package au.edu.uts.wsd.action.impl;

import au.edu.uts.wsd.Person;
import au.edu.uts.wsd.PetManager;
import au.edu.uts.wsd.action.Action;
import java.util.Optional;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Harrison
 */
public class RegisterAction implements Action {

    @Override
    public void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (session.getAttribute(Person.KEY) != null) {
            throw new Exception("You cannot do this while logged in.");
        }
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (name == null || email == null || password == null) {
            throw new Exception("You must supply a name, email address and password in order to login.");
        }
        
        PetManager app = PetManager.getInstance(application);
        
        Optional<Person> existing = app.getPeople().lookup(p -> p.getEmail().equals(email));
        
        if (existing.isPresent()) {
            throw new Exception("Sorry, that username is already taken.");
        }
        
        Person person = new Person(name, email, password);
        
        //TODO: add to people app and save XML
        
        session.setAttribute(Person.KEY, person);
    }
    
}
