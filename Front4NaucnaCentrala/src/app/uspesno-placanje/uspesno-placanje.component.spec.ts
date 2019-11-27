import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UspesnoPlacanjeComponent } from './uspesno-placanje.component';

describe('UspesnoPlacanjeComponent', () => {
  let component: UspesnoPlacanjeComponent;
  let fixture: ComponentFixture<UspesnoPlacanjeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UspesnoPlacanjeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UspesnoPlacanjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
