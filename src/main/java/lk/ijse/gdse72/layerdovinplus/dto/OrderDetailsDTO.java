package lk.ijse.gdse72.layerdovinplus.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDTO {
    private String orderId;
    private String tuteId;
    private int quantity;
    private double price;
}
