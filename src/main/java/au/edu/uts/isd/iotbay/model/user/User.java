package au.edu.uts.isd.iotbay.model.user;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends IdentifiableModel {

    private String name, username, password, phone;
    private Role role;
    private boolean enabled;
    private LocalDate created, verified;
    private List<PaymentMethod> payments;
    private List<Order> orders;

    public String[] names() {
        final String[] components = name.split(" +");
        final StringJoiner joiner = new StringJoiner(" ");
        for (int index = 1; index < components.length; index++) {
            joiner.add(components[index]);
        }
        return new String[] { components[0], joiner.length() == 0 ? null : joiner.toString() };
    }
}
