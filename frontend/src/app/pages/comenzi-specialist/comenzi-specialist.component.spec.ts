import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComenziSpecialistComponent } from './comenzi-specialist.component';

describe('ComenziSpecialistComponent', () => {
  let component: ComenziSpecialistComponent;
  let fixture: ComponentFixture<ComenziSpecialistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComenziSpecialistComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComenziSpecialistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
