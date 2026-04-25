package iuh.fit.userservice.controller;

import iuh.fit.userservice.dto.UserResponse;
import iuh.fit.userservice.model.User;
import iuh.fit.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200", "*"})
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
            .map(user -> ResponseEntity.ok(mapUser(user)))
            .orElse(ResponseEntity.notFound().build());
    }

    private UserResponse mapUser(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }
}
