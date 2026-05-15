package cz.cvut.fel.nss.user_service.controller;

import cz.cvut.fel.nss.user_service.model.DTO.ChangeRoleRequest;
import cz.cvut.fel.nss.user_service.model.DTO.UserDTO;
import cz.cvut.fel.nss.user_service.model.enums.Role;
import cz.cvut.fel.nss.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin-only REST endpoints for managing users
 *
 * All methods are protected by @PreAuthorize("hasRole('ADMIN')").
 * - JWT token must be present (otherwise 401)
 * - Token's role claim must include ROLE_ADMIN (otherwise 403)
 *
 * Search endpoint demonstrates the Specification Pattern - filters
 * are combined dynamically based on which query parameters are present.
 */
@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * List / search users with optional filters.
     *
     * Example:
     *   GET /admin/users?email=kari&role=CUSTOMER
     *   GET /admin/users?lastName=Mal
     *   GET /admin/users (no filters - returns all)
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> searchUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Role role) {

        return ResponseEntity.ok(userService.searchUsers(email, name, lastName, role));
    }

    // Returns details of a single user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Changes a user's role (CUSTOMER <-> ADMIN)
    @PutMapping("/{id}/role")
    public ResponseEntity<UserDTO> changeRole(@PathVariable Long id,
                                              @Valid @RequestBody ChangeRoleRequest request) {
        return ResponseEntity.ok(userService.changeUserRole(id, request.getRole()));
    }

    // Deletes a user (and all related addresses)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}