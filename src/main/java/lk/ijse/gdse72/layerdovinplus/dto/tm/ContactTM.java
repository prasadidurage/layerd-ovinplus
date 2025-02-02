package lk.ijse.gdse72.layerdovinplus.dto.tm;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class ContactTM {
    private String contactId;
    private Date date;
    private String studentName;
    private int contactNumber;
}
