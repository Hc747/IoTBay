package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.payment.CreditCardPaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod.Type;
import au.edu.uts.isd.iotbay.model.payment.PaypalPaymentMethod;
import lombok.SneakyThrows;

import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

public class PersistentPaymentMethodRepository implements PaymentMethodRepository {

    private static final ResultExtractor<PaymentMethod> EXTRACTOR = r -> {
        int id = r.getInt("id");
        Type type = Type.findByName(r.getString("type"));

        switch (type) {
            case CREDIT_CARD:
                String number = r.getString("card_number");
                String holder = r.getString("card_holder_name");
                String cvv = r.getString("card_verification_value");
                Date expiration = r.getDate("expiration");
                return new CreditCardPaymentMethod(id, number, holder, cvv, expiration);
            case PAYPAL:
                String token = r.getString("token");
                return new PaypalPaymentMethod(id, token);
            default: return null;
        }
    };

    private final ConnectionProvider datasource;

    public PersistentPaymentMethodRepository(ConnectionProvider datasource) {
        this.datasource = Objects.requireNonNull(datasource);
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public Collection<PaymentMethod> all() {
        final String query = "SELECT * FROM payment_method pm LEFT JOIN payment_method_credit_card cc on pm.id = cc.payment_method_id LEFT JOIN payment_method_paypal pp ON pm.id = pp.payment_method_id;";
        return datasource.withStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
    }

    @Override
    public PaymentMethod save(PaymentMethod instance) {
        return null;
    }

    @Override
    public PaymentMethod delete(PaymentMethod instance) {
        return null;
    }
}
