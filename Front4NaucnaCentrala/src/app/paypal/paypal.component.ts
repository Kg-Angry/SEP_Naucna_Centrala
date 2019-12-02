import { PaypalService } from './paypal.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-paypal',
  templateUrl: './paypal.component.html',
  styleUrls: ['./paypal.component.css']
})
export class PaypalComponent implements OnInit {

  request: String[] = [];
  payID: String;
  token: String;
  payerID: String;

  constructor(private paypalService: PaypalService, private router: Router) { }

  ngOnInit() {
    //console.log("Trenutna putanja: " + this.router.url);
    let args = this.router.url.split('?');
    //console.log("Argument: " + args[1]);
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
  }

}
