package au.edu.uts.wsd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class People implements Serializable {
    
    private List<Person> users = new ArrayList<>(); //TODO: populate from XML
    
    public Optional<Person> lookup(Predicate<Person> condition) {
        return users.stream().filter(condition).findAny();
    }
    
    public List<Person> lookupAll(Predicate<Person> condition) {
        return users.stream().filter(condition).collect(Collectors.toList());
    }
    
    public List<Person> getUsers() {
        return users;
    }
    
    public void setUsers(List<Person> users) {
        this.users = users;
    }
    
}
