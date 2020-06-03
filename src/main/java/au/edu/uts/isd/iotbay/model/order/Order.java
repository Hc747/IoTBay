package au.edu.uts.isd.iotbay.model.order;

import au.edu.uts.isd.iotbay.model.Identifiable;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class Order implements Identifiable {

    private ObjectId id;
    private PaymentMethod payment;

//    private int id, invoiceId, addressId, paymentMethodId, productId;
//    public enum status
//    {
//        SAVED,
//        SUBMITTED,
//        CANCELLED
//        ;
//    }
}
