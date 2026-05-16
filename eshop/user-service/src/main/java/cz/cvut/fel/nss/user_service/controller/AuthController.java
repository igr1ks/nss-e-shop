package cz.cvut.fel.nss.user_service.controller;

import cz.cvut.fel.nss.user_service.model.DTO.LoginRequest;
import cz.cvut.fel.nss.user_service.model.DTO.LoginResponse;
import cz.cvut.fel.nss.user_service.model.DTO.RegisterRequest;
import cz.cvut.fel.nss.user_service.model.DTO.UserDTO;
import cz.cvut.fel.nss.user_service.service.JwtService;
import cz.cvut.fel.nss.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentification endpoints
 *
 * FRQ1 - POST /register : sign up new user
 * FRQ2 - POST /login : sign in using email, password
 */
@RestController
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * FRQ1 - Sign up new user
     *
     * Body: { "email": "...", "password": "...", "name": "...",
     *         "lastName": "...", "phone": "..." }
     * Returns 201 Created s UserDTO if success
     * Returns 400 Bad Request if not valid data/duplicated email
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest request) {
        UserDTO user = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * FRQ2 - Sign in using email and password
     *
     * Body: { "email": "...", "password": "..." }
     * Check credentials with Spring Security AuthenticationManager
     * - If success returns 200 OK with UserDTO
     * - If non-valid credentials returns 401 Unauthorized
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(userDetails);
            UserDTO user = userService.getUserByEmail(userDetails.getUsername());

            return ResponseEntity.ok(new LoginResponse(token, user));
        } catch (AuthenticationException ex) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }
}