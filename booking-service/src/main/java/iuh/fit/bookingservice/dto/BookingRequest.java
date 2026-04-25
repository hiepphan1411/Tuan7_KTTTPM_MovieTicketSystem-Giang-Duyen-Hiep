package iuh.fit.bookingservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long movieId;

    @NotBlank
    private String movieTitle;

    @NotNull
    @Min(1)
    private Integer numberOfSeats;

    @NotNull
    private Double totalAmount;
}
