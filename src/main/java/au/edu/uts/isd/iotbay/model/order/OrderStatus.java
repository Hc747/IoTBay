package au.edu.uts.isd.iotbay.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class OrderStatus {

    private ObjectId id;
    private Order order;
    private String status, details;
    private Timestamp timestamp;

}
