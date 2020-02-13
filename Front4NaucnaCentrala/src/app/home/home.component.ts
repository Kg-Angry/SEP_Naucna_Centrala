import { Korisnik } from './../class/korisnik';
import { NaucniCasopisService } from './../naucni-casopis/naucni-casopis.service';
import { UserProfileService } from './../user-profile/user-profile.service';
import { HomeService } from './home.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  korisnik: Korisnik = JSON.parse(localStorage.getItem('korisnik'));

  constructor(private homeService: HomeService, private userService: UserProfileService, private casopis: NaucniCasopisService) { }

  ngOnInit() {
      if(this.korisnik){
        this.homeService.sveTransakcije();
        this.homeService.proveraPretplate(this.korisnik.korisnicko_ime);
      }
      this.homeService.getOblasti();
      this.homeService.getNaucniCasopisi();
      this.homeService.getNaucniRadovi();
      this.userService.getAllUsers();
      this.casopis.getTipoviPlacanja();
      this.casopis.getTipoviPlacanjaZaSveCasopise();
  }

}
