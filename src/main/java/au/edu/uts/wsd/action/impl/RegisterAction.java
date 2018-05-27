package au.edu.uts.wsd.action.impl;

import au.edu.uts.wsd.model.Person;
import au.edu.uts.wsd.PetManager;
import au.edu.uts.wsd.action.Action;
import au.edu.uts.wsd.model.Animals;
import au.edu.uts.wsd.util.UUIDGenerator;
import java.util.Optional;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Harrison
 */
public class RegisterAction extends Action {

    @Override
    protected void invoke(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (session.getAttribute(Person.KEY) != null) {
            throw new ActionException("You cannot do this while logged in.");
        }
        
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        if (name == null || email == null || password == null) {
            throw new ActionException("You must supply a name, email address and password in order to login.");
        }
        
        PetManager app = PetManager.getInstance(application);
        
        Optional<Person> existing = app.getPeople().lookup(p -> p.getEmail().equals(email));
        
        if (existing.isPresent()) {
            throw new ActionException("Sorry, that username is already taken.");
        }
        
        Person person = new Person(UUIDGenerator.generate(), name, email, password, new Animals());
        
        app.getPeople().add(person);
        app.save();
        
        session.setAttribute(Person.KEY, person);
        
        message = String.format("Registration successful. Welcome, %s", person.getName());
    }
    
}
