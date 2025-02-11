package lk.ijse.gdse72.layerdovinplus.dto;

import lombok.*;

import java.sql.Date;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public class DelivaryDTO {
        private String deliveryId;
        private Date deliveryDate;
        private String status;
        private String orderId;


    }
