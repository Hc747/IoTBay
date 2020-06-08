package au.edu.uts.isd.iotbay.persistence.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * The {@code MongoDatabaseProvider} interface provides a layer of abstraction over how the underlying {@code MongoDatabase}
 * is produced. Furthermore, it provides mechanisms for producing a reference to a {@code MongoCollection}
 */
public interface MongoDatabaseProvider extends AutoCloseable {

    MongoDatabase database();

    <T> MongoCollection<T> collection(String name, Class<T> clazz);

    default MongoCollection<Document> collection(String name) {
        return collection(name, Document.class);
    }
}
