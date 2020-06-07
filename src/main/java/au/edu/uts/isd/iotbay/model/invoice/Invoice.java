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
    private String emailAddress, name;

    public Invoice(ObjectId id, User user, double amount) {
        this.emailAddress = user.getUsername();
        this.name = user.getName();
        this.amount = amount;
    }

    public Invoice(User user, double amount) {
        this(null, user, amount);
    }
}
