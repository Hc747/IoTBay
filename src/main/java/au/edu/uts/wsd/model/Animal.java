package au.edu.uts.wsd.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Animal implements Serializable {

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

}
