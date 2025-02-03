package lk.ijse.gdse72.layerdovinplus.entity;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String studentId;
    private String studentName;
    private String address;
    private String batchId;
}
