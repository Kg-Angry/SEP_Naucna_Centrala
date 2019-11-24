import { timer } from 'rxjs';
import { HomeService } from './../home/home.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class NaucnaOblastService {

  constructor(private http: HttpClient , private homeService: HomeService) { }

  kreirajOblast(target)  {

  const naziv = target.querySelector('input[name=\'naziv\']').value;
  const opis = target.querySelector('textarea[name=\'opis\']').value;

  return this.http.post('api/naucna_oblast/kreirajOblast', { naziv: naziv, opis: opis})
    .subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste se dodali novu oblast',
      showConfirmButton: false,
      timer: 2500
    });
    this.homeService.getOblasti();
      timer(2500).subscribe(t => location.href = '/userProfile'); });
  }

  izmeniOblast(target)  {

    const naziv = target.querySelector('input[name=\'naziv\']').value;
    const opis = target.querySelector('textarea[name=\'opis\']').value;

      return this.http.put('api/naucna_oblast/izmeniOblast', {naziv: naziv, opis: opis})
      .subscribe(data => {Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Uspesno ste se izmenili oblast',
        showConfirmButton: false,
        timer: 2500
      });
      this.homeService.getOblasti();
        timer(2500).subscribe(t => location.href = '/userProfile'); });
  }

  obrisiOblast(naziv: String)  {
    return this.http.delete('api/naucna_oblast/obrisi/' + naziv)
    .subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste obrisali naucnu oblast',
      showConfirmButton: false,
      timer: 1500
    });
    this.homeService.getOblasti();
      timer(1500).subscribe(t => location.href = '/userProfile');
    });
  }
}
