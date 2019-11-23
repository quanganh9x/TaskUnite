import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskUniteSharedModule } from 'app/shared/shared.module';
import { TaskerComponent } from './tasker.component';
import { TaskerDetailComponent } from './tasker-detail.component';
import { TaskerUpdateComponent } from './tasker-update.component';
import { TaskerDeleteDialogComponent } from './tasker-delete-dialog.component';
import { taskerRoute } from './tasker.route';

@NgModule({
  imports: [TaskUniteSharedModule, RouterModule.forChild(taskerRoute)],
  declarations: [TaskerComponent, TaskerDetailComponent, TaskerUpdateComponent, TaskerDeleteDialogComponent],
  entryComponents: [TaskerDeleteDialogComponent]
})
export class TaskUniteTaskerModule {}
