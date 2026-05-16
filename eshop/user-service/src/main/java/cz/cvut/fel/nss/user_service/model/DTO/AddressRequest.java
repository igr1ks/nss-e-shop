package cz.cvut.fel.nss.user_service.model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for creating/editing addresses
 */
@Getter
@Setter
@NoArgsConstructor
public class AddressRequest {

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must be at most 100 characters")
    private String city;

    @NotBlank(message = "Street is required")
    @Size(max = 150, message = "Street must be at most 150 characters")
    private String street;

    @NotBlank(message = "Zip code is required")
    @Pattern(
            regexp = "^[0-9 \\-]{3,20}$",
            message = "Zip code must contain 3-20 digits, spaces or dashes"
    )
    private String zipCode;
}