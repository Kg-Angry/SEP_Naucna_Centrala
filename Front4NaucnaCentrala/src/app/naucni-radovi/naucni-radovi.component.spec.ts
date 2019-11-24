import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NaucniRadoviComponent } from './naucni-radovi.component';

describe('NaucniRadoviComponent', () => {
  let component: NaucniRadoviComponent;
  let fixture: ComponentFixture<NaucniRadoviComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NaucniRadoviComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NaucniRadoviComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
