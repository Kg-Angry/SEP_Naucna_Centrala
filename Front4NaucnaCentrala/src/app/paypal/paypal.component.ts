import { PaypalService } from './paypal.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-paypal',
  templateUrl: './paypal.component.html',
  styleUrls: ['./paypal.component.css']
})
export class PaypalComponent implements OnInit {

  constructor(private paypalService: PaypalService) { }

  ngOnInit() {
  }

  PayPal($event)
  {
    event.preventDefault()
    const target = event.target;

    this.paypalService.slanjePodataka(target);
  }

}
