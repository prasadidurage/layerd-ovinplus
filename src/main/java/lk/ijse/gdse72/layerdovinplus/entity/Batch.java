package lk.ijse.gdse72.layerdovinplus.entity;

import lombok .*;

@Data @Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode @ToString

public class Batch {
    private String batchID;
    private String batchName;
    private int studentCount;
}
