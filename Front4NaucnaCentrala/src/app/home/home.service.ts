import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { interval, timer } from 'rxjs';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  source = interval(10000);
  constructor(private http: HttpClient) { }

  getOblasti() {
    return this.http.get('api/naucna_oblast/sveOblasti').subscribe(data => localStorage.setItem('oblasti', JSON.stringify(data)));
  }
  getNaucniCasopisi()  {
    return this.http.get('api/naucni_casopis/sviCasopisi').subscribe(data => localStorage.setItem('casopisi', JSON.stringify(data)));
  }
  getNaucniRadovi()  {
    return this.http.get('api/naucni_rad/sviRadovi').subscribe(data => localStorage.setItem('radovi', JSON.stringify(data)));
  }

  sveTransakcije() {
    this.source.subscribe(()=>{
      return this.http.get('api/korisnik/provera')
    .subscribe((data: Boolean) => {

      if(!data)
      {
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Problem sa serverom. Molimo Vas sacekajte',
          showConfirmButton: false,
          timer: 2500
        });
      }
    });
    });
  }

  proveraPretplate(korisnickoIme: String)
  {
    this.source.subscribe(()=>{
      return this.http.post('api1/kp/pretplataTransakcija/'+korisnickoIme,{})
    .subscribe(data => {});
  });
  }
}
