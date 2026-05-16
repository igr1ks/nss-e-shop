package cz.cvut.fel.nss.user_service.model.DTO;

import cz.cvut.fel.nss.user_service.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request body for changing a user's role (for admin)
 */
@Getter
@Setter
@NoArgsConstructor
public class ChangeRoleRequest {

    @NotNull(message = "Role is required")
    private Role role;
}