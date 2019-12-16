import { UspesnoPlacanjeService } from './uspesno-placanje.service';
import { ActivatedRoute } from "@angular/router";
import { timer } from 'rxjs';
import Swal from 'sweetalert2';

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-uspesno-placanje',
  templateUrl: './uspesno-placanje.component.html',
  styleUrls: ['./uspesno-placanje.component.css']
})
export class UspesnoPlacanjeComponent implements OnInit {

  orderId: String;

  constructor(private route: ActivatedRoute, private uspesnoService: UspesnoPlacanjeService) { }

  ngOnInit() {
      this.orderId = this.route.snapshot.paramMap.get('orderId');
      this.uspesnoService.izmenaStatusa(this.orderId);
    Swal.fire({
      position: 'top-end',
      icon: 'success',
      title: 'Uspesno ste platili casopis',
      showConfirmButton: false,
      timer: 4500
    });
      timer(5500).subscribe(t => location.href = '/');
  }

}
