package au.edu.uts.isd.iotbay.model.invoice;

import lombok.*;

@Data
public class InvoiceGuest extends Invoice {
    String firstName, lastName, email;

}
