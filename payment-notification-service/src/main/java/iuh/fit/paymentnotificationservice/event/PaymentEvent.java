package iuh.fit.paymentnotificationservice.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentEvent {
    private String eventType;
    private Long bookingId;
    private Long userId;
    private String movieTitle;
    private Double amount;
    private String message;
    private LocalDateTime timestamp;
}
