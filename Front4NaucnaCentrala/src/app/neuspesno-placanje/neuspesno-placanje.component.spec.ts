import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NeuspesnoPlacanjeComponent } from './neuspesno-placanje.component';

describe('NeuspesnoPlacanjeComponent', () => {
  let component: NeuspesnoPlacanjeComponent;
  let fixture: ComponentFixture<NeuspesnoPlacanjeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NeuspesnoPlacanjeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NeuspesnoPlacanjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
