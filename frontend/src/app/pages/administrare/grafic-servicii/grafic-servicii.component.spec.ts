import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GraficServiciiComponent } from './grafic-servicii.component';

describe('GraficServiciiComponent', () => {
  let component: GraficServiciiComponent;
  let fixture: ComponentFixture<GraficServiciiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GraficServiciiComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GraficServiciiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
