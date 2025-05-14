import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecialistiComponent } from './specialisti.component';

describe('SpecialistiComponent', () => {
  let component: SpecialistiComponent;
  let fixture: ComponentFixture<SpecialistiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpecialistiComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpecialistiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
