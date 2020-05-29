package au.edu.uts.isd.iotbay.model.order;

import java.util.*;

public class Order {
    private int id, invoiceId, addressId, paymentMethodId, productId;
    public enum status
    {
        SAVED,
        SUBMITTED,
        CANCELLED;
    }
}
