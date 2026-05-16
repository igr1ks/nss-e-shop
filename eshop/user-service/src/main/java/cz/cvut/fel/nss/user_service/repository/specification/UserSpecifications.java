package cz.cvut.fel.nss.user_service.repository.specification;

import cz.cvut.fel.nss.user_service.model.BO.User;
import cz.cvut.fel.nss.user_service.model.enums.Role;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification Pattern - building blocks for dynamic User queries
 *
 * Each static method returns a small "specification"
 * They can be combined freely using and/or/not
 *
 * Example:
 *   Specification&lt;User&gt; spec = Specification
 *       .where(UserSpecifications.hasRole(Role.ADMIN))
 *       .and(UserSpecifications.emailContains("kari"));
 *   List&lt;User&gt; results = userRepository.findAll(spec);
 *
 * We use it so we don't need to create lots of finder method in UserRepository
 */
public final class UserSpecifications {

    private UserSpecifications() {
    }

    // Returns users whose email contains the given part
    // If the fragment is null or blank the specification is a no-op
    public static Specification<User> emailContains(String fragment) {
        return (root, query, cb) -> {
            if (fragment == null || fragment.isBlank()) {
                return cb.conjunction(); // always-true predicate
            }
            return cb.like(cb.lower(root.get("email")), "%" + fragment.toLowerCase() + "%");
        };
    }

    // Returns users whose first name contains the given part
    public static Specification<User> nameContains(String fragment) {
        return (root, query, cb) -> {
            if (fragment == null || fragment.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), "%" + fragment.toLowerCase() + "%");
        };
    }

    // Returns users whose last name contains the given part
    public static Specification<User> lastNameContains(String fragment) {
        return (root, query, cb) -> {
            if (fragment == null || fragment.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("lastName")), "%" + fragment.toLowerCase() + "%");
        };
    }

    // Returns users with the given role. Null = "any role"
    public static Specification<User> hasRole(Role role) {
        return (root, query, cb) -> {
            if (role == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("role"), role);
        };
    }
}