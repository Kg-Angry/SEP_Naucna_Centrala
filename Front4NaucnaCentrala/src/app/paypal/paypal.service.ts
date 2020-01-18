import { UspesnoPlacanjeService } from './../uspesno-placanje/uspesno-placanje.service';
import { Korisnik } from './../class/korisnik';
import Swal from 'sweetalert2';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { timer } from 'rxjs';
import { Korpa } from '../class/korpa';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  korisnik: Korisnik = JSON.parse(localStorage.getItem('korisnik'));
  constructor(private http: HttpClient, private uspesnoService: UspesnoPlacanjeService) { }

  izmenaPodataka()
  {
    return this.http.post('api1/kp/izmenjenStatusTransakcije', {}).subscribe(data => {});
  }

  slanjePodataka(order_id,target, payID, token, payerID) {

    return this.http.post('api1/kp/paypal-api', {paymentId: payID, payerID: payerID, orderId: order_id},{responseType:'text'})
    .subscribe((data: String) => {
      console.log('Vratio je ' + data);
      if(data === 'uspesno'){
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Uspesno ste platili za PayPal',
          showConfirmButton: false,
          timer: 2500
        });
          this.uspesnoService.izmenaStatusa(order_id);
          this.korisnik.korpa.naucni_casopis_list = [];
          this.korisnik.korpa.naucni_rad_list = [];
          localStorage.setItem('korisnik', JSON.stringify(this.korisnik));
          timer(2500).subscribe(t => location.href = '/home');

      } else{
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Greska prilikom placanja PayPal-a',
          showConfirmButton: false,
          timer: 2500
        });
          timer(2500).subscribe(t => location.href = '/home');
      }
    });
  }

  slanjePodatakaZaPretplatu(token) {
    return this.http.post('api1/kp/pretplata', {token: token}, {responseType: 'text'})
        .subscribe((data: String) => { Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Uspesno ste platili za PayPal',
          showConfirmButton: false,
          timer: 2500
        });
        this.korisnik.korpa.naucni_casopis_list = [];
        this.korisnik.korpa.naucni_rad_list = [];
        localStorage.setItem('korisnik', JSON.stringify(this.korisnik));
          timer(2500).subscribe(t => location.href = '/home');
        });
  }
}
