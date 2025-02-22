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

    private ArrayList<OrderDetail> orderDetails;

    public Order(String orderId, Date orderDate, String studentId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.studentId = studentId;
        this.orderDetails = new ArrayList<>(); // Initialize empty list
    }
}
