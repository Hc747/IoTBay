package au.edu.uts.isd.iotbay.model.payment;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString(callSuper = true)
public class CreditCardPaymentMethod extends PaymentMethod {

    private String number, holder, cvv;
    private Date expiration;

    public CreditCardPaymentMethod(Integer id, String number, String holder, String cvv, Date expiration) {
        super(id);
        this.number = number;
        this.holder = holder;
        this.cvv = cvv;
        this.expiration = expiration;
//        this.number = Objects.requireNonNull(number);
//        this.holder = Objects.requireNonNull(holder);
//        this.cvv = Objects.requireNonNull(cvv);
//        this.expiration = Objects.requireNonNull(expiration);
    }

    public boolean hasExpired() {
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    @Override
    public Type type() {
        return Type.CREDIT_CARD;
    }
}
