import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { BookingService } from '../../../core/services/booking.service';

@Component({
  selector: 'app-booking-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './booking-form.component.html',
  styleUrl: './booking-form.component.css'
})
export class BookingFormComponent implements OnInit {
  movie: any = null;
  numberOfSeats = 1;
  loading = false;
  success = '';
  error = '';

  constructor(private router: Router, private bookingService: BookingService) {}

  ngOnInit() {
    const state = typeof history !== 'undefined' ? history.state : null;
    if (!state?.movie) {
      this.router.navigate(['/movies']);
      return;
    }
    this.movie = state.movie;
  }

  get totalAmount() {
    return this.movie ? this.movie.price * this.numberOfSeats : 0;
  }

  submitBooking() {
    if (!this.movie) {
      return;
    }
    this.loading = true;
    this.error = '';
    this.success = '';

    const bookingData = {
      userId: 1,
      movieId: this.movie.id,
      movieTitle: this.movie.title,
      numberOfSeats: this.numberOfSeats,
      totalAmount: this.totalAmount
    };

    this.bookingService.createBooking(bookingData).subscribe({
      next: (booking) => {
        this.success = `Booking #${booking.id} created. Payment is processing.`;
        this.loading = false;
      },
      error: () => {
        this.error = 'Booking failed. Please try again.';
        this.loading = false;
      }
    });
  }
}
