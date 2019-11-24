import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PretragaElasticComponent } from './pretraga-elastic.component';

describe('PretragaElasticComponent', () => {
  let component: PretragaElasticComponent;
  let fixture: ComponentFixture<PretragaElasticComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PretragaElasticComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PretragaElasticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
