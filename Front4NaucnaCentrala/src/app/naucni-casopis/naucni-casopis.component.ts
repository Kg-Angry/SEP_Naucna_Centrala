import { TipPlacanja } from './../class/tip-placanja';
import { Placanje } from './../class/placanje.enum';
import { NaucniCasopisService } from './naucni-casopis.service';
import { NaucniRad } from './../class/naucni-rad';
import { NaucniCasopis } from './../class/naucni-casopis';
import { Component, OnInit } from '@angular/core';
import {Korisnik} from './../class/korisnik';
import Swal from 'sweetalert2';
import { ThrowStmt } from '@angular/compiler';

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
  zajednickiTipovi: Placanje[] = JSON.parse(localStorage.getItem('zajednickiTipovi'));
  korpa: NaucniCasopis[]=[];

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
      // this.casopisService.preusmeriBanka(this.CasopisPlati, this.korisnik);
    } else if (t === 'PAYPAL') {
      // this.casopisService.preusmeriPayPal(this.CasopisPlati, this.korisnik);
    } else if (t === 'BITCOIN') {
      // this.casopisService.preusmeriBitcoin(this.CasopisPlati, this.korisnik);
    }
  }

  uKorpu(casopis: NaucniCasopis){
    let dodaoUkorpu = -1;
    for(let k = 0; k < this.tipPlacanjaCasopisa.length; k++) {
      if(this.tipPlacanjaCasopisa[k].naziv === casopis.naziv) {
              for(let j=0; j < this.zajednickiTipovi.length;j++)
              {
                for(let i = 0; i < this.tipPlacanjaCasopisa[k].tipoviPlacanja.length; i++){
                    if(this.zajednickiTipovi[j] === this.tipPlacanjaCasopisa[k].tipoviPlacanja[i])
                    {
                      dodaoUkorpu = 1;
                      this.korisnik.korpa.naucni_casopis_list.push(casopis);
                    }
                }
                if(dodaoUkorpu){
                   this.casopisService.dodajUKorpuCasopis(this.korisnik.id, this.korisnik.korpa);
                  break;
                }
              }
        }
    }
    if(dodaoUkorpu === -1){
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: 'Nije moguce dodati casopis u korpu',
        showConfirmButton: false,
        timer: 2500
      });
    }
  }

}
