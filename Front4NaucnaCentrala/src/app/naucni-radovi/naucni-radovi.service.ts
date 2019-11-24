import { HomeService } from './../home/home.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { timer } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class NaucniRadoviService {

  constructor(private http: HttpClient, private homeService: HomeService) { }

  kreirajRad(target, koAutori, IzabranaNaucnaOblastRada, selektovaniFajl: File, IzabraniNaucniCasopis) {
    const nazivRada = target.querySelector('input[name=\'nazivRada\']').value;
    const kljucniPojmovi = target.querySelector('input[name=\'kljucni_pojmovi\']').value;
    const apstrakt = target.querySelector('textarea[name=\'apstrakt\']').value;

    return this.http.post('api/naucni_rad/kreiraj', {naslov: nazivRada, koautori: koAutori,
       kljucni_pojmovi: kljucniPojmovi, apstrakt: apstrakt, oblast_pripadanja: IzabranaNaucnaOblastRada, putanja_upload_fajla: ' ',
       naucni_casopis: IzabraniNaucniCasopis}).
    subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste registrovali novi rad',
      showConfirmButton: false,
      timer: 2500
    });
      let fd = new FormData();
      fd.append('file', selektovaniFajl, selektovaniFajl.name);
      return this.http.post('api/naucni_rad/uploadFile/' + nazivRada, fd).
     subscribe(data => {
       this.homeService.getNaucniRadovi();
       timer(2500).subscribe(t => location.href = '/userProfile')});

  });
}

  izmeniRad(target, koAutori, IzabranaNaucnaOblastRada) {
    const naziv = target.querySelector('input[name=\'nazivRadaIzmena\']').value;
    const kljucniPojmovi = target.querySelector('input[name=\'kljucni_pojmoviIzmena\']').value;
    const apstrakt = target.querySelector('textarea[name=\'apstraktIzmena\']').value;

    return this.http.put('api/naucni_rad/izmeniRad', {naslov: naziv, koautori: koAutori, kljucni_pojmovi: kljucniPojmovi
      , apstrakt: apstrakt, oblast_pripadanja: IzabranaNaucnaOblastRada, putanja_upload_fajla: ' '}).
      subscribe(data => {Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Uspesno ste izmenili rad',
        showConfirmButton: false,
        timer: 2500
      });
        this.homeService.getNaucniRadovi();
        timer(2500).subscribe(t => location.href = '/userProfile'); });
  }

  obrisiRad(naslov)
  {
    return this.http.delete('api/naucni_rad/obrisiRad/' + naslov)
        .subscribe(data => {Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: 'Uspesno ste obrisali rad',
          showConfirmButton: false,
          timer: 2500
        });
          this.homeService.getNaucniRadovi();
          timer(2500).subscribe(t => location.href = '/userProfile'); })
  }
}
