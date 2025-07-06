import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficSpecializariComponent } from './grafic-specializari.component';

describe('GraficSpecializariComponent', () => {
  let component: GraficSpecializariComponent;
  let fixture: ComponentFixture<GraficSpecializariComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GraficSpecializariComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GraficSpecializariComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
