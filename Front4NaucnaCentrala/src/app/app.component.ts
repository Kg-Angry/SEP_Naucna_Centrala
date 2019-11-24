import { Korisnik } from './class/korisnik';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Front4NaucnaCentrala';

  korisnik: Korisnik = JSON.parse(localStorage.getItem('korisnik'));
}
