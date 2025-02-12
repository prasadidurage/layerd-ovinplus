package lk.ijse.gdse72.layerdovinplus.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String paymentId;
    private String deliveryId;
    private Date paymentDate;
    private double amount;
}
