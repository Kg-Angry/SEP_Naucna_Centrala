import { Placanje } from './../class/placanje.enum';
import { NaucniRadoviService } from './naucni-radovi.service';
import { NaucniRad } from './../class/naucni-rad';
import { Component, OnInit } from '@angular/core';
import { Korisnik } from '../class/korisnik';
import Swal from 'sweetalert2';
import { TipPlacanja } from '../class/tip-placanja';

@Component({
  selector: 'app-naucni-radovi',
  templateUrl: './naucni-radovi.component.html',
  styleUrls: ['./naucni-radovi.component.css']
})
export class NaucniRadoviComponent implements OnInit {

  radovi: NaucniRad[] = JSON.parse(localStorage.getItem('radovi'));
  korisnik: Korisnik = JSON.parse(localStorage.getItem('korisnik'));
  tipPlacanjaCasopisa: TipPlacanja[] = JSON.parse(localStorage.getItem('tipoviPlacanjaCasopisa'));
  zajednickiTipovi: Placanje[] = JSON.parse(localStorage.getItem('zajednickiTipovi'));
  constructor(private naucniRadService: NaucniRadoviService) { }

  ngOnInit() {
  }

  uKorpu(rad: NaucniRad){
    let dodaoUkorpu = 0;
    let casopisDodat = false;
    for(let k = 0; k < this.tipPlacanjaCasopisa.length; k++) {
      if(this.tipPlacanjaCasopisa[k].naziv === rad.naucni_casopis.naziv) {
              for(let j=0; j < this.zajednickiTipovi.length;j++)
              {
                for(let i = 0; i < this.tipPlacanjaCasopisa[k].tipoviPlacanja.length; i++){
                    if(this.zajednickiTipovi[j] === this.tipPlacanjaCasopisa[k].tipoviPlacanja[i])
                    {

                      for(let r = 0; r < this.korisnik.korpa.naucni_casopis_list.length; r++){
                        if(this.korisnik.korpa.naucni_casopis_list[r].naziv === rad.naucni_casopis.naziv)
                        {
                          casopisDodat = true;
                        }
                      }
                      if(!casopisDodat){
                        dodaoUkorpu = 1;
                        this.korisnik.korpa.naucni_rad_list.push(rad);
                      }else{
                        break;
                      }

                    }
                }
                if(dodaoUkorpu){
                  this.naucniRadService.dodajUKorpuCasopis(this.korisnik.id, this.korisnik.korpa);
                  break;
                }
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
