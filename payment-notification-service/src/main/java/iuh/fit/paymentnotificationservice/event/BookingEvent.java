package iuh.fit.paymentnotificationservice.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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
