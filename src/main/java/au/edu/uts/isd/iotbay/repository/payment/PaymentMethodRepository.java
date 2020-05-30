package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.UserPaymentMethod;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Collection;

public interface PaymentMethodRepository extends Repository<PaymentMethod> {

    Collection<PaymentMethod> findAllByUser(User user);

    UserPaymentMethod associate(PaymentMethod method, User user);

    UserPaymentMethod disassociate(UserPaymentMethod method);

    static PaymentMethodRepository create(ConnectionProvider datasource) {
        if (datasource == null) {
            return InMemoryPaymentMethodRepository.concurrent();
        }
        return new PersistentPaymentMethodRepository(datasource);
    }
}