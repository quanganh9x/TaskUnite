import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Schedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';
import { ScheduleComponent } from './schedule.component';
import { ScheduleDetailComponent } from './schedule-detail.component';
import { ScheduleUpdateComponent } from './schedule-update.component';
import { ISchedule } from 'app/shared/model/schedule.model';

@Injectable({ providedIn: 'root' })
export class ScheduleResolve implements Resolve<ISchedule> {
  constructor(private service: ScheduleService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchedule> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((schedule: HttpResponse<Schedule>) => schedule.body));
    }
    return of(new Schedule());
  }
}

export const scheduleRoute: Routes = [
  {
    path: '',
    component: ScheduleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'taskUniteApp.schedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ScheduleDetailComponent,
    resolve: {
      schedule: ScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.schedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ScheduleUpdateComponent,
    resolve: {
      schedule: ScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.schedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ScheduleUpdateComponent,
    resolve: {
      schedule: ScheduleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.schedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
