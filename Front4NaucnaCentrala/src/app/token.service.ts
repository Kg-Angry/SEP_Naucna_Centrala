import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  token = 'JWTtoken';

  constructor() { }

  getToken() {
    return localStorage.getItem(this.token);
  }

  setToken(param) {
    localStorage.setItem(this.token, param);
  }

  removeToken() {
    localStorage.removeItem(this.token);
  }
}
