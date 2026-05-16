package cz.cvut.fel.nss.user_service.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service interface for handling JWT tokens.
 */
public interface JwtService {

    /** Generates a JWT token for the given user */
    String generateToken(UserDetails userDetails);

    /** Returns the email extracted from the token, or throws if the token is invalid */
    String extractEmail(String token);

    /** Verifies signature, expiration and that the token belongs to the given user */
    boolean isTokenValid(String token, UserDetails userDetails);
}