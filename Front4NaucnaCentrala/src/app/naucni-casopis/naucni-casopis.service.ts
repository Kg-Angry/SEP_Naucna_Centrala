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

  kreirajCasopis(naziv ,issn ,cena, tipCasopisa, Urednici, Recenzenti, Glavni_Korisnik, IzabranaNaucnaOblast, IzabaniTipoviPlacanja)
  {
    if(cena < 0)
    {
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: 'Cena za casopis ne sme biti negativna',
        showConfirmButton: false,
        timer: 2500
      });
        this.homeService.getNaucniCasopisi();
        timer(2500).subscribe(t => location.href = '/userProfile');
    } else{
    console.log(IzabaniTipoviPlacanja);
    console.log("NAziv" + naziv);
    return this.http.post('api/naucni_casopis/kreirajCasopis', {naziv: naziv, issn: issn, tipCasopisa: tipCasopisa,
       tipoviPlacanja: IzabaniTipoviPlacanja,
        glavni_urednik: Glavni_Korisnik, urednici: Urednici, recenzent: Recenzenti, naucna_oblast: IzabranaNaucnaOblast, cena: cena}).
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
        return this.http.post('api1/tipPlacanja/tip', {naziv: naziv, tipoviPlacanja: IzabaniTipoviPlacanja})
        .subscribe(data1 => {this.getTipoviPlacanjaZaSveCasopise();
        });
    });
  }
}

  izmeniCasopis(naziv, issn,cena, tipCasopisa, Urednici, Recenzenti, Glavni_Korisnik, IzabranaNaucnaOblast) {

    return this.http.put('api/naucni_casopis/izmeniCasopis', {naziv: naziv, issn: issn, cena: cena, tipCasopisa: tipCasopisa,
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
    location.href = '/banka';
  }
  preusmeriPayPal(casopis, korisnik) {
    Swal.fire({
      title: 'Pretplata ?',
      text: 'Da li biste zeleli da se pretplatite na PayPal?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Da',
      cancelButtonText: 'Ne',
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        return this.http.post('api1/kp/pretplata', {cena: casopis.cena, korisnicko_ime_platioca: korisnik.korisnicko_ime
          , lozinka_platioca: korisnik.lozinka, id_porudzbine: casopis.id, naziv_casopisa: casopis.naziv}, {responseType: 'text'})
        .subscribe((data: string) => {
           Swal.fire({
             position: 'top-end',
             icon: 'info',
             title: 'Bicete preusmereni na stranicu za pretplatu na PayPal',
             showConfirmButton: false,
             timer: 2500
         });
          timer(2500).subscribe(t => location.href = data);
        });
      } else if (
        result.dismiss === Swal.DismissReason.cancel
      ) {
        return this.http.post('api1/kp/paypal-api', {cena: casopis.cena, korisnicko_ime_platioca: korisnik.korisnicko_ime
          , lozinka_platioca: korisnik.lozinka, id_porudzbine: casopis.id, naziv_casopisa: casopis.naziv}, {responseType: 'text'})
        .subscribe((data: string) => {
           Swal.fire({
             position: 'top-end',
             icon: 'info',
             title: 'Bicete preusmereni na stranicu za PayPal',
             showConfirmButton: false,
             timer: 2500
         });
          timer(2500).subscribe(t => location.href = data);
        });
      }
    })
  }
  preusmeriBitcoin(casopis, korisnik){
    return this.http.post('api1/kp/bitcoin-api', {cena: casopis.cena, korisnicko_ime_platioca: korisnik.korisnicko_ime
    , lozinka_platioca: korisnik.lozinka, id_porudzbine: casopis.id, naziv_casopisa: casopis.naziv}, {responseType: 'text'})
    .subscribe((data: string) => { Swal.fire({
      position: 'top-end',
      icon: 'info',
      title: 'Bicete preusmereni na stranicu za Bitcoin',
      showConfirmButton: false,
      timer: 2500
    });
      timer(2500).subscribe(t => location.href = data);
    });
  }

  getTipoviPlacanja() {
    return this.http.get('api1/tipPlacanja/sviTipovi').subscribe(data => localStorage.setItem('tipoviPlacanja', JSON.stringify(data)));
  }

  getTipoviPlacanjaZaSveCasopise() {
    return this.http.get('api1/tipPlacanja/tipoviPlacanjaZaSveCasopise').subscribe(
      data2 => localStorage.setItem('tipoviPlacanjaCasopisa', JSON.stringify(data2)));
  }

  aktivirajCasopis(nazivCasopisa: String, dopuniti: number){
    return this.http.post('api/naucni_casopis/aktivirajCasopis/' + dopuniti, {naziv: nazivCasopisa})
    .subscribe(data => {Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste aktivirali casopis',
      showConfirmButton: false,
      timer: 2500
    });
      this.homeService.getNaucniCasopisi();
      timer(2500).subscribe(t => location.href = '/userProfile');
    });
  }
}
