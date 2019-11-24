import { Component, OnInit } from '@angular/core';
import { NaucnaOblast } from '../class/naucna-oblast';
import { NaucnaOblastService } from './naucna-oblast.service';

@Component({
  selector: 'app-naucna-oblast',
  templateUrl: './naucna-oblast.component.html',
  styleUrls: ['./naucna-oblast.component.css']
})
export class NaucnaOblastComponent implements OnInit {

  Naucna_oblast: NaucnaOblast[] = JSON.parse(localStorage.getItem('oblasti'));

  constructor() { }

  ngOnInit() {

  }
}
