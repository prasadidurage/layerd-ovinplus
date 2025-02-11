package lk.ijse.gdse72.layerdovinplus.entity;

import lk.ijse.gdse72.layerdovinplus.dto.OrderDetailsDTO;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private Date orderDate;
    private String studentId;

    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;
}
