import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecializariComponent } from './specializari.component';

describe('SpecializariComponent', () => {
  let component: SpecializariComponent;
  let fixture: ComponentFixture<SpecializariComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpecializariComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpecializariComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
