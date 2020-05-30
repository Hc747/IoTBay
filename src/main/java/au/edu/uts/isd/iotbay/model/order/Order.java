package au.edu.uts.isd.iotbay.model.order;

import java.util.*;

import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import lombok.*;

@Data
@AllArgsConstructor
public class Order {

    private Integer id;
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
