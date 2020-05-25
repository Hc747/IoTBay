package au.edu.uts.isd.iotbay.model.payment;

import lombok.Data;

import java.util.Objects;

@Data
public class PaypalPaymentMethod extends PaymentMethod {

    private String token;

    public PaypalPaymentMethod(Integer id, String token) {
        super(id);
        this.token = Objects.requireNonNull(token);
    }

    @Override
    public Type type() {
        return Type.PAYPAL;
    }
}
