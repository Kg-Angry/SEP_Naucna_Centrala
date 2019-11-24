import { TestBed } from '@angular/core/testing';

import { NaucniRadoviService } from './naucni-radovi.service';

describe('NaucniRadoviService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NaucniRadoviService = TestBed.get(NaucniRadoviService);
    expect(service).toBeTruthy();
  });
});
