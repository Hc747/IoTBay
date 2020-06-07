package au.edu.uts.isd.iotbay.model;

import org.bson.types.ObjectId;

public interface Identifiable {

    ObjectId getId();

    void setId(ObjectId id);

}
