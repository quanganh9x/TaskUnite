import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PaymentInformation } from 'app/shared/model/payment-information.model';
import { PaymentInformationService } from './payment-information.service';
import { PaymentInformationComponent } from './payment-information.component';
import { PaymentInformationDetailComponent } from './payment-information-detail.component';
import { PaymentInformationUpdateComponent } from './payment-information-update.component';
import { IPaymentInformation } from 'app/shared/model/payment-information.model';

@Injectable({ providedIn: 'root' })
export class PaymentInformationResolve implements Resolve<IPaymentInformation> {
  constructor(private service: PaymentInformationService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentInformation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((paymentInformation: HttpResponse<PaymentInformation>) => paymentInformation.body));
    }
    return of(new PaymentInformation());
  }
}

export const paymentInformationRoute: Routes = [
  {
    path: '',
    component: PaymentInformationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'taskUniteApp.paymentInformation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PaymentInformationDetailComponent,
    resolve: {
      paymentInformation: PaymentInformationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.paymentInformation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PaymentInformationUpdateComponent,
    resolve: {
      paymentInformation: PaymentInformationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.paymentInformation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PaymentInformationUpdateComponent,
    resolve: {
      paymentInformation: PaymentInformationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.paymentInformation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
