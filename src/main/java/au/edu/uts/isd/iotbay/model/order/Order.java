package au.edu.uts.isd.iotbay.model.order;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.invoice.Invoice;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends IdentifiableModel {

    private Invoice invoice;
    private PaymentMethod payment;
    private Status status;
    private List<Product> products;
    private LocalDate date;

    public enum Status {
        CREATED,
        CANCELLED,
        SHIPPED,
        DELIVERED
    }
}
