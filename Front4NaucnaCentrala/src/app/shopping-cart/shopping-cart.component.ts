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

  constructor(private shoppingService: ShoppingCartService) { }

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
    let zajednici = false;
    for(let k = 0; k < this.sviTipoviPlacanja.length; k++)
    {
      for(let j = 0; j < this.izabraniTipoviPlacanja.length; j++)
      {
        for(let i = 0; i < this.izabraniTipoviPlacanja[j].tipoviPlacanja.length; i++)
        {
          if(this.sviTipoviPlacanja[k].naziv === this.izabraniTipoviPlacanja[j].tipoviPlacanja[i].toString())
          {
            //BITCOIN === [BITCOIN, PAYPAL]
            //ovde sad videti kako da se resi problem zajedncki tipPlacanja
          }
        }

      }
    }

  }

  ukloniNaucniCasopis(naucniCasopis: NaucniCasopis){
    this.ukupnaCena -= naucniCasopis.cena;
    this.shoppingService.ukloniIzKorpe(this.korisnik.korisnicko_ime, naucniCasopis.id);
  }

  IzabraoPlacanje(tipPlacanja)
  {

  }

}
