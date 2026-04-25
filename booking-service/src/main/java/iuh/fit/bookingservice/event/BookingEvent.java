package iuh.fit.bookingservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEvent {
    private String eventType;
    private Long bookingId;
    private Long userId;
    private Long movieId;
    private String movieTitle;
    private Integer numberOfSeats;
    private Double totalAmount;
    private LocalDateTime timestamp;
}
