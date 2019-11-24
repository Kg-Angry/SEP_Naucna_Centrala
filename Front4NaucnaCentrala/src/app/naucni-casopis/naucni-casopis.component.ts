import { NaucniCasopisService } from './naucni-casopis.service';
import { TipPlacanja } from './../class/tip-placanja.enum';
import { NaucniRad } from './../class/naucni-rad';
import { NaucniCasopis } from './../class/naucni-casopis';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-naucni-casopis',
  templateUrl: './naucni-casopis.component.html',
  styleUrls: ['./naucni-casopis.component.css']
})
export class NaucniCasopisComponent implements OnInit {

  casopisi: NaucniCasopis[] = JSON.parse(localStorage.getItem('casopisi'));
  radovi: NaucniRad[] = JSON.parse(localStorage.getItem('radovi'));
  CasopisPlati: NaucniCasopis = new NaucniCasopis();

  constructor( private casopisService: NaucniCasopisService) { }

  ngOnInit() {
  }

  PlatiCasopis(c)
  {
    this.CasopisPlati = c;
  }
  IzabraoPlacanje(t)
  {
    if(t === 'BANKA')
    {
      this.casopisService.preusmeriBanka();
    } else if(t === 'PAYPAL')
    {
      this.casopisService.preusmeriPayPal();
    } else if(t === 'BITCOIN')
    {
      this.casopisService.preusmeriBitcoin();
    }
  }

}
