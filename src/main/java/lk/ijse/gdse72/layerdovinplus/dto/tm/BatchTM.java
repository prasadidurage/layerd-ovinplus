package lk.ijse.gdse72.layerdovinplus.dto.tm;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BatchTM  {
    private String batchId;
    private String batchName;
    private int studentCount;
}
