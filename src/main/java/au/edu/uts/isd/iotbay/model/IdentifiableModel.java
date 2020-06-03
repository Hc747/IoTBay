package au.edu.uts.isd.iotbay.model;

import org.bson.types.ObjectId;

public class IdentifiableModel implements Identifiable {

    protected ObjectId id;

    protected IdentifiableModel(ObjectId id) {
        this.id = id;
    }

    public IdentifiableModel() {
        this(null);
    }

    @Override
    public ObjectId getId() {
        return id;
    }

    @Override
    public void setId(ObjectId id) {
        this.id = id;
    }
}
