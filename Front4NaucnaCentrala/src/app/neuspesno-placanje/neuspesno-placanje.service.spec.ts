import { TestBed } from '@angular/core/testing';

import { NeuspesnoPlacanjeService } from './neuspesno-placanje.service';

describe('NeuspesnoPlacanjeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NeuspesnoPlacanjeService = TestBed.get(NeuspesnoPlacanjeService);
    expect(service).toBeTruthy();
  });
});
