import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

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
}
