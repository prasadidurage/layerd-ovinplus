package lk.ijse.gdse72.layerdovinplus.entity;


import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    private String orderId;
    private String tuteId;
    private int quantity;
    private double price;
}
