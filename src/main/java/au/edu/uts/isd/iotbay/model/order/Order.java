package au.edu.uts.isd.iotbay.model.order;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.invoice.Invoice;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends IdentifiableModel {

    private Invoice invoice;
    private PaymentMethod payment;
    private List<OrderProduct> products = new ArrayList<>();
    private List<OrderStatus> statuses = new ArrayList<>();
    private LocalDate date;

}
