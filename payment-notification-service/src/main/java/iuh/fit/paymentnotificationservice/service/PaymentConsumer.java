package iuh.fit.paymentnotificationservice.service;

import iuh.fit.paymentnotificationservice.event.BookingEvent;
import iuh.fit.paymentnotificationservice.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final Random random = new Random();

    @KafkaListener(topics = "booking-events", groupId = "payment-group")
    public void handleBookingCreated(String payload) {
        BookingEvent event = readEvent(payload, BookingEvent.class);
        if (event == null || !"BOOKING_CREATED".equals(event.getEventType())) {
            return;
        }

        System.out.println("[PAYMENT] Received BOOKING_CREATED for booking: " + event.getBookingId());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        boolean isSuccess = random.nextInt(10) < 7;
        PaymentEvent paymentEvent = PaymentEvent.builder()
            .bookingId(event.getBookingId())
            .userId(event.getUserId())
            .movieTitle(event.getMovieTitle())
            .amount(event.getTotalAmount())
            .timestamp(LocalDateTime.now())
            .build();

        if (isSuccess) {
            paymentEvent.setEventType("PAYMENT_COMPLETED");
            paymentEvent.setMessage("Payment processed successfully");
            System.out.println("[PAYMENT] SUCCESS for booking: " + event.getBookingId());
        } else {
            paymentEvent.setEventType("BOOKING_FAILED");
            paymentEvent.setMessage("Payment failed - insufficient funds");
            System.out.println("[PAYMENT] FAILED for booking: " + event.getBookingId());
        }

        kafkaTemplate.send("payment-events", paymentEvent);
    }

    private <T> T readEvent(String payload, Class<T> type) {
        try {
            return objectMapper.readValue(payload, type);
        } catch (Exception ex) {
            System.out.println("[PAYMENT] Failed to parse event: " + ex.getMessage());
            return null;
        }
    }
}
