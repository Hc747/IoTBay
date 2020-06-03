package au.edu.uts.isd.iotbay.model.invoice;

import au.edu.uts.isd.iotbay.model.user.User;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.util.Objects;

@ToString
@EqualsAndHashCode(callSuper = true)
public class UserInvoice extends Invoice {

    protected final User user;

    public UserInvoice(ObjectId id, double amount, User user) {
        super(id, amount);
        this.user = Objects.requireNonNull(user);
    }

    @Override
    public Type type() {
        return Type.USER;
    }

    @Override
    public String getEmailAddress() {
        return user.getUsername();
    }

    @Override
    public String getFirstName() {
        return user.names()[0];
    }

    @Override
    public String getLastName() {
        final String name = user.names()[1];
        return name == null ? "" : name;
    }
}
