import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TaskUniteTestModule } from '../../../test.module';
import { PaymentInformationUpdateComponent } from 'app/entities/payment-information/payment-information-update.component';
import { PaymentInformationService } from 'app/entities/payment-information/payment-information.service';
import { PaymentInformation } from 'app/shared/model/payment-information.model';

describe('Component Tests', () => {
  describe('PaymentInformation Management Update Component', () => {
    let comp: PaymentInformationUpdateComponent;
    let fixture: ComponentFixture<PaymentInformationUpdateComponent>;
    let service: PaymentInformationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [PaymentInformationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PaymentInformationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentInformationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentInformationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentInformation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentInformation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
