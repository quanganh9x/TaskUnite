import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserInformation } from 'app/shared/model/user-information.model';
import { UserInformationService } from './user-information.service';

@Component({
  templateUrl: './user-information-delete-dialog.component.html'
})
export class UserInformationDeleteDialogComponent {
  userInformation: IUserInformation;

  constructor(
    protected userInformationService: UserInformationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.userInformationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'userInformationListModification',
        content: 'Deleted an userInformation'
      });
      this.activeModal.dismiss(true);
    });
  }
}
