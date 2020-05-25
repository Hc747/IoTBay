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

    @Override
    @SneakyThrows //TODO: consider implications
    public PaymentMethod save(PaymentMethod instance) {
        final Integer id;
        final Type type = instance.type();

        //TODO: insert or update

        if (instance.getId() == null) {
            final String query = "INSERT INTO payment_method (type) VALUES (?);";
            id = datasource.useKeyedPreparedStatement(query, statement -> {
                statement.setString(1, type.name());

                final int affected = statement.executeUpdate();

                if (affected <= 0) {
                    return null;
                }

                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
                return null;
            });
        } else {
            id = instance.getId();
        }

        if (id == null) {
            return null;
        }

        final String query;

        switch (type) {
            case PAYPAL:
                query = "INSERT INTO payment_method_paypal (payment_method_id, token) VALUES (?, ?) ON DUPLICATE KEY UPDATE payment_method_id = VALUES(payment_method_id), token = VALUES(token);";
                break;
            case CREDIT_CARD:
                query = "INSERT INTO payment_method_credit_card (payment_method_id, card_number, card_holder_name, card_verification_value, expiration_date) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE payment_method_id = VALUES(payment_method_id), card_number = VALUES(card_number), card_holder_name = VALUES(card_holder_name), card_verification_value = VALUES(card_verification_value), expiration_date = VALUES(expiration_date);";
                break;
            default: return null;
        }

        final int modified = datasource.usePreparedStatement(query, statement -> {
           statement.setInt(1, id);

           if (type == Type.PAYPAL) {
               final PaypalPaymentMethod method = (PaypalPaymentMethod) instance;
               statement.setString(2, method.getToken());
           } else if (type == Type.CREDIT_CARD) {
               final CreditCardPaymentMethod method = (CreditCardPaymentMethod) instance;
               statement.setString(2, method.getNumber());
               statement.setString(3, method.getHolder());
               statement.setString(4, method.getCvv());
               statement.setDate(5, method.getExpiration());
           } else {
               return -1;
           }

           return statement.executeUpdate();
        });

        if (modified <= 0) {
            return null;
        }

        instance.setId(id);
        return instance;
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
