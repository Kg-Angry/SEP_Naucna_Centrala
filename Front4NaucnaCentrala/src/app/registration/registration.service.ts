import { UserProfileService } from './../user-profile/user-profile.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import {timer} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  ime: String;
  prezime: String;
  grad: String;
  drzava: String;
  titula: String;
  email: String;
  korisnicko_ime: String;
  lozinka: String;


  constructor(private http: HttpClient, private userService: UserProfileService) { }

  Registracija(target, recenzent, uloga) {
    this.ime = target.querySelector('input[name=\'ime\']').value;
    this.prezime = target.querySelector('input[name=\'prezime\']').value;
    this.grad = target.querySelector('input[name=\'grad\']').value;
    this.drzava = target.querySelector('input[name=\'drzava\']').value;
    this.titula = target.querySelector('input[name=\'titula\']').value;
    this.email = target.querySelector('input[name=\'email\']').value;
    this.korisnicko_ime = target.querySelector('input[name=\'korisnicko_ime\']').value;
    this.lozinka = target.querySelector('input[name=\'lozinka\']').value;

   return this.http.post('api/korisnik/registracijaKorisnika', {ime: this.ime, prezime: this.prezime, grad: this.grad,
    drzava: this.drzava, titula: this.titula, email: this.email, korisnicko_ime: this.korisnicko_ime,
     lozinka: this.lozinka, tipKorisnika: uloga})
    .subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste se registrovali',
      showConfirmButton: false,
      timer: 2500
    });
      timer(2500).subscribe(t => location.href = '/login'); }
  );
  }

  RegistracijaAdmin(target, recenzent, uloga) {
    this.ime = target.querySelector('input[name=\'ime\']').value;
    this.prezime = target.querySelector('input[name=\'prezime\']').value;
    this.grad = target.querySelector('input[name=\'grad\']').value;
    this.drzava = target.querySelector('input[name=\'drzava\']').value;
    this.titula = target.querySelector('input[name=\'titula\']').value;
    this.email = target.querySelector('input[name=\'email\']').value;
    this.korisnicko_ime = target.querySelector('input[name=\'korisnicko_ime\']').value;
    this.lozinka = target.querySelector('input[name=\'lozinka\']').value;

   return this.http.post('api/korisnik/registracijaKorisnika', {ime: this.ime, prezime: this.prezime, grad: this.grad,
    drzava: this.drzava, titula: this.titula, email: this.email, korisnicko_ime: this.korisnicko_ime,
     lozinka: this.lozinka, tipKorisnika: uloga})
    .subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste se registrovali novog korisnika',
      showConfirmButton: false,
      timer: 2500
    });
      this.userService.getAllUsers();
      timer(2500).subscribe(t => location.href = '/userProfile'); }
  );
  }

}
