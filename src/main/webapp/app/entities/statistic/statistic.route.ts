import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Statistic } from 'app/shared/model/statistic.model';
import { StatisticService } from './statistic.service';
import { StatisticComponent } from './statistic.component';
import { StatisticDetailComponent } from './statistic-detail.component';
import { StatisticUpdateComponent } from './statistic-update.component';
import { IStatistic } from 'app/shared/model/statistic.model';

@Injectable({ providedIn: 'root' })
export class StatisticResolve implements Resolve<IStatistic> {
  constructor(private service: StatisticService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStatistic> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((statistic: HttpResponse<Statistic>) => statistic.body));
    }
    return of(new Statistic());
  }
}

export const statisticRoute: Routes = [
  {
    path: '',
    component: StatisticComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'taskUniteApp.statistic.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StatisticDetailComponent,
    resolve: {
      statistic: StatisticResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.statistic.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StatisticUpdateComponent,
    resolve: {
      statistic: StatisticResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.statistic.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StatisticUpdateComponent,
    resolve: {
      statistic: StatisticResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.statistic.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
