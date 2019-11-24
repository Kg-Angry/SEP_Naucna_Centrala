import { HomeService } from './../home/home.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NaucniCasopisService {

  constructor(private http: HttpClient, private homeService: HomeService) { }

  kreirajCasopis(target, tipCasopisa, Urednici, Recenzenti, Glavni_Korisnik, IzabranaNaucnaOblast, IzabaniTipoviPlacanja)
  {
    const naziv = target.querySelector('input[name=\'naziv\']').value;
    const issn = target.querySelector('input[name=\'issn\']').value;
    console.log(IzabaniTipoviPlacanja);
    return this.http.post('api/naucni_casopis/kreirajCasopis', {naziv: naziv, issn: issn, tipCasopisa: tipCasopisa,
       tipoviPlacanja: IzabaniTipoviPlacanja,
        glavni_urednik: Glavni_Korisnik, urednici: Urednici, recenzent: Recenzenti, naucna_oblast: IzabranaNaucnaOblast,}).
    subscribe(data => {
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Uspesno ste se dodali novi casopis',
        showConfirmButton: false,
        timer: 2500
      });
        this.homeService.getNaucniCasopisi();
        timer(2500).subscribe(t => location.href = '/userProfile');
    });
  }

  izmeniCasopis(target, tipCasopisa, Urednici, Recenzenti, Glavni_Korisnik, IzabranaNaucnaOblast) {

    const naziv = target.querySelector('input[name=\'IzmeniNaziv\']').value;
    const issn = target.querySelector('input[name=\'IzmeniISSN\']').value;

    return this.http.put('api/naucni_casopis/izmeniCasopis', {naziv: naziv, issn: issn, tipCasopisa: tipCasopisa,
      urednici: Urednici, recenzent: Recenzenti, naucna_oblast: IzabranaNaucnaOblast})
      .subscribe(data => {Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Uspesno ste izmenili postojeci casopis',
        showConfirmButton: false,
        timer: 2500
      });
        this.homeService.getNaucniCasopisi();
        timer(2500).subscribe(t => location.href = '/userProfile');
      });
  }


  obrisiCasopis(casopis) {
    return this.http.delete('api/naucni_casopis/obrisiCasopis/' + casopis)
      .subscribe(data => {Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Uspesno ste obrisali casopis',
        showConfirmButton: false,
        timer: 2500
      });
        this.homeService.getNaucniCasopisi();
        timer(2500).subscribe(t => location.href = '/userProfile');
      })
  }

  preusmeriBanka()
  {
    location.href = "/banka";
  }
  preusmeriPayPal()
  {
    location.href = "/paypal";
  }
  preusmeriBitcoin()
  {
    return this.http.get('api1/bitcoin/startPayment', {responseType: 'text'})
    .subscribe((data: string) => {location.href = data});
  }
}
