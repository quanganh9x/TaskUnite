import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { PaymentInformationDetailComponent } from 'app/entities/payment-information/payment-information-detail.component';
import { PaymentInformation } from 'app/shared/model/payment-information.model';

describe('Component Tests', () => {
  describe('PaymentInformation Management Detail Component', () => {
    let comp: PaymentInformationDetailComponent;
    let fixture: ComponentFixture<PaymentInformationDetailComponent>;
    const route = ({ data: of({ paymentInformation: new PaymentInformation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [PaymentInformationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PaymentInformationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentInformationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentInformation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
