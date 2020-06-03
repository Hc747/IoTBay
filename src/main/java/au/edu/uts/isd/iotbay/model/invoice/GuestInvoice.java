package au.edu.uts.isd.iotbay.model.invoice;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class GuestInvoice extends Invoice {

    protected String firstName, lastName, email;

    public GuestInvoice(ObjectId id, double amount, String firstName, String lastName, String email) {
        super(id, amount);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public Type type() {
        return Type.GUEST;
    }

    @Override
    public String getEmailAddress() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }
}
