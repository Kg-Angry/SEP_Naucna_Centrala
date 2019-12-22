import { Component, OnInit } from '@angular/core';
import { NaucniRad } from '../class/naucni-rad';

@Component({
  selector: 'app-naucni-radovi',
  templateUrl: './naucni-radovi.component.html',
  styleUrls: ['./naucni-radovi.component.css']
})
export class NaucniRadoviComponent implements OnInit {

  radovi: NaucniRad[] = JSON.parse(localStorage.getItem('radovi'));

  constructor() { }

  ngOnInit() {
  }

}
