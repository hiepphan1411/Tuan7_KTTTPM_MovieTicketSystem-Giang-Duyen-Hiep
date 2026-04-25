import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Movie {
  id: number;
  title: string;
  description?: string;
  genre?: string;
  director?: string;
  duration?: number;
  price?: number;
  status?: string;
}

@Injectable({ providedIn: 'root' })
export class MovieService {
  private readonly baseUrl = environment.movieServiceUrl;

  constructor(private http: HttpClient) {}

  getMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${this.baseUrl}/movies`);
  }
}
