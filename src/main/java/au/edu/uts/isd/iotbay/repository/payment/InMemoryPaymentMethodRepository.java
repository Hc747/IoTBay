package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.repository.InMemoryRepository;

import java.util.Collection;

public class InMemoryPaymentMethodRepository extends InMemoryRepository<PaymentMethod> implements PaymentMethodRepository {

    //TODO: implement functionality

    @Override
    public Collection<PaymentMethod> findAllByUser(User user) {
        return null;
    }
}
