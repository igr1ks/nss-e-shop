package cz.cvut.fel.nss.user_service.model.utils;

import cz.cvut.fel.nss.user_service.model.BO.Address;
import cz.cvut.fel.nss.user_service.model.BO.User;
import cz.cvut.fel.nss.user_service.model.DTO.AddressDTO;
import cz.cvut.fel.nss.user_service.model.DTO.RegisterRequest;
import cz.cvut.fel.nss.user_service.model.DTO.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for converting between business objects (User, Address)
 * and DTO objects (UserDTO, AddressDTO)
 */
@Component
public class UserMapper {

    // Converts an Address entity to AddressDTO
    public AddressDTO toAddressDTO(Address address) {
        if (address == null) return null;
        return new AddressDTO(
                address.getId(),
                address.getCity(),
                address.getStreet(),
                address.getZipCode()
        );
    }

    // Converts a User entity to UserDTO (without password)
    public UserDTO toUserDTO(User user) {
        if (user == null) return null;

        List<AddressDTO> addressDTOs = user.getAddresses().stream()
                .map(this::toAddressDTO)
                .toList();

        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole(),
                addressDTOs
        );
    }

    // Create a new User entity from RegisterRequest
    // Role is always CUSTOMER (default), admin can only be set manually in DB
    public User fromRegisterRequest(RegisterRequest request) {
        if (request == null) return null;
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // plain
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        return user;
    }
}