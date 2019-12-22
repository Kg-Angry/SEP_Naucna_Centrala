import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { NaucniCasopisComponent } from './naucni-casopis/naucni-casopis.component';
import { NaucnaOblastComponent } from './naucna-oblast/naucna-oblast.component';
import { NaucniRadoviComponent } from './naucni-radovi/naucni-radovi.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSelectModule} from '@angular/material/select';
import { PretragaElasticComponent } from './pretraga-elastic/pretraga-elastic.component';
import { UspesnoPlacanjeComponent } from './uspesno-placanje/uspesno-placanje.component';
import { PaypalComponent } from './paypal/paypal.component';
import { NeuspesnoPlacanjeComponent } from './neuspesno-placanje/neuspesno-placanje.component';
import { IntercepterService } from './intercepter.service';
import { ErrorIntercepterService } from './error-intercepter.service';
import { MAT_DATE_LOCALE } from '@angular/material/core';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegistrationComponent,
    NaucniCasopisComponent,
    NaucnaOblastComponent,
    NaucniRadoviComponent,
    UserProfileComponent,
    PretragaElasticComponent,
    UspesnoPlacanjeComponent,
    PaypalComponent,
    NeuspesnoPlacanjeComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatSelectModule,
  ],
  providers: [{provide : HTTP_INTERCEPTORS,
  useClass: IntercepterService,
  multi   : true,
  },
    {
      provide: ErrorHandler,
      useClass: ErrorIntercepterService,
    },
      {provide: MAT_DATE_LOCALE, useValue: 'en-GB'},
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
