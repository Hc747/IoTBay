package au.edu.uts.wsd.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "people")
@XmlAccessorType(XmlAccessType.FIELD)
public class People implements Serializable {
    
    @XmlElement(name = "person")
    private List<Person> people = new ArrayList<>();
    
    public People() {}

    public People(List<Person> people) {
        this.people = people;
    }
    
    public void add(Person person) {
        people.add(person);
    }
    
    public void remove(Person person) {
        people.remove(person);
    }
    
    public Optional<Person> lookup(Predicate<Person> condition) {
        return people.stream().filter(condition).findAny();
    }
    
    public List<Person> lookupAll(Predicate<Person> condition) {
        return people.stream().filter(condition).collect(Collectors.toList());
    }
    
    public List<Person> getPeople() {
        return people;
    }
    
    public void setPeople(List<Person> people) {
        this.people = people;
    }
    
}
