import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  form = { username: '', email: '', password: '' };
  error = '';
  success = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.error = '';
    this.success = '';
    this.loading = true;
    this.authService.register(this.form).subscribe({
      next: () => {
        this.loading = false;
        this.success = 'Account created. You can sign in now.';
        setTimeout(() => this.router.navigate(['/login']), 700);
      },
      error: () => {
        this.error = 'Registration failed. Try a different username.';
        this.loading = false;
      }
    });
  }
}
