package lk.ijse.gdse72.layerdovinplus.dto;

import lombok.*;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ContactDTO {
    private String contactId;
    private Date date;
    private String studentName;
    private int contactNumber;
}
