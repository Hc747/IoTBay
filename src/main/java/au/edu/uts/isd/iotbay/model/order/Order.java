package au.edu.uts.isd.iotbay.model.order;

import au.edu.uts.isd.iotbay.model.IdentifiableModel;
import au.edu.uts.isd.iotbay.model.invoice.Invoice;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends IdentifiableModel {

    String Shipment_id;
    String Invoice_id;
    String Payment_method;
    String Products;
    String status;
    //LocalDate date;

   /* private Shipment shipment;
    private Invoice invoice;
    private PaymentMethod payment;
    private List<OrderProduct> products;
    private List<OrderStatus> statuses;
    private LocalDate date;*/


}
