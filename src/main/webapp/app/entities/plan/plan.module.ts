import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskUniteSharedModule } from 'app/shared/shared.module';
import { PlanComponent } from './plan.component';
import { PlanDetailComponent } from './plan-detail.component';
import { PlanUpdateComponent } from './plan-update.component';
import { PlanDeleteDialogComponent } from './plan-delete-dialog.component';
import { planRoute } from './plan.route';

@NgModule({
  imports: [TaskUniteSharedModule, RouterModule.forChild(planRoute)],
  declarations: [PlanComponent, PlanDetailComponent, PlanUpdateComponent, PlanDeleteDialogComponent],
  entryComponents: [PlanDeleteDialogComponent]
})
export class TaskUnitePlanModule {}
