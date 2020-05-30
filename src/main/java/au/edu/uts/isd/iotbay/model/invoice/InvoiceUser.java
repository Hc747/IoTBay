package au.edu.uts.isd.iotbay.model.invoice;

import lombok.*;

@Data
@AllArgsConstructor
public class InvoiceUser extends Invoice{
    protected int userID;
}
