import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecializareListComponent } from './specializare-list.component';

describe('SpecializareListComponent', () => {
  let component: SpecializareListComponent;
  let fixture: ComponentFixture<SpecializareListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpecializareListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpecializareListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
