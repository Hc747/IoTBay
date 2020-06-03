package au.edu.uts.isd.iotbay.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class OrderStatus {

    private String status, details;
    private Timestamp timestamp;

}
