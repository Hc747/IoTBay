package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.MongoRepository;

import java.util.Collection;

public class MongoPaymentMethodRepository extends MongoRepository<PaymentMethod> implements PaymentMethodRepository {

    protected MongoPaymentMethodRepository(MongoDatabaseProvider datasource) {
        super(datasource, PaymentMethod.class);
    }

    @Override
    public Collection<PaymentMethod> findAllByUser(User user) {
        return null;
    }
}
