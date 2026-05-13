package cz.cvut.fel.nss.user_service.model.DTO;

import cz.cvut.fel.nss.user_service.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO for a user
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String lastName;
    private String phone;
    private Role role;
    private List<AddressDTO> addresses = new ArrayList<>();
}