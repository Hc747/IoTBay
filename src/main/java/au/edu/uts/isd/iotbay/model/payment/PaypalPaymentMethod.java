package au.edu.uts.isd.iotbay.model.payment;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class PaypalPaymentMethod extends PaymentMethod {

    private String token;

    public PaypalPaymentMethod(Integer id, String token) {
        super(id);
//        this.token = Objects.requireNonNull(token);
        this.token = token;
    }

    @Override
    public Type type() {
        return Type.PAYPAL;
    }
}
