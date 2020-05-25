package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.repository.Repository;

public interface PaymentMethodRepository extends Repository<PaymentMethod> {

    static PaymentMethodRepository create(ConnectionProvider datasource) {
        //TODO: in memory implementation
        return new PersistentPaymentMethodRepository(datasource);
    }
}