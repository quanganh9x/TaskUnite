import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITasker } from 'app/shared/model/tasker.model';
import { TaskerService } from './tasker.service';

@Component({
  templateUrl: './tasker-delete-dialog.component.html'
})
export class TaskerDeleteDialogComponent {
  tasker: ITasker;

  constructor(protected taskerService: TaskerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.taskerService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'taskerListModification',
        content: 'Deleted an tasker'
      });
      this.activeModal.dismiss(true);
    });
  }
}
