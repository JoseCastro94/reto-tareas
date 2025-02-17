import { NgxSpinnerService } from 'ngx-spinner';
import { HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { Observable, finalize } from 'rxjs';
import { inject } from '@angular/core';

export const loadingInterceptor: HttpInterceptorFn = (
  request: HttpRequest<any>,
  next: HttpHandlerFn,
): Observable<HttpEvent<unknown>> =>{
  const spinner = inject(NgxSpinnerService);
  spinner.show();

  return next(request).pipe(
    finalize(() =>
      spinner.hide()
  ));
}




