package au.edu.uts.isd.iotbay.persistence.mongo;

import au.edu.uts.isd.iotbay.model.address.Address;
import au.edu.uts.isd.iotbay.model.category.Category;
import au.edu.uts.isd.iotbay.model.invoice.Invoice;
import au.edu.uts.isd.iotbay.model.log.UserLog;
import au.edu.uts.isd.iotbay.model.order.Order;
import au.edu.uts.isd.iotbay.model.order.OrderProduct;
import au.edu.uts.isd.iotbay.model.order.OrderStatus;
import au.edu.uts.isd.iotbay.model.payment.CreditCardPaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaymentMethod;
import au.edu.uts.isd.iotbay.model.payment.PaypalPaymentMethod;
import au.edu.uts.isd.iotbay.model.product.Product;
import au.edu.uts.isd.iotbay.model.shipment.Shipment;
import au.edu.uts.isd.iotbay.model.user.User;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.bild.codec.PojoCodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDatabaseProviderFactory {

    public static MongoDatabaseProvider mongo(String connectionString, String database) {
        final MongoClient client = MongoClients.create(connectionString);
        final PojoCodecProvider provider = PojoCodecProvider.builder()
                .register(
                        Address.class,
                        Category.class,
                        Invoice.class,
                        UserLog.class,
                        Order.class,
                        OrderProduct.class,
                        OrderStatus.class,
                        PaymentMethod.class,
                        PaypalPaymentMethod.class,
                        CreditCardPaymentMethod.class,
                        Product.class,
                        Shipment.class,
                        User.class
                ).build();
        final CodecRegistry registry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(provider));
        final MongoDatabase db = client.getDatabase(database).withCodecRegistry(registry);
        return new MongoDatabaseProviderImpl(client, db);
    }

    private static MongoClientURI uri(String user, String password, String role, String database, List<String> clusters) {
        final StringBuilder builder = new StringBuilder("mongodb://").append(user).append(":").append(password).append("@");

        final StringJoiner hosts = new StringJoiner(",");
        clusters.forEach(hosts::add);

        builder.append(hosts.toString()).append("/").append(database).append("?");
        builder.append("ssl=true&replicaSet=Cluster0-shard-0&retryWrites=true").append("&authSource=").append(role);

        return new MongoClientURI(builder.toString());
    }

    private static class MongoDatabaseProviderImpl implements MongoDatabaseProvider {

        private final MongoClient client;
        private final MongoDatabase database;

        private MongoDatabaseProviderImpl(MongoClient client, MongoDatabase database) {
            this.client = Objects.requireNonNull(client);
            this.database = Objects.requireNonNull(database);
        }

        @Override
        public MongoDatabase database() {
            return database;
        }

        @Override
        public <T> MongoCollection<T> collection(String name, Class<T> clazz) {
            return database.getCollection(name, clazz);
        }

        @Override
        public void close() {
            client.close();
        }
    }
}
