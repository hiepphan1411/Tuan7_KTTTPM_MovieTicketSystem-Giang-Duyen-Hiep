import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly baseUrl = environment.userServiceUrl;

  constructor(private http: HttpClient) {}

  register(data: { username: string; email: string; password: string }): Observable<string> {
    return this.http.post(`${this.baseUrl}/auth/register`, data, { responseType: 'text' });
  }

  login(data: { username: string; password: string }): Observable<{ token: string }> {
    return this.http.post<{ token: string }>(`${this.baseUrl}/auth/login`, data).pipe(
      tap((res) => {
        if (this.isBrowser()) {
          localStorage.setItem('token', res.token);
        }
      })
    );
  }

  logout(): void {
    if (this.isBrowser()) {
      localStorage.removeItem('token');
    }
  }

  getToken(): string | null {
    return this.isBrowser() ? localStorage.getItem('token') : null;
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  private isBrowser(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }
}
