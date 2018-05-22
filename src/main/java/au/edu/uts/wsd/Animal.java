package au.edu.uts.wsd;

import java.io.Serializable;

public class Animal implements Serializable {
    
    private Person owner;
    private Species species;
    private String name;
    
    public Animal() {}
    
    public Animal(Person owner, Species species, String name) {
        this.owner = owner;
        this.species = species;
        this.name = name;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
