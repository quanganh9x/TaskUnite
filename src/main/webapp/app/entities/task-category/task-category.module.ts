import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskUniteSharedModule } from 'app/shared/shared.module';
import { TaskCategoryComponent } from './task-category.component';
import { TaskCategoryDetailComponent } from './task-category-detail.component';
import { TaskCategoryUpdateComponent } from './task-category-update.component';
import { TaskCategoryDeleteDialogComponent } from './task-category-delete-dialog.component';
import { taskCategoryRoute } from './task-category.route';

@NgModule({
  imports: [TaskUniteSharedModule, RouterModule.forChild(taskCategoryRoute)],
  declarations: [TaskCategoryComponent, TaskCategoryDetailComponent, TaskCategoryUpdateComponent, TaskCategoryDeleteDialogComponent],
  entryComponents: [TaskCategoryDeleteDialogComponent]
})
export class TaskUniteTaskCategoryModule {}
