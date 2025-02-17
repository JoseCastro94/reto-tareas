import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  success(message: string, title: string = 'Éxito') {
    Swal.fire({
      title: title,
      text: message,
      icon: 'success',
      confirmButtonText: 'Aceptar',
      customClass: {
        popup: 'swal-popup'
      }
    });
  }

  info(message: string, title: string = 'Información') {
    Swal.fire({
      title: title,
      text: message,
      icon: 'info',
      confirmButtonText: 'Aceptar',
      customClass: {
        popup: 'swal-popup'
      }
    });
  }

  warning(message: string, title: string = 'Advertencia') {
    Swal.fire({
      title: title,
      text: message,
      icon: 'warning',
      confirmButtonText: 'Aceptar',
      customClass: {
        popup: 'swal-popup'
      }
    });
  }

  error(message: string, title: string = 'Error') {
    Swal.fire({
      title: title,
      text: message,
      icon: 'error',
      confirmButtonText: 'Aceptar',
      customClass: {
        popup: 'swal-popup'
      }
    });
  }

  confirm(message: string, title: string = 'Confirmación') {
    return Swal.fire({
      title: title,
      text: message,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí',
      cancelButtonText: 'No',
      customClass: {
        popup: 'swal-popup'
      }
    }).then((result) => result.isConfirmed);
  }
}
