import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComandaDialogComponent } from './comanda-dialog.component';

describe('ComandaDialogComponent', () => {
  let component: ComandaDialogComponent;
  let fixture: ComponentFixture<ComandaDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComandaDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ComandaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
