import { TestBed } from '@angular/core/testing';

import { ErrorIntercepterService } from './error-intercepter.service';

describe('ErrorIntercepterService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ErrorIntercepterService = TestBed.get(ErrorIntercepterService);
    expect(service).toBeTruthy();
  });
});
