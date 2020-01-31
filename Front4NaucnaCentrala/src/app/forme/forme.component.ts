import { FormeService } from './forme.service';
import { FormaDTO } from './../class/forma-dto';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-forme',
  templateUrl: './forme.component.html',
  styleUrls: ['./forme.component.css']
})
export class FormeComponent implements OnInit {

  forma: FormaDTO = JSON.parse(localStorage.getItem('forma'));
  naslovCasopisa: String = JSON.parse(localStorage.getItem('naslovCasopisa'));
  tipPlacanja: String = JSON.parse(localStorage.getItem('dugmeTip'));

  constructor(private formService: FormeService) { }

  ngOnInit() {
  }

  PopunjeniPodaci($event){
    event.preventDefault()
    const target = event.target;
    this.formService.posaljiPodatke(target, this.forma, this.naslovCasopisa, this.tipPlacanja);
    localStorage.removeItem('forma');
    localStorage.removeItem('naslovCasopisa');
  }

}
