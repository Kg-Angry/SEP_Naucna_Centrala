import { NaucniRad } from './naucni-rad';
import { NaucniCasopis } from './naucni-casopis';

export class NaucnaOblast {
  id: number;
  naziv: String;
  opis: String;
  casopisi: NaucniCasopis[];
  naucni_radovi: NaucniRad[];
}
