package lk.ijse.gdse72.layerdovinplus.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderTM {
    private String tuteId;
    private String tuteName;
    private int cartQuantity;
    private double unitPrice;
    private double total;
    private Button removeBtn;
}
