import { Korpa } from './korpa';
import { NaucniCasopis } from './naucni-casopis';
import { NaucnaOblast } from './naucna-oblast';

export class Korisnik {
  id: number;
  ime: String;
  prezime: String;
  grad: String;
  drzava: String;
  titula: String;
  email: String;
  korisnicko_ime: String;
  lozinka: String;
  tipKorisnika: String;
  aktiviran_nalog: number;
  recenzent: boolean;
  naucne_oblasti: NaucnaOblast[];
  korpa: Korpa;
}
