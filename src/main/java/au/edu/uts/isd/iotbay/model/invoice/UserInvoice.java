package au.edu.uts.isd.iotbay.model.invoice;

import au.edu.uts.isd.iotbay.model.user.User;
import lombok.*;

import java.util.Objects;

@ToString
@EqualsAndHashCode(callSuper = true)
public class UserInvoice extends Invoice {

    protected final User user;

    public UserInvoice(Integer id, double amount, User user) {
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
        return user.getNameComponents()[0];
    }

    @Override
    public String getLastName() {
        final String name = user.getNameComponents()[1];
        return name == null ? "" : name;
    }
}
