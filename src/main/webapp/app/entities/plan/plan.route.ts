import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Plan } from 'app/shared/model/plan.model';
import { PlanService } from './plan.service';
import { PlanComponent } from './plan.component';
import { PlanDetailComponent } from './plan-detail.component';
import { PlanUpdateComponent } from './plan-update.component';
import { IPlan } from 'app/shared/model/plan.model';

@Injectable({ providedIn: 'root' })
export class PlanResolve implements Resolve<IPlan> {
  constructor(private service: PlanService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlan> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((plan: HttpResponse<Plan>) => plan.body));
    }
    return of(new Plan());
  }
}

export const planRoute: Routes = [
  {
    path: '',
    component: PlanComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'taskUniteApp.plan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlanDetailComponent,
    resolve: {
      plan: PlanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.plan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlanUpdateComponent,
    resolve: {
      plan: PlanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.plan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlanUpdateComponent,
    resolve: {
      plan: PlanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'taskUniteApp.plan.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
