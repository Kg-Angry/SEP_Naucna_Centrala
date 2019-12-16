import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NeuspesnoPlacanjeService {

  constructor(private http: HttpClient) { }

  izmenaStatusa(orderId)
  {
    return this.http.post('api1/kp/izmenjenStatusTransakcije', {orderId: orderId, status: 'neuspesno'}).subscribe();
  }
}
