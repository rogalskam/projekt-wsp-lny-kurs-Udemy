package com.example.auth.services;

import com.example.auth.entity.Role;
import com.example.auth.entity.UserRegisterDTO;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    public String generateToken(String username) {
       return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public void register(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setLogin(userRegisterDTO.getLogin());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());

        if(userRegisterDTO.getRole() != null) {
            user.setRole(userRegisterDTO.getRole());
        } else user.setRole(Role.USER);

        saveUser(user);
    }
}
