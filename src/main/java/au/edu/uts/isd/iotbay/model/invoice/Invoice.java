package au.edu.uts.isd.iotbay.model.invoice;


import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends IdentifiableModel {

    private double amount;
    private String emailAddress, firstName, lastName;

    public Invoice(ObjectId id, User user, double amount) {
        final String[] names = user.names();
        this.emailAddress = user.getUsername();
        this.firstName = names[0];
        this.lastName = names[1];
        this.amount = amount;
    }

    public Invoice(User user, double amount) {
        this(null, user, amount);
    }
}
