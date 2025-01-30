package lk.ijse.gdse72.layerdovinplus.dto.tm;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserTM {
    private String userId;
    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
}
