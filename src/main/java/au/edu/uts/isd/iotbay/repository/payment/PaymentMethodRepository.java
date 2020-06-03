package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.UserPaymentMethod;
import au.edu.uts.isd.iotbay.model.user.User;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import au.edu.uts.isd.iotbay.repository.Repository;

import java.util.Collection;

public interface PaymentMethodRepository extends Repository<PaymentMethod> {

    Collection<PaymentMethod> findAllByUser(User user);

    UserPaymentMethod associate(User user, PaymentMethod method);

    UserPaymentMethod disassociate(UserPaymentMethod method);

    static PaymentMethodRepository create(MongoDatabaseProvider datasource) {
        if (datasource == null) {
            return new InMemoryPaymentMethodRepository();
        }
        return new MongoPaymentMethodRepository(datasource);
    }
}