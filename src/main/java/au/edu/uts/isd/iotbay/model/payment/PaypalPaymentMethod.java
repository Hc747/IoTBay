package au.edu.uts.isd.iotbay.model.payment;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.util.Objects;

@Data
@ToString(callSuper = true)
public class PaypalPaymentMethod extends PaymentMethod {

    private String token;

    public PaypalPaymentMethod() {
        super(null);
    }

    public PaypalPaymentMethod(ObjectId id, String token) {
        super(id);
        this.token = Objects.requireNonNull(token);
    }

    @Override
    public Type type() {
        return Type.PAYPAL;
    }

    @Override
    public String details() {
        return String.format("Token: %s", token);
    }
}
