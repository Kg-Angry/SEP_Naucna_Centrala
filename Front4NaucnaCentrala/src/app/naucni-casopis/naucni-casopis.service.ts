import { NaucniRad } from './../class/naucni-rad';
import { NaucniCasopis } from './../class/naucni-casopis';
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

  izmeniCasopis(id,naziv, issn,cena, tipCasopisa, IzabranaNaucnaOblast) {

    return this.http.put('api/naucni_casopis/izmeniCasopis', {id: id, naziv: naziv, issn: issn, cena: cena, tipCasopisa: tipCasopisa,
      naucna_oblast: IzabranaNaucnaOblast})
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

  preusmeriBanka(casopis, korisnik) {
    //uzimam sve nazive casopisa koji su u korpi
      let proba = '';
      let randomBroj: Number = Math.random()*100;
      proba = randomBroj.toFixed(0);

    return this.http.post('api1/kp/bankaPayment', {korisnicko_ime_platioca: korisnik.korisnicko_ime
      , lozinka_platioca: korisnik.lozinka, id_porudzbine: proba, nazivi_casopisa: casopis}, {responseType: 'text'})
      .subscribe((data: string) => { Swal.fire({
        position: 'top-end',
        icon: 'info',
        title: 'Bicete preusmereni na stranicu za Banku',
        showConfirmButton: false,
        timer: 2500
      });
        timer(2500).subscribe(t => location.href = data);
      });
    }
  preusmeriPayPal(ukupnaCena, casopis: NaucniCasopis[], korisnik) {

    //uzimam sve nazive casopisa koji su u korpi
    let naziv: String = '';
    let proba = '';
    for(let i = 0; i < casopis.length;i++)
    {
      if(i !== casopis.length - 1){
        naziv += casopis[i].naziv + ',';
      } else {
        naziv += casopis[i].naziv.toString();
      }
      let randomBroj: Number = Math.random()*100;
      proba = randomBroj.toFixed(0);
    }
    Swal.fire({
      title: 'Pretplata ?',
      text: 'Da li biste zeleli da se pretplatite na PayPal?',
      icon: 'warning',
      input: 'select',
      inputOptions: {
        'WEEK': 'WEEK',
        'MONTH': 'MONTH',
        'YEAR': 'YEAR'
      },
      showCancelButton: true,
      confirmButtonText: 'Da',
      cancelButtonText: 'Ne',
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        Swal.fire({
          title: 'Vremenski period ?',
          input: 'select',
          inputOptions: {
            '1': '1',
            '2': '2',
            '3': '3',
            '5': '5',
            '7': '7'
          },
          showCancelButton: true,
          confirmButtonText: 'Potvrdi',
        }).then((result1) => {
          if (result1.value) {
          return this.http.post('api1/kp/pretplata', {cena: ukupnaCena, korisnicko_ime_platioca: korisnik.korisnicko_ime
          , lozinka_platioca: korisnik.lozinka, id_porudzbine: proba, naziv_casopisa: naziv, period: result.value, rate: result1.value}, {responseType: 'text'})
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
          }
        })
      } else if (
        result.dismiss === Swal.DismissReason.cancel
      ) {
        return this.http.post('api1/kp/paypal-api', {cena: ukupnaCena, korisnicko_ime_platioca: korisnik.korisnicko_ime
          , lozinka_platioca: korisnik.lozinka, id_porudzbine: proba, naziv_casopisa: naziv}, {responseType: 'text'})
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
  preusmeriBitcoin(ukupnaCena, casopis: NaucniCasopis[], korisnik){
    //uzimam sve nazive casopisa koji su u korpi
    let naziv: String = '';
    let proba = '';
    for(let i = 0; i < casopis.length; i++)
    {
      if(i !== casopis.length - 1){
        naziv += casopis[i].naziv + ',';
      } else {
        naziv += casopis[i].naziv.toString();
      }
      let randomBroj: Number = Math.random()*100;
      proba = randomBroj.toFixed(0);
    }
    return this.http.post('api1/kp/bitcoin-api', {cena: ukupnaCena, korisnicko_ime_platioca: korisnik.korisnicko_ime
    , lozinka_platioca: korisnik.lozinka, id_porudzbine: proba, naziv_casopisa: naziv}, {responseType: 'text'})
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

  aktivirajCasopis(nazivCasopisa: String, dopuniti: number, text){
    return this.http.post('api/naucni_casopis/aktivirajCasopis/' + dopuniti+'/'+text, {naziv: nazivCasopisa})
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

  dodajUKorpuCasopis(korisnik, korpa){
    return this.http.post('api/naucni_casopis/dodajUKorpu', {id: korisnik, korpa: korpa})
           .subscribe(data => {
              Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Uspesno ste dodali casopis u korpu',
                showConfirmButton: false,
                timer: 2000
              });
              this.http.get('api/korisnik/ulogovan')
            .subscribe( (data1: any) => {
              console.log(data1);
              localStorage.setItem('korisnik', JSON.stringify(data1));
          });
        });
  }
}
