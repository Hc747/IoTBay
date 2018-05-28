package au.edu.uts.wsd.model;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Animal implements Serializable {
    
    public static final String key = "animal";

    @XmlAttribute
    private String microchip;

    private String name;
    private Species species;

    public Animal() {
    }

    public Animal(String microchip, String name, Species species) {
        this.microchip = microchip;
        this.name = name;
        this.species = species;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
    
    @XmlElement(name = "imageURL")
    public String getImageURL() {
        return species.getImageURL();
    }
    
    //Determine that an animal is equal to another animal if they have the same
    //microchip.
    //see the contract between Object#equals and Object#hashcode
    //https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html
    
    @Override
    public boolean equals(Object other) {
        return other instanceof Animal && ((Animal) other).microchip.equals(microchip);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(microchip);
    }

}
