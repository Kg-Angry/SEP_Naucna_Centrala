import { NaucniCasopis } from './naucni-casopis';
import { NaucniRad } from './naucni-rad';

export class Korpa {
  id: number;
  naucni_casopis_list: NaucniCasopis[] = [];
  naucni_rad_list: NaucniRad[] = [];
}
