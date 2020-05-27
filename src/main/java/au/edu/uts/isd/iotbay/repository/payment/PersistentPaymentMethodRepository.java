package au.edu.uts.isd.iotbay.repository.payment;

import au.edu.uts.isd.iotbay.database.ConnectionProvider;
import au.edu.uts.isd.iotbay.database.ResultExtractor;
import au.edu.uts.isd.iotbay.model.payment.CreditCardPaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod.Type;
import au.edu.uts.isd.iotbay.model.payment.PaypalPaymentMethod;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.ResultSet;
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
                Date expiration = r.getDate("expiration_date");
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
        return datasource.useStatement(statement -> EXTRACTOR.all(statement.executeQuery(query)));
    }

    //TODO: make transactional
    @Override
    @SneakyThrows //TODO: consider implications
    public PaymentMethod create(PaymentMethod instance) {
        final Type type = instance.type();
        final String initial = "INSERT INTO payment_method (type) VALUES (?);";
        final Integer id = datasource.useKeyedPreparedStatement(initial, statement -> {
            statement.setString(1, type.name());

            final int inserted = statement.executeUpdate();

            if (inserted <= 0) {
                return null;
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
                return null;
            }
        });

        if (id == null) {
            return null;
        }

        instance.setId(id);

        switch (type) {
            case PAYPAL: return doCreate((PaypalPaymentMethod) instance);
            case CREDIT_CARD: return doCreate((CreditCardPaymentMethod) instance);
            default: return null;
        }
    }

    @SneakyThrows
    private PaypalPaymentMethod doCreate(PaypalPaymentMethod paypal) {
        final String query = "INSERT INTO payment_method_paypal (payment_method_id, token) VALUES (?, ?);";
        final int inserted = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, paypal.getId());
            statement.setString(2, paypal.getToken());

            return statement.executeUpdate();
        });
        return inserted <= 0 ? null : paypal;
    }

    @SneakyThrows
    private CreditCardPaymentMethod doCreate(CreditCardPaymentMethod card) {
        final String query = "INSERT INTO payment_method_credit_card (payment_method_id, card_number, card_holder_name, card_verification_valuepayment_method_credit_card (payment_method_id, card_number, card_holder_name, card_verification_value, expiration_, expiration_date) VALUES (?, ?, ?, ?, ?);";
        final int inserted = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, card.getId());
            statement.setString(2, card.getNumber());
            statement.setString(3, card.getHolder());
            statement.setString(4, card.getCvv());
            statement.setDate(5, card.getExpiration());

            return statement.executeUpdate();
        });
        return inserted <= 0 ? null : card;
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public PaymentMethod update(PaymentMethod instance) {
        final Type type = instance.type();
        switch (type) {
            case PAYPAL: return doUpdate((PaypalPaymentMethod) instance);
            case CREDIT_CARD: return doUpdate((CreditCardPaymentMethod) instance);
            default: return null;
        }
    }

    @SneakyThrows
    private PaypalPaymentMethod doUpdate(PaypalPaymentMethod paypal) {
        final String query = "UPDATE payment_method_paypal SET token = ? WHERE payment_method_id = ? LIMIT 1;";
        final int updated = datasource.usePreparedStatement(query, statement -> {
            statement.setString(1, paypal.getToken());
            statement.setInt(2, paypal.getId());

            return statement.executeUpdate();
        });
        return updated <= 0 ? null : paypal;
    }

    @SneakyThrows
    private CreditCardPaymentMethod doUpdate(CreditCardPaymentMethod card) {
        final String query = "UPDATE payment_method_credit_card SET card_number = ?, card_holder_name = ?, card_verification_value = ?, expiration_date = ? WHERE payment_method_id = ? LIMIT 1;";
        final int updated = datasource.usePreparedStatement(query, statement -> {
            statement.setString(1, card.getNumber());
            statement.setString(2, card.getHolder());
            statement.setString(3, card.getCvv());
            statement.setDate(4, card.getExpiration());
            statement.setInt(5, card.getId());

            return statement.executeUpdate();
        });
        return updated <= 0 ? null : card;
    }

    @Override
    @SneakyThrows //TODO: consider implications
    public PaymentMethod delete(PaymentMethod instance) {
        if (instance.getId() == null) {
            return null;
        }
        final String query = "DELETE FROM payment_method WHERE id = ?;";
        final int deleted = datasource.usePreparedStatement(query, statement -> {
            statement.setInt(1, instance.getId());
            return statement.executeUpdate();
        });
        return deleted > 0 ? instance : null;
    }
}
