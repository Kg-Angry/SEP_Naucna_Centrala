import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NaucniCasopisComponent } from './naucni-casopis.component';

describe('NaucniCasopisComponent', () => {
  let component: NaucniCasopisComponent;
  let fixture: ComponentFixture<NaucniCasopisComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NaucniCasopisComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NaucniCasopisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
