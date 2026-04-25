package iuh.fit.bookingservice.service;

import iuh.fit.bookingservice.dto.BookingRequest;
import iuh.fit.bookingservice.event.BookingEvent;
import iuh.fit.bookingservice.model.Booking;
import iuh.fit.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Booking createBooking(BookingRequest request) {
        Booking booking = Booking.builder()
            .userId(request.getUserId())
            .movieId(request.getMovieId())
            .movieTitle(request.getMovieTitle())
            .numberOfSeats(request.getNumberOfSeats())
            .totalAmount(request.getTotalAmount())
            .status("PENDING")
            .createdAt(LocalDateTime.now())
            .build();
        booking = bookingRepository.save(booking);

        BookingEvent event = BookingEvent.builder()
            .eventType("BOOKING_CREATED")
            .bookingId(booking.getId())
            .userId(booking.getUserId())
            .movieId(booking.getMovieId())
            .movieTitle(booking.getMovieTitle())
            .numberOfSeats(booking.getNumberOfSeats())
            .totalAmount(booking.getTotalAmount())
            .timestamp(LocalDateTime.now())
            .build();
        kafkaTemplate.send("booking-events", event);
        System.out.println("[BOOKING] Published BOOKING_CREATED for booking: " + booking.getId());

        return booking;
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
}
