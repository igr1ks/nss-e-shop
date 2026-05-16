package cz.cvut.fel.nss.user_service.controller;

import cz.cvut.fel.nss.user_service.model.DTO.UserDTO;
import cz.cvut.fel.nss.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for operations on the currently authenticated user
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Returns DTO of actual authenticated user
    // return 200 OK with UserDTO or 401 Unauthorized if user is not signed in
    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(Authentication authentication) {
        String email = authentication.getName();
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }
}