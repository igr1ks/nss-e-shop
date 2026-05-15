package cz.cvut.fel.nss.user_service.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response returned after a successful login
 * The client stores the token and sends it in the
 * "Authorization: Bearer <token>" header on subsequent requests
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserDTO user;
}