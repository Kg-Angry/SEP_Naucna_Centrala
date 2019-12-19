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


  constructor(private userService: UserProfileService, private noService: NaucnaOblastService, private ncService: NaucniCasopisService
    , private nrService: NaucniRadoviService, private regService: RegistrationService) { }

  ngOnInit() {
    this.korisnik = JSON.parse(localStorage.getItem('korisnik'));
    this.userService.getAllUsers();

    for (let i = 0; i < this.sviKorisnici.length; i++)   {
        if (this.sviKorisnici[i].tipKorisnika === 'UREDNIK') {
              this.urednici.push(this.sviKorisnici[i]);
            }

        if (this.sviKorisnici[i].tipKorisnika === 'RECENZENT') {
              this.recenzenti.push(this.sviKorisnici[i]);
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

    this.regService.RegistracijaAdmin(target, this.recenzenti, 'UREDNIK');
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

  KreirajCasopis($event)
  {
    event.preventDefault();
    const target = event.target;
    console.log('Korisnicko Ime: ' + this.korisnik.korisnicko_ime);
    this.ncService.kreirajCasopis(target, this.tipCasopisa, this.IzabraniUrednici,
       this.IzabraniRecenzenti, this.korisnik, this.IzabranaNaucnaOblast, this.IzabaniTipoviPlacanja);
  }

  IzmeniCasopis(casopis: NaucniCasopis)
  {
    this.casopis_za_izmenu = casopis;
    this.tipCasopisaIzmena = casopis.tipCasopisa;
  }

  IzmenaSelektovanogCasopisa($event)
  {
    event.preventDefault();
    const target = event.target;


    this.ncService.izmeniCasopis(target,this.tipCasopisaIzmena, this.IzabraniUredniciIzmena,
      this.IzabraniRecenzentiIzmena, this.korisnik, this.IzabranaNaucnaOblastIzmena);
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
}
