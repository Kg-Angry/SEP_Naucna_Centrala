import { NaucniCasopisService } from './../naucni-casopis/naucni-casopis.service';
import { Placanje } from './../class/placanje.enum';
import { TipPlacanja } from './../class/tip-placanja';
import { ShoppingCartService } from './shopping-cart.service';
import { Korisnik } from './../class/korisnik';
import { NaucniCasopis } from './../class/naucni-casopis';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

  ukupnaCena = 0;
  casopisi: NaucniCasopis[] = [];
  korisnik: Korisnik = JSON.parse(localStorage.getItem('korisnik'));
  izabraniTipoviPlacanja: TipPlacanja[] = [];
  zajednickiTipPlacanja: Placanje[] = [];
  sviTipoviPlacanja: TipPlacanja[] = JSON.parse(localStorage.getItem('tipoviPlacanja'));
  tipPlacanjaCasopisa: TipPlacanja[] = JSON.parse(localStorage.getItem('tipoviPlacanjaCasopisa'));
  moguciTipoviPlacanja: TipPlacanja[] = [];

  constructor(private shoppingService: ShoppingCartService, private casopisService: NaucniCasopisService) { }

  ngOnInit() {
    for(let i = 0; i < this.korisnik.korpa.naucni_casopis_list.length; i++){
      this.ukupnaCena += this.korisnik.korpa.naucni_casopis_list[i].cena;
    }

    //uzeo sam samo casopis i tip placanja za izabrani casopis u korpi
    for(let k = 0; k < this.korisnik.korpa.naucni_casopis_list.length; k++){
        for(let j=0; j < this.tipPlacanjaCasopisa.length; j++)
        {
          if(this.korisnik.korpa.naucni_casopis_list[k].id === this.tipPlacanjaCasopisa[j].id)
          {
            this.izabraniTipoviPlacanja.push(this.tipPlacanjaCasopisa[j]);
          }
        }
    }
    let brojac = 0; //sluzi da izbroji da li svi casopisi imaju podrzani tip placanja
    //uzeo sve tipove placanja za sve casopise
    for(let k = 0; k < this.sviTipoviPlacanja.length; k++) {
      for(let j = 0; j < this.izabraniTipoviPlacanja.length; j++) {
        for(let i = 0; i < this.izabraniTipoviPlacanja[j].tipoviPlacanja.length; i++) {
          if(this.sviTipoviPlacanja[k].naziv === this.izabraniTipoviPlacanja[j].tipoviPlacanja[i].toString()) {
            ++brojac;
            if(brojac === this.korisnik.korpa.naucni_casopis_list.length) {
              this.zajednickiTipPlacanja.push(this.izabraniTipoviPlacanja[j].tipoviPlacanja[i]);
              localStorage.setItem('setovao', JSON.stringify(this.zajednickiTipPlacanja));
              break;
            }
          }
        }
      }
      brojac = 0;
    }
  }

  ukloniNaucniCasopis(naucniCasopis: NaucniCasopis){
    this.ukupnaCena -= naucniCasopis.cena;
    this.shoppingService.ukloniIzKorpe(this.korisnik.korisnicko_ime, naucniCasopis.id);
  }

  IzabraoPlacanje(tipPlacanja)
  {

    if(tipPlacanja === 'BANKA') {
      this.casopisService.preusmeriBanka(this.ukupnaCena, this.korisnik.korpa.naucni_casopis_list, this.korisnik);
    } else if (tipPlacanja === 'PAYPAL') {
      this.casopisService.preusmeriPayPal(this.ukupnaCena, this.korisnik.korpa.naucni_casopis_list, this.korisnik);
    } else if (tipPlacanja === 'BITCOIN') {
      this.casopisService.preusmeriBitcoin(this.ukupnaCena, this.korisnik.korpa.naucni_casopis_list, this.korisnik);
    }
  }

}
