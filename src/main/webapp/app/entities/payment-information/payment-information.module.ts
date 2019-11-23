import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskUniteSharedModule } from 'app/shared/shared.module';
import { PaymentInformationComponent } from './payment-information.component';
import { PaymentInformationDetailComponent } from './payment-information-detail.component';
import { PaymentInformationUpdateComponent } from './payment-information-update.component';
import { PaymentInformationDeleteDialogComponent } from './payment-information-delete-dialog.component';
import { paymentInformationRoute } from './payment-information.route';

@NgModule({
  imports: [TaskUniteSharedModule, RouterModule.forChild(paymentInformationRoute)],
  declarations: [
    PaymentInformationComponent,
    PaymentInformationDetailComponent,
    PaymentInformationUpdateComponent,
    PaymentInformationDeleteDialogComponent
  ],
  entryComponents: [PaymentInformationDeleteDialogComponent]
})
export class TaskUnitePaymentInformationModule {}
