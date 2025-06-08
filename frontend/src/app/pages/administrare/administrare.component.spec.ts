import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministrareComponent } from './administrare.component';

describe('AdministrareComponent', () => {
  let component: AdministrareComponent;
  let fixture: ComponentFixture<AdministrareComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdministrareComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministrareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
