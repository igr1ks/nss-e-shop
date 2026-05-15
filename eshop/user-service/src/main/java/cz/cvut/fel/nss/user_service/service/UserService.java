package cz.cvut.fel.nss.user_service.service;

import cz.cvut.fel.nss.user_service.model.DTO.RegisterRequest;
import cz.cvut.fel.nss.user_service.model.DTO.UserDTO;
import cz.cvut.fel.nss.user_service.model.enums.Role;

import java.util.List;

/**
 * Service interface for user management
 */
public interface UserService {

    /**
     * User registration
     * - Verifies that the email does not already exist
     * - Encrypts the password with BCrypt
     * - Saves the user with the CUSTOMER role
     *
     * @return DTO of the newly registered user (without password)
     * @throws IllegalArgumentException if email already exists or data is invalid
     */
    UserDTO registerUser(RegisterRequest request);

    /**
     * Finds a user by email and returns their DTO.
     *
     * @throws IllegalArgumentException if user does not exist
     */
    UserDTO getUserByEmail(String email);

    // Admin operations
    /**
     * Returns all users matching the given filters (Specification pattern)
     * Any filter argument may be null to skip it.
     */
    List<UserDTO> searchUsers(String emailFragment, String nameFragment,
                              String lastNameFragment, Role role);

    /** Returns the DTO of the user with the given id */
    UserDTO getUserById(Long id);

    /** Changes the role of the user with the given id */
    UserDTO changeUserRole(Long id, Role newRole);

    /** Deletes the user with the given id (and all related addresses) */
    void deleteUser(Long id);
}