import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TaskUniteTestModule } from '../../../test.module';
import { PaymentInformationDeleteDialogComponent } from 'app/entities/payment-information/payment-information-delete-dialog.component';
import { PaymentInformationService } from 'app/entities/payment-information/payment-information.service';

describe('Component Tests', () => {
  describe('PaymentInformation Management Delete Component', () => {
    let comp: PaymentInformationDeleteDialogComponent;
    let fixture: ComponentFixture<PaymentInformationDeleteDialogComponent>;
    let service: PaymentInformationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TaskUniteTestModule],
        declarations: [PaymentInformationDeleteDialogComponent]
      })
        .overrideTemplate(PaymentInformationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentInformationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentInformationService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
