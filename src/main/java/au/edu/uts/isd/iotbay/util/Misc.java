package au.edu.uts.isd.iotbay.util;

import au.edu.uts.isd.iotbay.model.Identifiable;
import org.bson.types.ObjectId;

import java.util.Collection;

public class Misc {

    public static <T extends Identifiable> T findById(Collection<T> elements, String id) {
        return findById(elements, new ObjectId(id));
    }

    public static <T extends Identifiable> T findById(Collection<T> elements, ObjectId id) {
        for (T element : elements) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        return null;
    }

    private Misc() {
        throw new IllegalStateException("Unable to create an instance of: " + getClass().getSimpleName());
    }
}
