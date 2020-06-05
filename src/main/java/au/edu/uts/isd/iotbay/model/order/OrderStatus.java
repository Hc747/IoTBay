package au.edu.uts.isd.iotbay.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatus {

    private String status, details;
    private Timestamp timestamp;

}
