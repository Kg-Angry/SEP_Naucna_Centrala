import Swal from 'sweetalert2';
import { Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

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
      } else if (error.status === 403) {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Izmena nije omogucena!',
          showConfirmButton: false,
          timer: 3500
        });
      } else if (error.status === 406) {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Korisnik ne postoji u bazi!',
          showConfirmButton: false,
          timer: 3500
        });
      } else if (error.status === 409) {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Smestaj nije pronadjen!',
          showConfirmButton: false,
          timer: 3500
        });
      } else if (error.status === 422) {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Nije moguce izvrsiti naredbu!',
          showConfirmButton: false,
          timer: 3500
        });
      } else if (error.status === 404) {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Nisu pronadjene rezervacije!',
          showConfirmButton: false,
          timer: 3500
        });
      } else if (error.status === 410) {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Poruka nije poslata!',
          showConfirmButton: false,
          timer: 3500
        });
      }
    }
  }
}
