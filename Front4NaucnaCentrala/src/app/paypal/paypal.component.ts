import { UspesnoPlacanjeService } from './../uspesno-placanje/uspesno-placanje.service';
import { PaypalService } from './paypal.service';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-paypal',
  templateUrl: './paypal.component.html',
  styleUrls: ['./paypal.component.css']
})
export class PaypalComponent implements OnInit {

  orderId: String;
  request: String[] = [];
  payID: String;
  token: String;
  payerID: String;

  constructor(private paypalService: PaypalService, private router: Router,
         private route: ActivatedRoute,private uspesnoService: UspesnoPlacanjeService) { }

  ngOnInit() {

    let args = this.router.url.split('?');
    let splitovanje = args[1].split('&');
    for(let i=0;i<splitovanje.length;i++)
    {
      let podela = splitovanje[i].split('=');
      this.request.push(podela[1]);
    }
    this.payID = this.request[0];
    this.token = this.request[1];
    this.payerID = this.request[2];

  }

  PayPal($event)
  {
    event.preventDefault()
    const target = event.target;

    this.paypalService.slanjePodataka(target, this.payID, this.token, this.payerID);
    this.orderId = this.route.snapshot.paramMap.get('orderId');
      this.uspesnoService.izmenaStatusa(this.orderId);
  }

}
