import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Master } from 'app/shared/model/master.model';
import { MasterService } from './master.service';
import { MasterComponent } from './master.component';
import { MasterDetailComponent } from './master-detail.component';
import { MasterUpdateComponent } from './master-update.component';
import { IMaster } from 'app/shared/model/master.model';

@Injectable({ providedIn: 'root' })
export class MasterResolve implements Resolve<IMaster> {
  constructor(private service: MasterService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMaster> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((master: HttpResponse<Master>) => master.body));
    }
    return of(new Master());
  }
}

export const masterRoute: Routes = [
  {
    path: '',
    component: MasterComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'taskUniteApp.master.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MasterDetailComponent,
    resolve: {
      master: MasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.master.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MasterUpdateComponent,
    resolve: {
      master: MasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.master.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MasterUpdateComponent,
    resolve: {
      master: MasterResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.master.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
