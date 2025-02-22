package lk.ijse.gdse72.layerdovinplus.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDTO {

    private String orderId;
    private Date orderDate;
    private String studentId;

    private ArrayList<OrderDetailsDTO> orderDetailsDTOS;


    public OrderDTO(String orderId, Date orderDate, String studentId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.studentId = studentId;

    }


}
