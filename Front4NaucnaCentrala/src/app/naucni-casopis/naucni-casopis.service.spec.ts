import { TestBed } from '@angular/core/testing';

import { NaucniCasopisService } from './naucni-casopis.service';

describe('NaucniCasopisService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NaucniCasopisService = TestBed.get(NaucniCasopisService);
    expect(service).toBeTruthy();
  });
});
