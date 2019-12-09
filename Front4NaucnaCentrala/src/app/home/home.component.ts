import { NaucniCasopisService } from './../naucni-casopis/naucni-casopis.service';
import { UserProfileService } from './../user-profile/user-profile.service';
import { HomeService } from './home.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private homeService: HomeService, private userService: UserProfileService, private casopis: NaucniCasopisService) { }

  ngOnInit() {
    this.homeService.getOblasti();
    this.homeService.getNaucniCasopisi();
    this.homeService.getNaucniRadovi();
    this.userService.getAllUsers();
    this.casopis.getTipoviPlacanja();
  }

}
