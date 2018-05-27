package au.edu.uts.wsd;

import au.edu.uts.wsd.model.People;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public final class PetManager implements Serializable {

    public static final String KEY = "PetManager";
    public static final String BASE_FILEPATH = "/WEB-INF/";

    private String baseFilepath, peopleFilepath;
    private People people;

    public PetManager() {
    }

    public PetManager(String peopleFilepath) {
        this(BASE_FILEPATH, peopleFilepath);
    }

    public PetManager(String baseFilepath, String peopleFilepath) {
        setBaseFilepath(baseFilepath);
        setPeopleFilepath(peopleFilepath);
    }

    public void save() {
        marshal(People.class, peopleFilepath, people);
    }

    private <T> T unmarshal(Class<T> clazz, String filepath) {
        try (FileInputStream stream = new FileInputStream(baseFilepath + filepath)) {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            return (T) unmarshaller.unmarshal(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void marshal(Class<T> clazz, String filepath, T object) {
        try (FileOutputStream stream = new FileOutputStream(baseFilepath + filepath)) {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            marshaller.marshal(object, stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getBaseFilepath() {
        return baseFilepath;
    }

    public void setBaseFilepath(String baseFilepath) {
        this.baseFilepath = baseFilepath;
    }

    public String getPeopleFilepath() {
        return peopleFilepath;
    }

    public void setPeopleFilepath(String peopleFilepath) {
        this.peopleFilepath = peopleFilepath;
        this.people = unmarshal(People.class, peopleFilepath);
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    public static PetManager getInstance(ServletContext application) {
        PetManager app = (PetManager) application.getAttribute(KEY);
        if (app == null) {
            app = (PetManager) application.getAttribute(KEY);
            if (app == null) {
                app = new PetManager(application.getRealPath(BASE_FILEPATH), "/PetOwners.xml");
                application.setAttribute(KEY, app);
            }
        }
        return app;
    }

}
