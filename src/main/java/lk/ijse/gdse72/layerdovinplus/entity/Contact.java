package lk.ijse.gdse72.layerdovinplus.entity;

import java.util.Date;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Contact{
    private String contactId;
    private Date date;
    private String studentName;
    private int contactNumber;
}
