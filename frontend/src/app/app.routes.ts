import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
	{ path: '', redirectTo: '/movies', pathMatch: 'full' },
	{
		path: 'login',
		loadComponent: () =>
			import('./features/auth/login/login.component').then((m) => m.LoginComponent)
	},
	{
		path: 'register',
		loadComponent: () =>
			import('./features/auth/register/register.component').then((m) => m.RegisterComponent)
	},
	{
		path: 'movies',
		canActivate: [authGuard],
		loadComponent: () =>
			import('./features/movies/movie-list/movie-list.component').then((m) => m.MovieListComponent)
	},
	{
		path: 'booking',
		canActivate: [authGuard],
		loadComponent: () =>
			import('./features/bookings/booking-form/booking-form.component').then((m) => m.BookingFormComponent)
	},
	{
		path: 'bookings',
		canActivate: [authGuard],
		loadComponent: () =>
			import('./features/bookings/booking-list/booking-list.component').then((m) => m.BookingListComponent)
	},
	{ path: '**', redirectTo: '/movies' }
];
