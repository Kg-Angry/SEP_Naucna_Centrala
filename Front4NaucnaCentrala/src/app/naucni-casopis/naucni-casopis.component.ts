import { TipPlacanja } from './../class/tip-placanja';
import { Placanje } from './../class/placanje.enum';
import { NaucniCasopisService } from './naucni-casopis.service';
import { NaucniRad } from './../class/naucni-rad';
import { NaucniCasopis } from './../class/naucni-casopis';
import { Component, OnInit } from '@angular/core';
import {Korisnik} from './../class/korisnik';

@Component({
  selector: 'app-naucni-casopis',
  templateUrl: './naucni-casopis.component.html',
  styleUrls: ['./naucni-casopis.component.css']
})
export class NaucniCasopisComponent implements OnInit {

  casopisi: NaucniCasopis[] = JSON.parse(localStorage.getItem('casopisi'));
  radovi: NaucniRad[] = JSON.parse(localStorage.getItem('radovi'));
  korisnik: Korisnik = JSON.parse(localStorage.getItem('korisnik'));
  tipCasopisa = Placanje;
  CasopisPlati: NaucniCasopis = new NaucniCasopis();
  tipPlacanjaCasopisa: TipPlacanja[] = JSON.parse(localStorage.getItem('tipoviPlacanjaCasopisa'));
  tipPlacanjaJendogCasopisa: TipPlacanja[] = [];

  constructor( private casopisService: NaucniCasopisService) { }

  ngOnInit() {
    for(let i = 0; i < this.tipPlacanjaCasopisa.length; i++) {

      if(this.casopisi[i].id === this.tipPlacanjaCasopisa[i].id) {
            this.casopisi[i].tipoviPlacanja = this.tipPlacanjaCasopisa[i].tipoviPlacanja;
          }
    }
  }

  PlatiCasopis(c: NaucniCasopis) {
    this.CasopisPlati = c;

  }
  IzabraoPlacanje(t) {
    if(t === 'BANKA') {
      this.casopisService.preusmeriBanka();
    } else if(t === 'PAYPAL') {
      this.casopisService.preusmeriPayPal(this.CasopisPlati, this.korisnik);
    } else if(t === 'BITCOIN') {
      this.casopisService.preusmeriBitcoin(this.CasopisPlati, this.korisnik);
    }
  }

}
