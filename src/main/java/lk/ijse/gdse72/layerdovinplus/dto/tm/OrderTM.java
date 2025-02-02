package lk.ijse.gdse72.layerdovinplus.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderTM {
    private String orderId;
    private Date orderDate;
    private String studentId;

}
