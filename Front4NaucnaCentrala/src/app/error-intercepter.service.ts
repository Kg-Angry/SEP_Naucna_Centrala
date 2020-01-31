import Swal from 'sweetalert2';
import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorIntercepterService {

  constructor() { }

  handleError(error: any) {
    if (error instanceof HttpErrorResponse) {
      if (error.status === 401) {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Username ili password nisu ispravni!',
          showConfirmButton: false,
          timer: 3500
        });
        timer(3500).subscribe(t => location.href = '/login');
      }else if(error.status === 504)
      {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Problem sa serverom! Molim vas malo sacekajte.',
          showConfirmButton: false,
          timer: 3500
        });
      }
    }
  }
}
