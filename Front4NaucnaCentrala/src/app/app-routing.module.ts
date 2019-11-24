import { PretragaElasticComponent } from './pretraga-elastic/pretraga-elastic.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { NaucniRadoviComponent } from './naucni-radovi/naucni-radovi.component';
import { NaucnaOblastComponent } from './naucna-oblast/naucna-oblast.component';
import { NaucniCasopisComponent } from './naucni-casopis/naucni-casopis.component';
import { RegistrationComponent } from './registration/registration.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

const appRoutes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'scientific_journal', component: NaucniCasopisComponent},
  {path: 'scientific_area', component: NaucnaOblastComponent},
  {path: 'scientific_papers', component: NaucniRadoviComponent},
  {path: 'userProfile', component: UserProfileComponent},
  {path: 'search', component: PretragaElasticComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true}
    ),
  ],
  declarations: []
})
export class AppRoutingModule { }
