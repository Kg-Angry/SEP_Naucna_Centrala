import { Transakcija } from './../class/transakcija';
import { ShoppingCartService } from './../shopping-cart/shopping-cart.service';
import { TipPlacanja } from './../class/tip-placanja';
import { Placanje } from './../class/placanje.enum';
import { NaucniCasopisService } from './naucni-casopis.service';
import { NaucniRad } from './../class/naucni-rad';
import { NaucniCasopis } from './../class/naucni-casopis';
import { Component, OnInit } from '@angular/core';
import {Korisnik} from './../class/korisnik';
import Swal from 'sweetalert2';


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
  transakcije: Transakcija[] = JSON.parse(localStorage.getItem('transakcije'));

  constructor( private casopisService: NaucniCasopisService, private shopingService: ShoppingCartService) { }

  ngOnInit() {
    this.casopisService.sveTransakcije(this.korisnik.korisnicko_ime);
    for(let i = 0; i < this.tipPlacanjaCasopisa.length; i++) {

      if(this.casopisi[i].id === this.tipPlacanjaCasopisa[i].id) {
            this.casopisi[i].tipoviPlacanja = this.tipPlacanjaCasopisa[i].tipoviPlacanja;
          }
    }
  }

  pretplatiSe(c: NaucniCasopis) {
    this.CasopisPlati = c;
    let pretplata = false;
    for(let k = 0; k < this.tipPlacanjaCasopisa.length; k++) {
      if(this.tipPlacanjaCasopisa[k].naziv === c.naziv) {
        for(let i = 0; i < this.tipPlacanjaCasopisa[k].tipoviPlacanja.length; i++)
        {
          if(this.tipPlacanjaCasopisa[k].tipoviPlacanja[i].toString() === 'PAYPAL'){
            pretplata = true;
            this.casopisService.pretplataPaypal(c, this.korisnik);
            break;
          }
        }
        if(!pretplata){
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: 'Nije moguce pretplatiti se na odabrani casopis',
            showConfirmButton: false,
            timer: 2500
          });
        }
      }
    }
  }

  uKorpu(casopis: NaucniCasopis){
    let dodaoUkorpu = 0;
    for(let k = 0; k < this.tipPlacanjaCasopisa.length; k++) {
      if(this.tipPlacanjaCasopisa[k].naziv === casopis.naziv) {
              if(this.zajednickiTipovi)
              {
                for(let j=0; j < this.zajednickiTipovi.length;j++)
              {
                for(let i = 0; i < this.tipPlacanjaCasopisa[k].tipoviPlacanja.length; i++){
                    if(this.zajednickiTipovi[j] === this.tipPlacanjaCasopisa[k].tipoviPlacanja[i])
                    {

                      dodaoUkorpu = 1;
                      for(let r = 0; r < this.korisnik.korpa.naucni_rad_list.length; r++)
                      {
                        if(this.korisnik.korpa.naucni_rad_list[r].naucni_casopis.id === casopis.id)
                        {
                            this.shopingService.ukloniIzKorpeRad2(this.korisnik.korisnicko_ime, this.korisnik.korpa.naucni_rad_list[r].id);
                        }
                      }
                      this.korisnik.korpa.naucni_casopis_list.push(casopis);
                    }
                }
                if(dodaoUkorpu){
                   this.casopisService.dodajUKorpuCasopis(this.korisnik.id, this.korisnik.korpa);
                  break;
                }
              }
              }else
              {
                dodaoUkorpu = 1;
                 this.korisnik.korpa.naucni_casopis_list.push(casopis);
                this.casopisService.dodajUKorpuCasopis(this.korisnik.id, this.korisnik.korpa);
              }
        }
    }
    if(dodaoUkorpu === 0){
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
