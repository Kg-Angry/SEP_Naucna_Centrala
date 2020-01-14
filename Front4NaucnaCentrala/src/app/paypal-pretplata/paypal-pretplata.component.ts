import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PaypalService } from '../paypal/paypal.service';

@Component({
  selector: 'app-paypal-pretplata',
  templateUrl: './paypal-pretplata.component.html',
  styleUrls: ['./paypal-pretplata.component.css']
})
export class PaypalPretplataComponent implements OnInit {

  token: String;
  constructor( private router: Router, private paypalService: PaypalService) { }

  ngOnInit() {
    let args = this.router.url.split('?');
    let splitovanje = args[1].split('=');
    this.token = splitovanje[1];
  }

  PayPal($event)
  {
    event.preventDefault()
    const target = event.target;

     this.paypalService.slanjePodatakaZaPretplatu(this.token);

  }

}
