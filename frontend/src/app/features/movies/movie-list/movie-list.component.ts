import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { Movie, MovieService } from '../../../core/services/movie.service';

@Component({
  selector: 'app-movie-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './movie-list.component.html',
  styleUrl: './movie-list.component.css'
})
export class MovieListComponent implements OnInit {
  movies: Movie[] = [];
  loading = true;

  constructor(private movieService: MovieService, private router: Router) {}

  ngOnInit() {
    this.movieService.getMovies().subscribe({
      next: (data) => {
        this.movies = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  selectMovie(movie: Movie) {
    this.router.navigate(['/booking'], { state: { movie } });
  }
}
