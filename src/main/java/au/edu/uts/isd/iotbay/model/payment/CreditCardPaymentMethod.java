package au.edu.uts.isd.iotbay.model.payment;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.util.Objects;

@Data
@ToString(callSuper = true)
public class CreditCardPaymentMethod extends PaymentMethod {

    private String number, holder, cvv;
    private Date expiration;

    public CreditCardPaymentMethod(ObjectId id, String number, String holder, String cvv, Date expiration) {
        super(id);
        this.number = Objects.requireNonNull(number);
        this.holder = Objects.requireNonNull(holder);
        this.cvv = Objects.requireNonNull(cvv);
        this.expiration = Objects.requireNonNull(expiration);
    }

    public boolean hasExpired() {
        return expiration.before(new Date(System.currentTimeMillis()));
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
