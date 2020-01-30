import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor(private http: HttpClient) { }

  ukloniIzKorpe(korisnickoIme: String, id_casopisa){
    return this.http.post('api/naucni_casopis/izbaciIzKorpe/' + id_casopisa, {korisnicko_ime: korisnickoIme})
        .subscribe(data =>
          { Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Uspesno ste uklonili artikal iz korpe!',
            showConfirmButton: false,
            timer: 1500
          });
            this.http.get('api/korisnik/ulogovan')
            .subscribe( (data1: any) => {
              console.log(data1);
              localStorage.setItem('korisnik', JSON.stringify(data1));
          });
          timer(2000).subscribe(t => location.href = '/shopping-cart');
          });
  }

  ukloniIzKorpeRad(korisnickoIme: String, id_rada) {
    return this.http.post('api/naucni_rad/izbaciIzKorpe/' + id_rada, {korisnicko_ime: korisnickoIme})
    .subscribe(data =>{ Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Uspesno ste uklonili artikal iz korpe!',
        showConfirmButton: false,
        timer: 1500
      });
        this.http.get('api/korisnik/ulogovan')
        .subscribe( (data1: any) => {
          console.log(data1);
          localStorage.setItem('korisnik', JSON.stringify(data1));
      });
      timer(2000).subscribe(t => location.href = '/shopping-cart');
      });
  }

  //kada se doda casopis da se uklone svi njegovi radovi iz korpe
  ukloniIzKorpeRad2(korisnickoIme: String, id_rada) {
    return this.http.post('api/naucni_rad/izbaciIzKorpe/' + id_rada, {korisnicko_ime: korisnickoIme})
    .subscribe(data =>{
        this.http.get('api/korisnik/ulogovan')
        .subscribe( (data1: any) => {
          console.log(data1);
          localStorage.setItem('korisnik', JSON.stringify(data1));
      });
      timer(2000).subscribe(t => location.href = '/shopping-cart');
      });
  }
}
