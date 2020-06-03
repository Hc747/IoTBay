package au.edu.uts.isd.iotbay.model.invoice;


import au.edu.uts.isd.iotbay.model.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public abstract class Invoice implements Identifiable {

    protected ObjectId id;
    protected double amount;

    public abstract Type type();

    public abstract String getEmailAddress();

    public abstract String getFirstName();

    public abstract String getLastName();

    public enum Type {
        GUEST,
        USER
        ;

        public static Type findByName(String name) {
            return valueOf(name);
        }
    }
}
