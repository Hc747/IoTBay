package au.edu.uts.wsd;

import java.io.Serializable;
import javax.servlet.ServletContext;

public class PetManager implements Serializable {

    private static final String KEY = "PetManager";

    private People people = new People(); //TODO: load from XML

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    //TODO: initialise correctly
    public static PetManager getInstance(ServletContext application) {
        PetManager app = (PetManager) application.getAttribute(KEY);
        if (app == null) {
            synchronized (application) {
                app = (PetManager) application.getAttribute(KEY);
                if (app == null) {
                    app = new PetManager();
                    application.setAttribute(KEY, app);
                }
            }
        }
        return app;
    }

}
