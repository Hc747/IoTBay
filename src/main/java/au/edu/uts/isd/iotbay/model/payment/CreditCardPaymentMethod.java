package au.edu.uts.isd.iotbay.model.payment;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.Objects;

@Data
@ToString(callSuper = true)
public class CreditCardPaymentMethod extends PaymentMethod {

    private String number, holder, cvv;
    private LocalDate expiration;

    public CreditCardPaymentMethod() {
        super(null);
    }

    public CreditCardPaymentMethod(ObjectId id, String number, String holder, String cvv, LocalDate expiration) {
        super(id);
        this.number = Objects.requireNonNull(number);
        this.holder = Objects.requireNonNull(holder);
        this.cvv = Objects.requireNonNull(cvv);
        this.expiration = Objects.requireNonNull(expiration);
    }

    public boolean hasExpired() {
        return expiration.isBefore(LocalDate.now());
    }

    @Override
    public Type type() {
        return Type.CREDIT_CARD;
    }

    @Override
    public String details() {
        return String.format("Card Number: %s, Holder: %s, CVV: %s, Expiration: %s", number, holder, cvv, expiration.toString());
    }
}
