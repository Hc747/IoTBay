package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.UserPaymentMethod;
import au.edu.uts.isd.iotbay.model.user.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPaymentMethodRepository implements PaymentMethodRepository {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(1);

    private final Map<Integer, PaymentMethod> methods;

    private InMemoryPaymentMethodRepository(Map<Integer, PaymentMethod> methods) {
        this.methods = Objects.requireNonNull(methods);
    }

    @Override
    public Collection<PaymentMethod> all() {
        return new HashSet<>(methods.values());
    }

    @Override
    public PaymentMethod create(PaymentMethod instance) {
        final Integer id = SEQUENCE.getAndIncrement();
        return methods.compute(id, (Integer k, PaymentMethod v) -> {
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
    public UserPaymentMethod associate(PaymentMethod method, User user) {
        return null;
    }

    @Override
    public UserPaymentMethod disassociate(UserPaymentMethod method) {
        return null;
    }
}
