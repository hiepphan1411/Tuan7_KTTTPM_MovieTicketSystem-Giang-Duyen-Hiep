import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Booking, BookingService } from '../../../core/services/booking.service';

@Component({
  selector: 'app-booking-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './booking-list.component.html',
  styleUrl: './booking-list.component.css'
})
export class BookingListComponent implements OnInit {
  bookings: Booking[] = [];
  loading = true;

  constructor(private bookingService: BookingService) {}

  ngOnInit() {
    this.bookingService.getUserBookings(1).subscribe({
      next: (data) => {
        this.bookings = data;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }
}
