package au.edu.uts.isd.iotbay.model.payment;

import lombok.Data;

@Data
public abstract class PaymentMethod {

    protected Integer id;

    protected PaymentMethod(Integer id) {
        this.id = id;
    }

    public abstract Type type();
    
    public abstract String details();

    public enum Type {
        PAYPAL,
        CREDIT_CARD
        ;

        public static Type findByName(String name) {
            return valueOf(name);
        }
    }

}
