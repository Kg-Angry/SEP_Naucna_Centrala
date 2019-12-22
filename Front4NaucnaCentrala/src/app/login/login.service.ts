import { TokenService } from './../token.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  korisnicko_ime: String;
  lozinka: String;

  headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient, private tokenService: TokenService) { }

  Logovanje(target)
  {
    this.korisnicko_ime = target.querySelector('input[name=\'korisnicko_ime\']').value;
    this.lozinka = target.querySelector('input[name=\'lozinka\']').value;

    return this.http.post('api/korisnik/logovanje', {korisnickoIme: this.korisnicko_ime, lozinka: this.lozinka}, {responseType: 'text'})
    .subscribe(data => {
      this.tokenService.setToken(data);
      this.http.get('api/korisnik/ulogovan', {headers: this.headers})
            .subscribe( (data1: any) => {
              console.log(data1);
              localStorage.setItem('korisnik', JSON.stringify(data1));
              Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Uspesno ste se logovali',
                showConfirmButton: false,
                timer: 2500
              });
              timer(2500).subscribe(t => location.href = '/home');
          });
    });
  }
}
