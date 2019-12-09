import { timer } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-neuspesno-placanje',
  templateUrl: './neuspesno-placanje.component.html',
  styleUrls: ['./neuspesno-placanje.component.css']
})
export class NeuspesnoPlacanjeComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    Swal.fire({
      position: 'top-end',
      icon: 'error',
      title: 'Greska prilikom placanja casopisa',
      showConfirmButton: false,
      timer: 4500
    });
      timer(5500).subscribe(t => location.href = '/');
  }

}
