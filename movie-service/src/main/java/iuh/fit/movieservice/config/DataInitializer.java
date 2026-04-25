package iuh.fit.movieservice.config;

import iuh.fit.movieservice.model.Movie;
import iuh.fit.movieservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final MovieRepository movieRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (movieRepository.count() > 0) {
            return;
        }

        movieRepository.saveAll(List.of(
            Movie.builder()
                .title("Avengers")
                .genre("Action")
                .duration(180)
                .price(120000.0)
                .status("SHOWING")
                .build(),
            Movie.builder()
                .title("The Batman")
                .genre("Action")
                .duration(156)
                .price(110000.0)
                .status("SHOWING")
                .build(),
            Movie.builder()
                .title("Dune Part 2")
                .genre("Sci-Fi")
                .duration(166)
                .price(130000.0)
                .status("UPCOMING")
                .build()
        ));
    }
}
