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

public abstract class MongoRepository<T extends Identifiable> implements Repository<T> {

    protected final MongoDatabaseProvider datasource;
    protected final MongoCollection<T> collection;

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

    protected Bson identity(ObjectId id) {
        return eq("_id", id);
    }

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
