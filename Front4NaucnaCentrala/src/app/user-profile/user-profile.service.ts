import { NaucniCasopisService } from './../naucni-casopis/naucni-casopis.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserProfileService {

  constructor(private http: HttpClient, private ncServise: NaucniCasopisService) { }

  getAllUsers() {
    return this.http.get('api/korisnik/getKorisnici').subscribe(data => {localStorage.setItem('korisnici', JSON.stringify(data)); });
  }

  izmeniKorisnika(target, uloga)  {
    const korisnicko_ime = target.querySelector('input[name=\'korisnicko_ime\']').value;

    return this.http.put('api/korisnik/izmeniKorisnika', {korisnicko_ime: korisnicko_ime, tipKorisnika: uloga})
    .subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste izmenili ulogu korisnika',
      showConfirmButton: false,
      timer: 1500
    });
      timer(1500).subscribe(t => location.href = '/userProfile'); });
  }

  obrisiKorisnika(korisnicko_ime) {
    return this.http.delete('api/korisnik/brisanjeKorisnika/' + korisnicko_ime)
    .subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste obrisali korisnika',
      showConfirmButton: false,
      timer: 1500
    });
      this.getAllUsers();
      timer(1500).subscribe(t => location.href = '/userProfile');
    });
  }

  izmeniPodatkeKorisniku(target) {

    const ime = target.querySelector('input[name=\'IzmeniIme\']').value;
    const prezime = target.querySelector('input[name=\'IzmeniPrezime\']').value;
    const grad = target.querySelector('input[name=\'IzmeniGrad\']').value;
    const drzava = target.querySelector('input[name=\'IzmeniDrzavu\']').value;
    const titula = target.querySelector('input[name=\'IzmeniTitulu\']').value;
    const email = target.querySelector('input[name=\'IzmeniEmail\']').value;
    const korisnicko_ime = target.querySelector('input[name=\'korisnicko_ime_Izmeni\']').value;

    return this.http.put('api/korisnik/izmeniPodatkeOKorisniku', {ime: ime, prezime: prezime, grad: grad,
      drzava: drzava, titula: titula, email: email, korisnicko_ime: korisnicko_ime})
    .subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste izmenili podatke o korisniku',
      showConfirmButton: false,
      timer: 1500
    });
      localStorage.setItem('korisnik', JSON.stringify(data));
      timer(1500).subscribe(t => location.href = '/userProfile'); });
  }

  promenaPassworda(target,korisnicko_ime)
  {
    const stara_lozinka = target.querySelector('input[name=\'stara_lozinka\']').value;
    const nova_lozinka = target.querySelector('input[name=\'nova_lozinka\']').value;

    console.log(korisnicko_ime + ' ' + stara_lozinka + ' ' + nova_lozinka + ' ');

    return this.http.put('api/korisnik/promenaLozinke', {korisnicko_ime: korisnicko_ime, email: nova_lozinka, lozinka: stara_lozinka})
    .subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste promenili lozinku',
      showConfirmButton: false,
      timer: 1500
    });
      localStorage.removeItem('korisnik');
      timer(1500).subscribe(t => location.href = '/login');
    });
  }

  zahtevZaRecenzenta(korisnickoIme, odobrio, usernameAktivnog){
    return this.http.post('api/korisnik/iRecenzent/'+ odobrio, {korisnicko_ime: korisnickoIme, ime: usernameAktivnog})
    .subscribe(data =>{
      if(odobrio == 1){
        Swal.fire({
          position: 'top-end',
          icon: 'info',
          title: 'Zahtev je odobren',
          showConfirmButton: false,
          timer: 1500
        });
      } else{
        Swal.fire({
          position: 'top-end',
          icon: 'info',
          title: 'Zahtev nije odobren',
          showConfirmButton: false,
          timer: 1500
        });
      }
        this.getAllUsers();
      timer(1500).subscribe(t => location.href = '/userProfile');
    });
  }

  preuzimanjeFormi(tipPlacanja: String,casopis: String){

    return this.http.get('api1/kp/form/'+ tipPlacanja).subscribe(data =>
      {
        localStorage.setItem('forma', JSON.stringify(data));
        localStorage.setItem('naslovCasopisa', JSON.stringify(casopis));
        localStorage.setItem('dugmeTip', JSON.stringify(tipPlacanja));
        location.href = '/genforme';
      })
  }

  dodajNoviServis(target){
    const nazivServisa = target.querySelector('input[name=\'nazivServisa\']').value.toUpperCase();

    return this.http.post('api1/tipPlacanja/noviServis', { naziv: nazivServisa }).subscribe(data =>
      {
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Dodat novi servis',
          showConfirmButton: false,
          timer: 2000
        });
        this.ncServise.getTipoviPlacanja();
        timer(2000).subscribe(t => location.href = '/userProfile');
      });

  }

}
