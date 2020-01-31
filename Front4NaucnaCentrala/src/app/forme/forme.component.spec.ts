import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormeComponent } from './forme.component';

describe('FormeComponent', () => {
  let component: FormeComponent;
  let fixture: ComponentFixture<FormeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
