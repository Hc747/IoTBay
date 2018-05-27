package au.edu.uts.wsd.rest;

import au.edu.uts.wsd.PetManager;
import au.edu.uts.wsd.model.People;
import au.edu.uts.wsd.model.Person;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class RestService {
    
    @Context
    private ServletContext application;
    
    private PetManager getPetManager() throws Exception {
        return PetManager.getInstance(application);
    }
    
    @GET
    @Path("people")
    @Produces(MediaType.APPLICATION_XML)
    public People getAll(
            @QueryParam("name") String name, 
            @QueryParam("email") String email, 
            @QueryParam("password") String password,
            @QueryParam("unique") String unique) throws Exception {
        
        List<Person> values = new ArrayList<>(getPetManager().getPeople().getPeople());
        
        if (name != null) {
            
            values = values.stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
            
        }
        
        if (email != null) {
            
            values = values.stream().filter(p -> p.getEmail().equals(email)).collect(Collectors.toList());
            
        }
        
        if (password != null) {
            
            values = values.stream().filter(p -> p.getPassword().equals(password)).collect(Collectors.toList());
            
        }
        
        if (unique != null) {
            
            Set<Person> uniqueValues = new HashSet<>();
            
            uniqueValues.addAll(values);
            
            values = new ArrayList<>(uniqueValues);
            
            
        }
        
        return new People(values);
    }
    
}
