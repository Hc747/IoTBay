package au.edu.uts.isd.iotbay.persistence.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public interface MongoDatabaseProvider extends AutoCloseable {

    MongoDatabase database();

    <T> MongoCollection<T> collection(String name, Class<T> clazz);

    default MongoCollection<Document> collection(String name) {
        return collection(name, Document.class);
    }
}
