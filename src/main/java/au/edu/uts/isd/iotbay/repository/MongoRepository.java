package au.edu.uts.isd.iotbay.repository;

import au.edu.uts.isd.iotbay.model.Identifiable;
import au.edu.uts.isd.iotbay.persistence.mongo.MongoDatabaseProvider;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

/**
 * Provides the underlying {@code Repository} functionality for interfacing with a MongoDB Collection.
 * Persists between deployments.
 *
 * @param <T>
 *     The type of elements stored within the underlying {@code MongoCollection}.
 */
public abstract class MongoRepository<T extends Identifiable> implements Repository<T> {

    protected final MongoDatabaseProvider datasource;
    protected final MongoCollection<T> collection;

    /**
     * Constructs a MongoRepository
     * @param datasource
     *      The underlying database provider.
     * @param clazz
     *      The class of object to persist.
     * @param collection
     *      The name of the collection within MongoDB.
     */
    protected MongoRepository(MongoDatabaseProvider datasource, Class<T> clazz, String collection) {
        this.datasource = Objects.requireNonNull(datasource);
        this.collection = datasource.collection(collection, clazz);
    }

    protected MongoRepository(MongoDatabaseProvider datasource, Class<T> clazz) {
        this(datasource, clazz, clazz.getSimpleName());
    }


    protected Bson identity(T instance) {
        return identity(instance.getId());
    }

    /**
     * Generates a MongoDB BSON criteria for matching objects by ID.
     */
    protected Bson identity(ObjectId id) {
        return eq("_id", id);
    }

    @Override
    public T findById(ObjectId id) {
        return find(identity(id));
    }

    public T find(Bson criteria) {
        return collection.find(criteria).first();
    }

    public Collection<T> findAll(Bson criteria) {
        return collection.find(criteria).into(new ArrayList<>());
    }

    @Override
    public Collection<T> all() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public T create(T instance) {
        if (instance.getId() == null) {
            //TODO: ensure logic is correct
            instance.setId(new ObjectId());
            this.collection.insertOne(instance);
            return instance;
        }
        return update(instance);
    }

    @Override
    public T update(T instance) {
        return collection.findOneAndReplace(identity(instance), instance);
    }

    @Override
    public T delete(T instance) {
        return collection.findOneAndDelete(identity(instance));
    }
}
