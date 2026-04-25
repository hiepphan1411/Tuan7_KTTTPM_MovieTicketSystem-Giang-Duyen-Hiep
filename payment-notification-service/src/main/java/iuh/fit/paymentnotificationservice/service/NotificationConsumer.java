package iuh.fit.paymentnotificationservice.service;

import iuh.fit.paymentnotificationservice.event.PaymentEvent;
import iuh.fit.paymentnotificationservice.event.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-events", groupId = "notification-group")
    public void handlePaymentEvent(String payload) {
        PaymentEvent event = readEvent(payload, PaymentEvent.class);
        if (event == null) {
            return;
        }

        System.out.println("==================================================");
        if ("PAYMENT_COMPLETED".equals(event.getEventType())) {
            System.out.println("[NOTIFICATION] Booking #" + event.getBookingId() + " SUCCESS");
            System.out.println("[NOTIFICATION] User #" + event.getUserId()
                + " booked '" + event.getMovieTitle() + "'");
            System.out.println("[NOTIFICATION] Amount: " + event.getAmount());
        } else if ("BOOKING_FAILED".equals(event.getEventType())) {
            System.out.println("[NOTIFICATION] Booking #" + event.getBookingId() + " FAILED");
            System.out.println("[NOTIFICATION] Reason: " + event.getMessage());
        }
        System.out.println("==================================================");
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void handleUserEvent(String payload) {
        UserEvent event = readEvent(payload, UserEvent.class);
        if (event == null || !"USER_REGISTERED".equals(event.getEventType())) {
            return;
        }

        System.out.println("[NOTIFICATION] New user registered: " + event.getUsername()
            + " (" + event.getEmail() + ")");
    }

    private <T> T readEvent(String payload, Class<T> type) {
        try {
            return objectMapper.readValue(payload, type);
        } catch (Exception ex) {
            System.out.println("[NOTIFICATION] Failed to parse event: " + ex.getMessage());
            return null;
        }
    }
}
