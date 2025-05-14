import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AboutFyhComponent } from './about-fyh.component';

describe('AboutFyhComponent', () => {
  let component: AboutFyhComponent;
  let fixture: ComponentFixture<AboutFyhComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AboutFyhComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AboutFyhComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
