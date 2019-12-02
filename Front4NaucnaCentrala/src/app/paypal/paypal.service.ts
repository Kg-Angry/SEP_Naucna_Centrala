import Swal from 'sweetalert2';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private http: HttpClient) { }

  slanjePodataka(target, payID, token, payerID) {
    return this.http.post('api3/paypal/completePayment', {paymentId: payID, payerID: payerID})
    .subscribe(data => { Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste platili za PayPal',
      showConfirmButton: false,
      timer: 2500
    });
      timer(2500).subscribe(t => location.href = '/home');
    });
  }
}
