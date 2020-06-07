package au.edu.uts.isd.iotbay.model.payment;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public abstract class PaymentMethod extends IdentifiableModel {

    public PaymentMethod() {
        this(null);
    }

    protected PaymentMethod(ObjectId id) {
        super(id);
    }

    public abstract Type type();

    public enum Type {
        PAYPAL,
        CREDIT_CARD
        ;

        public static Type findByName(String name) {
            try {
                return valueOf(name);
            } catch (Exception e) {
                return null;
            }
        }
    }

}
