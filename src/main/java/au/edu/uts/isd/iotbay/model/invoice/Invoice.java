package au.edu.uts.isd.iotbay.model.invoice;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Invoice {

    protected Integer id;
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
