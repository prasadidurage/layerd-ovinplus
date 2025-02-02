package lk.ijse.gdse72.layerdovinplus.dto.tm;

import lombok.*;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymrntTM {
    private String paymentId;
    private String deliveryId;
    private Date paymentDate;
    private double amount;
}
