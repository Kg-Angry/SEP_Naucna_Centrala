import { Placanje } from './placanje.enum';
import { NaucnaOblast } from './naucna-oblast';
import { Korisnik } from './korisnik';
export class NaucniCasopis {
  id: number;
  naziv: String;
  issn: number;
  tipCasopisa: String;
  glavni_urednik: Korisnik;
  urednici: Korisnik[];
  recenzenti: Korisnik[];
  naucna_oblast: NaucnaOblast[];
  tipPlacanja: String[] = [];
  tipoviPlacanja: Placanje[];
  status: boolean;
  cena: number;
}
