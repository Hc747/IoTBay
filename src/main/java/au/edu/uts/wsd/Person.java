package au.edu.uts.wsd;

import java.io.Serializable;

public class Person implements Serializable {
    
    public static final String KEY = "person";
    
    private String email, name, password;
    
    public Person() {}
    
    public Person(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
