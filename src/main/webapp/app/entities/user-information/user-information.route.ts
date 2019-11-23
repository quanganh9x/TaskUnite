import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserInformation } from 'app/shared/model/user-information.model';
import { UserInformationService } from './user-information.service';
import { UserInformationComponent } from './user-information.component';
import { UserInformationDetailComponent } from './user-information-detail.component';
import { UserInformationUpdateComponent } from './user-information-update.component';
import { IUserInformation } from 'app/shared/model/user-information.model';

@Injectable({ providedIn: 'root' })
export class UserInformationResolve implements Resolve<IUserInformation> {
  constructor(private service: UserInformationService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserInformation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((userInformation: HttpResponse<UserInformation>) => userInformation.body));
    }
    return of(new UserInformation());
  }
}

export const userInformationRoute: Routes = [
  {
    path: '',
    component: UserInformationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'taskUniteApp.userInformation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserInformationDetailComponent,
    resolve: {
      userInformation: UserInformationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.userInformation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserInformationUpdateComponent,
    resolve: {
      userInformation: UserInformationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.userInformation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserInformationUpdateComponent,
    resolve: {
      userInformation: UserInformationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.userInformation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
