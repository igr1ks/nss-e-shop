package cz.cvut.fel.nss.user_service.service;

import cz.cvut.fel.nss.user_service.model.DTO.RegisterRequest;
import cz.cvut.fel.nss.user_service.model.DTO.UserDTO;

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
}