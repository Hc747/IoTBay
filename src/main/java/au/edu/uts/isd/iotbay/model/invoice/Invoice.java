package au.edu.uts.isd.iotbay.model.invoice;


import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public abstract class Invoice extends IdentifiableModel {

    protected Invoice(ObjectId id, double amount) {
        super(id);
        this.amount = amount;
    }

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
