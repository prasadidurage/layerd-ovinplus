package lk.ijse.gdse72.layerdovinplus.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {

        private String userId;
        private String userName;
        private String password;
        private String confirmPassword;
        private String email;
    }


