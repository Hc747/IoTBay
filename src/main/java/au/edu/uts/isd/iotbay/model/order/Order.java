package au.edu.uts.isd.iotbay.model.order;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order extends IdentifiableModel {

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
