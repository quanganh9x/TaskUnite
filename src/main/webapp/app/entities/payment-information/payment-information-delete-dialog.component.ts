import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentInformation } from 'app/shared/model/payment-information.model';
import { PaymentInformationService } from './payment-information.service';

@Component({
  templateUrl: './payment-information-delete-dialog.component.html'
})
export class PaymentInformationDeleteDialogComponent {
  paymentInformation: IPaymentInformation;

  constructor(
    protected paymentInformationService: PaymentInformationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.paymentInformationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'paymentInformationListModification',
        content: 'Deleted an paymentInformation'
      });
      this.activeModal.dismiss(true);
    });
  }
}
