import { TokenService } from './../token.service';
import { Placanje } from './../class/placanje.enum';
import { TipPlacanja } from 'src/app/class/tip-placanja';
import { RegistrationService } from './../registration/registration.service';
import { NaucniRadoviService } from './../naucni-radovi/naucni-radovi.service';
import { NaucniCasopisService } from './../naucni-casopis/naucni-casopis.service';
import { NaucniRad } from './../class/naucni-rad';
import { HomeService } from './../home/home.service';
import { timer } from 'rxjs';
import { UserProfileService } from './user-profile.service';
import { Component, OnInit } from '@angular/core';
import {Korisnik} from 'src/app/class/korisnik';
import Swal from 'sweetalert2';
import { NaucnaOblastService } from '../naucna-oblast/naucna-oblast.service';
import { NaucnaOblast } from '../class/naucna-oblast';
import { NaucniCasopis } from '../class/naucni-casopis';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  uloga: String;
  nazivCasopisa: String = '';
  issn: Number = 0;
  cena: Number = 0;
  zahtevRecenzent: Korisnik[]=[];
  korisnik: Korisnik = JSON.parse(localStorage.getItem('korisnik'));
  sviKorisnici: Korisnik[] = JSON.parse(localStorage.getItem('korisnici'));
  casopisi: NaucniCasopis[] = JSON.parse(localStorage.getItem('casopisi'));
  oblasti: NaucnaOblast [] = JSON.parse(localStorage.getItem('oblasti'));
  preostaleOblasti: NaucnaOblast [] = [];
  radovi: NaucniRad[] = JSON.parse(localStorage.getItem('radovi'));
  oblast: NaucnaOblast = new NaucnaOblast();
  tipCasopisa: String;
  tipCasopisaIzmena: String;
  IzabraniUrednici: Korisnik[] = [];
  IzabraniRecenzenti: Korisnik[] = [];
  IzabraniUredniciIzmena: Korisnik[] = [];
  IzabraniRecenzentiIzmena: Korisnik[] = [];
  urednici1: Korisnik[] = [];
  recenzenti1: Korisnik[] = [];
  urednici: Korisnik[] = [];
  preostaliUrednici: Korisnik[] = [];
  recenzenti: Korisnik[] = [];
  preostaliRecenzenti: Korisnik[] = [];
  kor: Korisnik = new Korisnik();
  tipKorisnika: String;
  recenzent = false;
  IzabranaNaucnaOblast: NaucnaOblast[] = [];
  IzabranaNaucnaOblastIzmena: NaucnaOblast[] = [];
  koAutori: Korisnik[] = [];
  IzmenjeniKoAutori: Korisnik[] = [];
  IzabranaNaucnaOblastRada: NaucnaOblast = new NaucnaOblast();
  IzmenjenaNaucnaOblastRada: NaucnaOblast = new NaucnaOblast();
  casopis_za_izmenu: NaucniCasopis = new NaucniCasopis();
  rad_za_izmenu: NaucniRad = new NaucniRad();
  selectUploadFile: File = null;
  IzabraniNaucniCasopis: NaucniCasopis = new NaucniCasopis();
  IzabaniTipoviPlacanja: Placanje[] = [];
  TipoviPlacanja: TipPlacanja[] = JSON.parse(localStorage.getItem('tipoviPlacanja'));
  imeUrednika: String;
  prezimeUrednika: String;
  gradUrednika: String;
  drzavaUrednika: String;
  titulaUrednika: String;
  emailUrednika: String;
  korisnickoImeUrednika: String;
  lozinkaUrednika: String;

  constructor(private userService: UserProfileService, private noService: NaucnaOblastService, private ncService: NaucniCasopisService
    , private nrService: NaucniRadoviService, private regService: RegistrationService, private token: TokenService) { }

  ngOnInit() {
    this.userService.getAllUsers();
    for (let i = 0; i < this.sviKorisnici.length; i++) {
      if(this.sviKorisnici[i].recenzent === true && this.sviKorisnici[i].tipKorisnika === 'OBICAN') {
           this.zahtevRecenzent.push(this.sviKorisnici[i]);
        }
    }
  }

  IzmenaPodataka($event) {
    event.preventDefault();
    const target = event.target;

    this.userService.izmeniPodatkeKorisniku(target);
  }

  SignOut()  {
    localStorage.removeItem('korisnik');
    this.token.removeToken();
    Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste se izlogovali',
      showConfirmButton: false,
      timer: 1500
    });
    timer(1500).subscribe(t => location.href = '/home');
  }

  IzmeniKorisnika(korisnik)  {
    this.kor = korisnik;
    this.tipKorisnika = this.kor.tipKorisnika;

  }

  IzmenaUlogeKorisnika($event) {
    event.preventDefault();
    const target = event.target;

    this.userService.izmeniKorisnika(target, this.tipKorisnika);
  }

  Obrisi(korisnicko_ime: String)  {
    this.userService.obrisiKorisnika(korisnicko_ime);
  }

  KreirajOblast($event) {
    event.preventDefault();
    const target = event.target;

    this.noService.kreirajOblast(target);
  }

  IzmeniOblast(o)  {
    //uzima oblast za koju se vrsi izmena
    this.oblast = o;
  }

  ObrisiOblast(naziv) {
    this.noService.obrisiOblast(naziv);
  }

  IzmenaPodatakaOblasti($event)
  {
    event.preventDefault();
    const target = event.target;

    this.noService.izmeniOblast(target);
  }

  DodavanjeKorisnikaAdmin($event)
  {
    event.preventDefault();
    const target = event.target;

    if(this.IzabranaNaucnaOblast.length === 0)
    {
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: 'Mora se izabrati bar jedna naucna oblast',
        showConfirmButton: false,
        timer: 1500
      });
    } else{
      this.regService.RegistracijaAdmin(this.imeUrednika, this.prezimeUrednika, this.gradUrednika, this.drzavaUrednika,
        this.titulaUrednika, this.emailUrednika, this.korisnickoImeUrednika, this.lozinkaUrednika, 'UREDNIK', this.IzabranaNaucnaOblast);
    }
  }

  KreirajRad($event)
  {
    event.preventDefault();
    const target = event.target;

    this.nrService.kreirajRad(target, this.koAutori, this.IzabranaNaucnaOblastRada,
       this.selectUploadFile,this.IzabraniNaucniCasopis, this.korisnik);
  }
  SelectFile(event) {
    this.selectUploadFile = event.target.files[0];
  }

  IzmeniRad(rad)
  {
    this.rad_za_izmenu = rad;
  }

  IzmeniPostojeciRad($event)
  {
    event.preventDefault();
    const target = event.target;

    this.nrService.izmeniRad(target, this.IzmenjeniKoAutori, this.IzmenjenaNaucnaOblastRada);
  }

  ObrisiRad(rad)
  {
    this.nrService.obrisiRad(rad);
  }

  KreirajCasopis($event) {

    event.preventDefault();
    const target = event.target;

    //uzimanje svih urednika
    let urednici = this.sviKorisnici;
    let urednici1 = this.sviKorisnici;
    this.urednici1 = urednici.filter(urednik => urednik.tipKorisnika === 'UREDNIK' && urednik.korisnicko_ime !== this.korisnik.korisnicko_ime);

    this.urednici = [];
    for(let i = 0; i < this.urednici1.length; i++){
      for(let k = 0; k < this.IzabranaNaucnaOblast.length; k++)
      {
        let trazi = this.urednici1[i].naucne_oblasti.find(x => x.naziv === this.IzabranaNaucnaOblast[k].naziv);

        if(trazi !== undefined){
          this.urednici.push(this.urednici1[i]);
          break;
        }
      }
    }

    //uzimanje svih recenzenata
    let recenzenti = this.sviKorisnici;
    let recenzenti1 = this.sviKorisnici;
    this.recenzenti1 = recenzenti.filter(rec => rec.tipKorisnika === 'RECENZENT');

    //izabrali i recenzente
    this.recenzenti = [];
    for(let i = 0; i < this.recenzenti1.length; i++){
      for(let k = 0; k < this.IzabranaNaucnaOblast.length; k++)
      {
        console.log("Izabrana naucna oblast: " + this.IzabranaNaucnaOblast[k]);
        let trazi = this.recenzenti1[i].naucne_oblasti.find(x => x.naziv === this.IzabranaNaucnaOblast[k].naziv);

        if(trazi !== undefined){

          this.recenzenti.push(this.recenzenti1[i]);
          break;
        }
      }
    }

  }

  posaljiPodatkeOcasopisu($event){
    let proces = JSON.parse(localStorage.getItem('proces'));
    if(this.IzabraniRecenzenti.length < 2) {
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: 'Broj recenzenata minimalno mora biti 2',
        showConfirmButton: false,
        timer: 2500
      });
    } else {
      this.ncService.kreirajCasopis(this.nazivCasopisa, this.issn, this.cena, this.tipCasopisa, this.IzabraniUrednici,
        this.IzabraniRecenzenti, this.korisnik, this.IzabranaNaucnaOblast, this.IzabaniTipoviPlacanja);
    }

  }

  //na osnovu izabranih naucnih oblasti u izmeni da se nadju adekvatni recenzenti ili urendici
  IzmenjenCasopis($event) {

    event.preventDefault();
    const target = event.target;

    //uzimanje svih urednika
    let urednici = this.sviKorisnici;
    let urednici1 = this.sviKorisnici;
    this.urednici1 = urednici.filter(urednik => urednik.tipKorisnika === 'UREDNIK' && urednik.korisnicko_ime !== this.korisnik.korisnicko_ime);

    this.urednici = [];
    for(let i = 0; i < this.urednici1.length; i++){
      for(let k = 0; k < this.IzabranaNaucnaOblastIzmena.length; k++)
      {
        let trazi = this.urednici1[i].naucne_oblasti.find(x => x.naziv === this.IzabranaNaucnaOblastIzmena[k].naziv);

        if(trazi !== undefined){
          this.urednici.push(this.urednici1[i]);
          break;
        }
      }
    }

    //uzimanje svih recenzenata
    let recenzenti = this.sviKorisnici;
    let recenzenti1 = this.sviKorisnici;
    this.recenzenti1 = recenzenti.filter(rec => rec.tipKorisnika === 'RECENZENT');

    //izabrali i recenzente
    this.recenzenti = [];
    for(let i = 0; i < this.recenzenti1.length; i++){
      for(let k = 0; k < this.IzabranaNaucnaOblastIzmena.length; k++)
      {
        let trazi = this.recenzenti1[i].naucne_oblasti.find(x => x.naziv === this.IzabranaNaucnaOblastIzmena[k].naziv);

        if(trazi !== undefined){
          this.recenzenti.push(this.recenzenti1[i]);
          break;
        }
      }
    }
  }

  //uzimanje casopisa koji je za izmenu
  IzmeniCasopis(casopis: NaucniCasopis)
  {
    this.casopis_za_izmenu = casopis;
    this.nazivCasopisa = this.casopis_za_izmenu.naziv;
    this.issn = this.casopis_za_izmenu.issn;
    this.cena = this.casopis_za_izmenu.cena;
    this.IzabranaNaucnaOblast = this.casopis_za_izmenu.naucna_oblast;
    this.tipCasopisaIzmena = casopis.tipCasopisa;
  }

  IzmenaSelektovanogCasopisa()
  {
      this.ncService.izmeniCasopis(this.casopis_za_izmenu.id, this.nazivCasopisa,
         this.issn, this.cena, this.tipCasopisaIzmena, this.IzabranaNaucnaOblastIzmena);
  }

  ObrisiCasopis(casopis)
  {
    this.ncService.obrisiCasopis(casopis);
  }

  PromenaLozinke($event)
  {
    event.preventDefault()
    const target = event.target;

    this.userService.promenaPassworda(target, this.korisnik.korisnicko_ime);
  }

  AktivirajCasopis(nazivCasopisa: String){
    Swal.fire({
      title: 'Dopuna ?',
      input: 'text',
      showCancelButton: true,
      cancelButtonText: 'Ne',
      confirmButtonText: 'Da',
      showLoaderOnConfirm: true,
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      if (result.value) {
              this.ncService.aktivirajCasopis(nazivCasopisa, 1, result.value);
            } else {
              this.ncService.aktivirajCasopis(nazivCasopisa, 0, result.value);
            }
    });
  }

  ZahtevRecenzenta(korisnickoIme: String, odobrio){
    this.userService.zahtevZaRecenzenta(korisnickoIme, odobrio, this.korisnik.korisnicko_ime);
  }

  PopuniPodatke(tipPlacanja: String, casopisNaziv: String){
    this.userService.preuzimanjeFormi(tipPlacanja, casopisNaziv);
  }
}
