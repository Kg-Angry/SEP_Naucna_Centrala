import { TestBed } from '@angular/core/testing';

import { FormeService } from './forme.service';

describe('FormeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FormeService = TestBed.get(FormeService);
    expect(service).toBeTruthy();
  });
});
