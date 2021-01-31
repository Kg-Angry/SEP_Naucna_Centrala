import { TestBed } from '@angular/core/testing';

import { UspesnoPlacanjeService } from './uspesno-placanje.service';

describe('UspesnoPlacanjeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UspesnoPlacanjeService = TestBed.get(UspesnoPlacanjeService);
    expect(service).toBeTruthy();
  });
});
