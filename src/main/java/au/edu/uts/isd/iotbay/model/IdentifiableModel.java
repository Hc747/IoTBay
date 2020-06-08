package au.edu.uts.isd.iotbay.model;

import org.bson.types.ObjectId;

/**
 * The base class for Models to be persisted in MongoDB.
 */
public class IdentifiableModel implements Identifiable {

    protected ObjectId _id;

    protected IdentifiableModel(ObjectId id) {
        this._id = id;
    }

    public IdentifiableModel() {
        this(null);
    }

    @Override
    public ObjectId getId() {
        return _id;
    }

    @Override
    public void setId(ObjectId id) {
        this._id = id;
    }
}
