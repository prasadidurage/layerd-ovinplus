package lk.ijse.gdse72.layerdovinplus.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryTM {
    private String deliveryId;
    private Date deliveryDate;
    private String status;
    private String orderId;
}
