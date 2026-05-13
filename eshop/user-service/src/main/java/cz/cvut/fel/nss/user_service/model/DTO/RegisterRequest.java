package cz.cvut.fel.nss.user_service.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for signing up
 */
@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phone;
}