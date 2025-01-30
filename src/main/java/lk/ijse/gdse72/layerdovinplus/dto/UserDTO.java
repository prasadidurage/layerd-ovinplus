package lk.ijse.gdse72.layerdovinplus.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String userId;
    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
}
