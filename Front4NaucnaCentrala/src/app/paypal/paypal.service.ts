import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private http: HttpClient) { }

  slanjePodataka(target)
  {
    const client_secret = target.querySelector('input[name=\'client_secret\']').value;
    const client_id = target.querySelector('input[name=\'client_id\']').value;

    console.log('Client secret ' + client_secret + 'client_id ' + client_id);
    //return this.http.post('',{}).subscribe();
  }
}
