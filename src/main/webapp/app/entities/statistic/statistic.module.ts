import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskUniteSharedModule } from 'app/shared/shared.module';
import { StatisticComponent } from './statistic.component';
import { StatisticDetailComponent } from './statistic-detail.component';
import { StatisticUpdateComponent } from './statistic-update.component';
import { StatisticDeleteDialogComponent } from './statistic-delete-dialog.component';
import { statisticRoute } from './statistic.route';

@NgModule({
  imports: [TaskUniteSharedModule, RouterModule.forChild(statisticRoute)],
  declarations: [StatisticComponent, StatisticDetailComponent, StatisticUpdateComponent, StatisticDeleteDialogComponent],
  entryComponents: [StatisticDeleteDialogComponent]
})
export class TaskUniteStatisticModule {}
