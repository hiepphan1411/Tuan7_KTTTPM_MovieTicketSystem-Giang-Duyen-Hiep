package iuh.fit.userservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEvent {
    private String eventType;
    private Long userId;
    private String email;
    private String username;
    private LocalDateTime timestamp;
}
