import { TestBed } from '@angular/core/testing';

import { NaucnaOblastService } from './naucna-oblast.service';

describe('NaucnaOblastService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: NaucnaOblastService = TestBed.get(NaucnaOblastService);
    expect(service).toBeTruthy();
  });
});
