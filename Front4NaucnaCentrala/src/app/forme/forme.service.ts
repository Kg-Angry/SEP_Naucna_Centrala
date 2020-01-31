import { FormaSubmition } from './../class/forma-submition';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormaDTO } from '../class/forma-dto';
import { FormaSubmit } from '../class/forma-submit';
import Swal from 'sweetalert2';
import { timer } from 'rxjs';
import { HomeService } from '../home/home.service';

@Injectable({
  providedIn: 'root'
})
export class FormeService {

  forma = new FormaSubmit();

  constructor(private http: HttpClient,private homeService: HomeService) { }

  posaljiPodatke(target, form: FormaDTO,casopisUsername: String, tipPlacanja: String){

    this.forma.casopisUsername = casopisUsername;
    this.forma.naziv = form.naziv;
    for(let i=0; i < form.fields.length; i++){
      let vrednost = target.querySelector('input[name=\''+ form.fields[i].fieldLabel +'\']').value;
      let formaSubmition = new FormaSubmition();
      formaSubmition.fieldId = form.fields[i].fieldLabel;
      formaSubmition.fieldValue = vrednost;
      this.forma.fields.push(formaSubmition);
    }

    return this.http.post('api1/kp/form/submit', this.forma)
    .subscribe(data => {
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Uspesno ste se obavili popunjavanje forme',
        showConfirmButton: false,
        timer: 2500
      });
      this.promenaStatusaPopunjavanja(tipPlacanja, casopisUsername);
      timer(2500).subscribe(t => location.href = '/userProfile');
    })
    //pogotiti NC da bi se dugme uklonilo
  }

  promenaStatusaPopunjavanja(tipPlacanja: String, naucniCasopis: String){

    return this.http.put('api/naucni_casopis/popunjenaForma/'+ tipPlacanja, {'naziv': naucniCasopis})
      .subscribe(data => {this.homeService.getNaucniCasopisi();});
  }
}
