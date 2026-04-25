package iuh.fit.userservice.service;

import iuh.fit.userservice.config.JwtService;
import iuh.fit.userservice.dto.LoginRequest;
import iuh.fit.userservice.dto.RegisterRequest;
import iuh.fit.userservice.event.UserEvent;
import iuh.fit.userservice.model.User;
import iuh.fit.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())
            || userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role("USER")
            .build();
        user = userRepository.save(user);

        UserEvent event = UserEvent.builder()
            .eventType("USER_REGISTERED")
            .userId(user.getId())
            .email(user.getEmail())
            .username(user.getUsername())
            .timestamp(LocalDateTime.now())
            .build();
        kafkaTemplate.send("user-events", event);

        return "User registered successfully";
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(user.getUsername(), user.getRole());
    }
}
