import { RegistrationService } from './registration.service';
import { Component, OnInit } from '@angular/core';
import { NaucnaOblast } from '../class/naucna-oblast';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  recenzent = false;
  IzabranaNaucnaOblast: NaucnaOblast[] = [];
  oblasti: NaucnaOblast [] = JSON.parse(localStorage.getItem('oblasti'));

  uloga: String = 'OBICAN';
  constructor(private registracijaService: RegistrationService) { }

  ngOnInit() {

  }

  RegistracijaKorisnika($event) {
    event.preventDefault();
    const target = event.target;
    if (this.IzabranaNaucnaOblast.length < 1) {
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: 'Broj naucnih oblasti minimalno mora biti 1',
        showConfirmButton: false,
        timer: 2500
      });
    } else {
      this.registracijaService.Registracija(target, this.recenzent, this.uloga, this.IzabranaNaucnaOblast);
    }
  }

}
