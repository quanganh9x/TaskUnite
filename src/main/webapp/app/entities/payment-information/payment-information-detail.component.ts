import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentInformation } from 'app/shared/model/payment-information.model';

@Component({
  selector: 'jhi-payment-information-detail',
  templateUrl: './payment-information-detail.component.html'
})
export class PaymentInformationDetailComponent implements OnInit {
  paymentInformation: IPaymentInformation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ paymentInformation }) => {
      this.paymentInformation = paymentInformation;
    });
  }

  previousState() {
    window.history.back();
  }
}
