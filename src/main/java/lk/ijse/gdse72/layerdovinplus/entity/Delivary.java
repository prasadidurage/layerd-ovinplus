package lk.ijse.gdse72.layerdovinplus.entity;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Delivary {
    private String deliveryId;
    private Date deliveryDate;
    private String status;
    private String orderId;
}
