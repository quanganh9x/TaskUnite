import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Notification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { NotificationComponent } from './notification.component';
import { NotificationDetailComponent } from './notification-detail.component';
import { NotificationUpdateComponent } from './notification-update.component';
import { INotification } from 'app/shared/model/notification.model';

@Injectable({ providedIn: 'root' })
export class NotificationResolve implements Resolve<INotification> {
  constructor(private service: NotificationService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotification> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((notification: HttpResponse<Notification>) => notification.body));
    }
    return of(new Notification());
  }
}

export const notificationRoute: Routes = [
  {
    path: '',
    component: NotificationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'taskUniteApp.notification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NotificationDetailComponent,
    resolve: {
      notification: NotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.notification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NotificationUpdateComponent,
    resolve: {
      notification: NotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.notification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NotificationUpdateComponent,
    resolve: {
      notification: NotificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.notification.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
