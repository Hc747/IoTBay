package au.edu.uts.wsd.model;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {
    
    public static final String KEY = "person";
    
    @XmlAttribute
    private String id;
    
    private String name, email, password;
    private Animals pets = new Animals();
    
    public Person() {}
    
    public Person(String id, String name, String email, String password, Animals pets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.pets = pets;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Animals getPets() {
        return pets;
    }

    public void setPets(Animals pets) {
        this.pets = pets;
    }
    
    //Determine that a person is equal to another person if they have the same
    //id.
    //see the contract between Object#equals and Object#hashcode
    //https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html
    
    @Override
    public boolean equals(Object other) {
        return other instanceof Person && ((Person) other).id.equals(id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}
