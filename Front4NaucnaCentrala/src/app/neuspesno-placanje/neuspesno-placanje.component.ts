import { NeuspesnoPlacanjeService } from './neuspesno-placanje.service';
import { timer } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-neuspesno-placanje',
  templateUrl: './neuspesno-placanje.component.html',
  styleUrls: ['./neuspesno-placanje.component.css']
})
export class NeuspesnoPlacanjeComponent implements OnInit {

  orderId: String;
  constructor(private route: ActivatedRoute, private neuspesnoService: NeuspesnoPlacanjeService) { }

  ngOnInit() {
    this.orderId = this.route.snapshot.paramMap.get('orderId');
    this.neuspesnoService.izmenaStatusa(this.orderId);
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
