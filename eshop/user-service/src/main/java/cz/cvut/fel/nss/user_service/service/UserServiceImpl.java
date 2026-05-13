package cz.cvut.fel.nss.user_service.service;

import cz.cvut.fel.nss.user_service.model.BO.User;
import cz.cvut.fel.nss.user_service.model.DTO.RegisterRequest;
import cz.cvut.fel.nss.user_service.model.DTO.UserDTO;
import cz.cvut.fel.nss.user_service.model.utils.UserMapper;
import cz.cvut.fel.nss.user_service.model.enums.Role;
import cz.cvut.fel.nss.user_service.repository.UserRepository;
import cz.cvut.fel.nss.user_service.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Implementation of UserService and Spring-Security UserDetailsService
 * Email is the login identifier (FRQ2)
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional
    public UserDTO registerUser(RegisterRequest request) {
        // Bean Validation already passed in controller

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        User user = userMapper.fromRegisterRequest(request);

        // Password from the request is in plain text - we need to hash it before saving to the database
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);

        User saved = userRepository.save(user);
        return userMapper.toUserDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + email));
        return userMapper.toUserDTO(user);
    }

    // Verifying login, username = email (FRQ2)
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE:" + user.getRole().name())
                ))
                .build();
    }
}