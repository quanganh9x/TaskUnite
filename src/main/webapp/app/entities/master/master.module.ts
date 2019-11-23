import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskUniteSharedModule } from 'app/shared/shared.module';
import { MasterComponent } from './master.component';
import { MasterDetailComponent } from './master-detail.component';
import { MasterUpdateComponent } from './master-update.component';
import { MasterDeleteDialogComponent } from './master-delete-dialog.component';
import { masterRoute } from './master.route';

@NgModule({
  imports: [TaskUniteSharedModule, RouterModule.forChild(masterRoute)],
  declarations: [MasterComponent, MasterDetailComponent, MasterUpdateComponent, MasterDeleteDialogComponent],
  entryComponents: [MasterDeleteDialogComponent]
})
export class TaskUniteMasterModule {}
