package au.edu.uts.wsd;

import java.io.Serializable;
import java.util.List;

public class Animals implements Serializable {
    
    private List<Animal> animals;

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
    
}
