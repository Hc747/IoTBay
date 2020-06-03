package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.UserPaymentMethod;
import au.edu.uts.isd.iotbay.model.user.User;
import org.bson.types.ObjectId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPaymentMethodRepository implements PaymentMethodRepository {

    private final Map<ObjectId, PaymentMethod> methods;

    private InMemoryPaymentMethodRepository(Map<ObjectId, PaymentMethod> methods) {
        this.methods = Objects.requireNonNull(methods);
    }

    @Override
    public Collection<PaymentMethod> all() {
        return new HashSet<>(methods.values());
    }

    @Override
    public PaymentMethod create(PaymentMethod instance) {
        final ObjectId id = new ObjectId();
        return methods.compute(id, (ObjectId k, PaymentMethod v) -> {
            instance.setId(id);
            return instance;
        });
    }

    @Override
    public PaymentMethod update(PaymentMethod instance) {
        methods.put(instance.getId(), instance);
        return instance;
    }

    @Override
    public PaymentMethod delete(PaymentMethod instance) {
        return methods.remove(instance.getId(), instance) ? instance : null;
    }

    public static InMemoryPaymentMethodRepository concurrent() {
        return new InMemoryPaymentMethodRepository(new ConcurrentHashMap<>());
    }

    public static InMemoryPaymentMethodRepository synchronised() {
        return new InMemoryPaymentMethodRepository(Collections.synchronizedMap(new HashMap<>()));
    }

    public static InMemoryPaymentMethodRepository unsynchronised() {
        return new InMemoryPaymentMethodRepository(new HashMap<>());
    }

    @Override
    public Collection<PaymentMethod> findAllByUser(User user) {
        return null;
    }

    @Override
    public UserPaymentMethod associate(User user, PaymentMethod method) {
        return null;
    }

    @Override
    public UserPaymentMethod disassociate(UserPaymentMethod method) {
        return null;
    }
}
