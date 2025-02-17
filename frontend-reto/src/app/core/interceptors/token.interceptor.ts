import { HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { AlertService } from '../services/alert.service';
import { AuthService } from '../services/auth.service';

export const TokenInterceptor: HttpInterceptorFn = (
  request: HttpRequest<any>,
  next: HttpHandlerFn,
): Observable<HttpEvent<unknown>> => {
  const alerta = inject(AlertService);
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getToken();

  let authReq = request;

  if (token) {
    const cloned = authReq.clone({
      headers: authReq.headers
      .set('Authorization', `Bearer ${token}`)
    });

    return next(cloned).pipe(
    catchError((error) => {
      if (error.status === 401 || error.status === 403) {
        authService.removerAuth();
        router.navigate(['login']);
      }

      return throwError(() => error);
    }));
  }
  return next(authReq);
};
