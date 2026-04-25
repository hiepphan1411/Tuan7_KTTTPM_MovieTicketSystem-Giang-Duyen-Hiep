import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Booking {
  id: number;
  userId: number;
  movieId: number;
  movieTitle: string;
  numberOfSeats: number;
  totalAmount: number;
  status: string;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class BookingService {
  private readonly baseUrl = environment.bookingServiceUrl;

  constructor(private http: HttpClient) {}

  createBooking(data: Partial<Booking>): Observable<Booking> {
    return this.http.post<Booking>(`${this.baseUrl}/bookings`, data);
  }

  getUserBookings(userId: number): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.baseUrl}/bookings/user/${userId}`);
  }
}
