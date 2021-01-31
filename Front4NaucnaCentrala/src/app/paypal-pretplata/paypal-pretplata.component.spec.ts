import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaypalPretplataComponent } from './paypal-pretplata.component';

describe('PaypalPretplataComponent', () => {
  let component: PaypalPretplataComponent;
  let fixture: ComponentFixture<PaypalPretplataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaypalPretplataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaypalPretplataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
