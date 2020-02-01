import { ListPeriodPretplate } from './../class/list-period-pretplate';
import { Transakcija } from './../class/transakcija';
import { NaucniRad } from './../class/naucni-rad';
import { NaucniCasopis } from './../class/naucni-casopis';
import { HomeService } from './../home/home.service';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';
import { timer, interval } from 'rxjs';
import {Placanje} from '../class/placanje.enum';
import {UnetiTipovi} from '../class/uneti-tipovi';

@Injectable({
  providedIn: 'root'
})
export class NaucniCasopisService {

//na svakih 10s on proverava da li je KP ziv :D
  source = interval(10000);
  poljaZaPopunjavanje: UnetiTipovi[]=[];
  listPeriodPretplate: ListPeriodPretplate = new ListPeriodPretplate();

  constructor(private http: HttpClient, private homeService: HomeService) { }

  kreirajCasopis(naziv ,issn ,cena, tipCasopisa, Urednici, Recenzenti, Glavni_Korisnik, IzabranaNaucnaOblast, IzabaniTipoviPlacanja: Placanje[])
  {
    if(IzabaniTipoviPlacanja.length > 0)
    {
      for(let i = 0; i < IzabaniTipoviPlacanja.length; i++){
        let u = new UnetiTipovi();
        u.tipPlacanja = IzabaniTipoviPlacanja[i].toString();
        u.popunjeno = false;
        this.poljaZaPopunjavanje.push(u);
      }

    }
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
    return this.http.post('api/naucni_casopis/kreirajCasopis', {naziv: naziv, issn: issn, tipCasopisa: tipCasopisa,
       tipoviPlacanja: IzabaniTipoviPlacanja,
        glavni_urednik: Glavni_Korisnik, urednici: Urednici, recenzent: Recenzenti, naucna_oblast: IzabranaNaucnaOblast, cena: cena,
      unosTipova: this.poljaZaPopunjavanje}).
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

  preusmeriBanka(casopis, korisnik, rad:NaucniRad[]) {
    //uzimam sve nazive casopisa koji su u korpi
      let proba = '';
      let nazivRadova: String = '';
      let randomBroj: Number = Math.random()*100;

      for(let i = 0; i < rad.length;i++)
    {
      if(i !== rad.length - 1){
        nazivRadova += rad[i].naslov + ',';
      } else {
        nazivRadova += rad[i].naslov.toString();
      }
    }

      proba = randomBroj.toFixed(0);
    console.log("Cena: " + casopis.cena);
    return this.http.post('api1/kp/bankaPayment', {korisnicko_ime_platioca: korisnik.korisnicko_ime
      , lozinka_platioca: korisnik.lozinka, id_porudzbine: proba, nazivi_casopisa: casopis, naziv_radova: nazivRadova }, {responseType: 'text'})
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

  pretplataPaypal(casopis: NaucniCasopis, korisnik){
    this.periodPretplate().subscribe((data: ListPeriodPretplate) =>
    {
      this.listPeriodPretplate = data;
      let randomBroj: Number = Math.random()*100;
      let proba = randomBroj.toFixed(0);
      let period:Map<string, string> = new Map<string, string>();
      let vreme: Map<string, string> = new Map<string, string>();
      for(let i = 0; i < this.listPeriodPretplate.list_period.length; i++)
      {
        period.set(this.listPeriodPretplate.list_period[i].period.toString(), this.listPeriodPretplate.list_period[i].period.toString());
      }

    Swal.fire({
      title: 'Pretplata ?',
      text: 'Da li biste zeleli da se pretplatite na PayPal?',
      icon: 'warning',
      input: 'select',
      inputOptions: period,
      showCancelButton: true,
      confirmButtonText: 'Da',
      cancelButtonText: 'Ne',
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        for(let i = 0; i < this.listPeriodPretplate.list_period.length; i++)
      {
          if(this.listPeriodPretplate.list_period[i].period === result.value)
          {
            for(let j=0; j < this.listPeriodPretplate.list_period[i].ciklus.length;j++)
            {
              vreme.set(this.listPeriodPretplate.list_period[i].ciklus[j].toString(),this.listPeriodPretplate.list_period[i].ciklus[j].toString());
            }
          }
      }
        Swal.fire({
          title: 'Vremenski period ?',
          input: 'select',
          inputOptions: vreme,
          showCancelButton: true,
          confirmButtonText: 'Potvrdi',
        }).then((result1) => {
          if (result1.value) {
          return this.http.post('api1/kp/pretplata', {cena: casopis.cena, korisnicko_ime_platioca: korisnik.korisnicko_ime
          , lozinka_platioca: korisnik.lozinka, id_porudzbine: proba, naziv_casopisa: casopis.naziv, period: result.value, rate: result1.value}, {responseType: 'text'})
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
      }
  });
    });

}
  preusmeriPayPal(ukupnaCena, casopis: NaucniCasopis[], rad: NaucniRad[], korisnik) {

    //uzimam sve nazive casopisa koji su u korpi
    let naziv: String = '';
    let nazivRadova: String = '';
    let proba = '';
    for(let i = 0; i < casopis.length;i++)
    {
      if(i !== casopis.length - 1){
        naziv += casopis[i].naziv + ',';
      } else {
        naziv += casopis[i].naziv.toString();
      }

    }

    for(let i = 0; i < rad.length;i++)
    {
      if(i !== rad.length - 1){
        nazivRadova += rad[i].naslov + ',';
      } else {
        nazivRadova += rad[i].naslov.toString();
      }

    }

    if(casopis.length === 0){
      naziv = rad[0].naucni_casopis.naziv;
    }

    let randomBroj: Number = Math.random()*100;
      proba = randomBroj.toFixed(0);
    return this.http.post('api1/kp/paypal-api', {cena: ukupnaCena, korisnicko_ime_platioca: korisnik.korisnicko_ime
          , lozinka_platioca: korisnik.lozinka, id_porudzbine: proba, naziv_casopisa: naziv, naziv_radova:nazivRadova}, {responseType: 'text'})
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
  preusmeriBitcoin(ukupnaCena, casopis: NaucniCasopis[], rad:NaucniRad[], korisnik){
    //uzimam sve nazive casopisa koji su u korpi
    let naziv: String = '';
    let nazivRadova: String = '';
    let proba = '';
    for(let i = 0; i < casopis.length; i++)
    {
      if(i !== casopis.length - 1){
        naziv += casopis[i].naziv + ',';
      } else {
        naziv += casopis[i].naziv.toString();
      }
    }

    for(let i = 0; i < rad.length;i++)
    {
      if(i !== rad.length - 1){
        nazivRadova += rad[i].naslov + ',';
      } else {
        nazivRadova += rad[i].naslov.toString();
      }

    }

    if(casopis.length === 0)
    {
      naziv = rad[0].naucni_casopis.naziv;
    }
    let randomBroj: Number = Math.random()*100;
      proba = randomBroj.toFixed(0);

    return this.http.post('api1/kp/bitcoin-api', {cena: ukupnaCena, korisnicko_ime_platioca: korisnik.korisnicko_ime
    , lozinka_platioca: korisnik.lozinka, id_porudzbine: proba, naziv_casopisa: naziv, naziv_radova:nazivRadova}, {responseType: 'text'})
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

  sveTransakcije(korisnicko_ime: String) {
    this.source.subscribe(()=>{
      return this.http.get('api1/kp/sveTransakcije/' + korisnicko_ime)
    .subscribe(data => {localStorage.setItem('transakcije', JSON.stringify(data))});
    })
  }

  periodPretplate(){
    return this.http.get('api1/kp/preiodPretplate');
  }
}
