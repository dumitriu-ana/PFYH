import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiciiListComponent } from './servicii-list.component';

describe('ServiciiListComponent', () => {
  let component: ServiciiListComponent;
  let fixture: ComponentFixture<ServiciiListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ServiciiListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ServiciiListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
