package lk.ijse.gdse72.layerdovinplus.dto;

import lombok.*;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PaymentDTO {
    private String paymentId;
    private String deliveryId;
    private Date paymentDate;
    private double amount;
}
