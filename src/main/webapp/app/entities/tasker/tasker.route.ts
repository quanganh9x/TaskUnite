import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Tasker } from 'app/shared/model/tasker.model';
import { TaskerService } from './tasker.service';
import { TaskerComponent } from './tasker.component';
import { TaskerDetailComponent } from './tasker-detail.component';
import { TaskerUpdateComponent } from './tasker-update.component';
import { ITasker } from 'app/shared/model/tasker.model';

@Injectable({ providedIn: 'root' })
export class TaskerResolve implements Resolve<ITasker> {
  constructor(private service: TaskerService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITasker> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((tasker: HttpResponse<Tasker>) => tasker.body));
    }
    return of(new Tasker());
  }
}

export const taskerRoute: Routes = [
  {
    path: '',
    component: TaskerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'taskUniteApp.tasker.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TaskerDetailComponent,
    resolve: {
      tasker: TaskerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.tasker.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TaskerUpdateComponent,
    resolve: {
      tasker: TaskerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.tasker.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TaskerUpdateComponent,
    resolve: {
      tasker: TaskerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.tasker.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
