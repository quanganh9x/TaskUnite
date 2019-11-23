import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskUniteSharedModule } from 'app/shared/shared.module';
import { UserInformationComponent } from './user-information.component';
import { UserInformationDetailComponent } from './user-information-detail.component';
import { UserInformationUpdateComponent } from './user-information-update.component';
import { UserInformationDeleteDialogComponent } from './user-information-delete-dialog.component';
import { userInformationRoute } from './user-information.route';

@NgModule({
  imports: [TaskUniteSharedModule, RouterModule.forChild(userInformationRoute)],
  declarations: [
    UserInformationComponent,
    UserInformationDetailComponent,
    UserInformationUpdateComponent,
    UserInformationDeleteDialogComponent
  ],
  entryComponents: [UserInformationDeleteDialogComponent]
})
export class TaskUniteUserInformationModule {}
