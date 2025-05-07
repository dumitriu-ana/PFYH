import { TestBed } from '@angular/core/testing';

import { SpecializareService } from './specializare.service';

describe('SpecializareService', () => {
  let service: SpecializareService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SpecializareService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
