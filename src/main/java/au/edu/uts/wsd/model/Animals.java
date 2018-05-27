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

@XmlRootElement(name = "animals")
@XmlAccessorType(XmlAccessType.FIELD)
public class Animals implements Serializable {

    @XmlElement(name = "animal")
    private List<Animal> animals = new ArrayList<>();
    
    public void add(Animal animal) {
        animals.add(animal);
    }
    
    public void remove(Animal animal) {
        animals.remove(animal);
    }

    public Optional<Animal> lookup(Predicate<Animal> condition) {
        return animals.stream().filter(condition).findAny();
    }

    public List<Animal> lookupAll(Predicate<Animal> condition) {
        return animals.stream().filter(condition).collect(Collectors.toList());
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

}
