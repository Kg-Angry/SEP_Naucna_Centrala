import { NaucniCasopis } from './naucni-casopis';
import { Korisnik } from './korisnik';
import { NaucnaOblast } from './naucna-oblast';
export class NaucniRad {
  id: number;
  naslov: String;
  koautor: Korisnik[];
  kljucni_pojmovi: String;
  apstrakt: String;
  oblast_pripadanja: NaucnaOblast;
  putanja_upload_fajla: String;
  naucni_casopis: NaucniCasopis;
}
