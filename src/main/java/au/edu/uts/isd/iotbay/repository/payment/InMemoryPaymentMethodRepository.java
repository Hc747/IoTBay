package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;

import java.util.*;

public class InMemoryPaymentMethodRepository implements PaymentMethodRepository {

    private final List<PaymentMethod> methods;

    private InMemoryPaymentMethodRepository(List<PaymentMethod> methods) {
        this.methods = Objects.requireNonNull(methods);
    }

    @Override
    public Collection<PaymentMethod> all() {
        return new HashSet<>(methods);
    }

    @Override
    public PaymentMethod save(PaymentMethod instance) {
        methods.remove(instance);
        methods.add(instance);
        return instance;
    }

    @Override
    public PaymentMethod delete(PaymentMethod instance) {
        return methods.remove(instance) ? instance : null;
    }

    public static InMemoryPaymentMethodRepository synchronised() {
        return new InMemoryPaymentMethodRepository(Collections.synchronizedList(new ArrayList<>()));
    }

    public static InMemoryPaymentMethodRepository unsafe() {
        return new InMemoryPaymentMethodRepository(new ArrayList<>());
    }
}
