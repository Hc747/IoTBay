package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.repository.Repository;

public interface PaymentMethodRepository extends Repository<PaymentMethod> {

    static PaymentMethodRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            return InMemoryPaymentMethodRepository.synchronised();
        }
        return new PersistentPaymentMethodRepository(datasource);
    }
}