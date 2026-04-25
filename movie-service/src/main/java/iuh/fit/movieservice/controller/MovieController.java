package iuh.fit.movieservice.controller;

import iuh.fit.movieservice.model.Movie;
import iuh.fit.movieservice.repository.MovieRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(movieRepository.save(movie));
    }
}
