import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  korisnicko_ime: String;
  lozinka: String;

  constructor(private http: HttpClient) { }

  Logovanje(target)
  {
    this.korisnicko_ime = target.querySelector('input[name=\'korisnicko_ime\']').value;
    this.lozinka = target.querySelector('input[name=\'lozinka\']').value;

    return this.http.post('api/korisnik/logovanje', {korisnicko_ime: this.korisnicko_ime, lozinka: this.lozinka})
    .subscribe(data => { Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste se logovali',
      showConfirmButton: false,
      timer: 2500
    });
      timer(2500).subscribe(t => location.href = '/home');
      localStorage.setItem('korisnik', JSON.stringify(data));
    });
  }
}
