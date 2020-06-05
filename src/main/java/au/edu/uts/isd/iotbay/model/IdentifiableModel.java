package au.edu.uts.isd.iotbay.model;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
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
