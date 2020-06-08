package au.edu.uts.isd.iotbay.util;

import au.edu.uts.isd.iotbay.model.Identifiable;
import org.bson.types.ObjectId;

import java.util.Collection;

import static au.edu.uts.isd.iotbay.util.Validator.isNullOrEmpty;

/**
 * A utility class for operating upon collections of {@code Identifiable} elements.
 */
public class CollectionUtil {

    public static <T extends Identifiable> T findById(Collection<T> elements, String id) {
        if (isNullOrEmpty(id) || !ObjectId.isValid(id)) {
            return null;
        }
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

    private CollectionUtil() {
        throw new IllegalStateException("Unable to create an instance of: " + getClass().getSimpleName());
    }
}
