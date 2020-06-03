package au.edu.uts.isd.iotbay.persistence.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDatabaseProviderFactory {

    public static MongoDatabaseProvider mongo(String connectionString, String database) {
        //TODO: validate inputs
//        final MongoClientURI uri = uri(user, password, role, database, clusters);
//        final MongoClient client = new MongoClient(uri);
//        final MongoCredential credentials = MongoCredential.createCredential(user, database, password.toCharArray()); //TODO: necessity of credentials
        final MongoClient client = MongoClients.create(connectionString);
        final CodecRegistry registry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
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
