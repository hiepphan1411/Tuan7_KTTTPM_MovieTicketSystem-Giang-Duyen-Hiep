package iuh.fit.movieservice.repository;

import iuh.fit.movieservice.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
