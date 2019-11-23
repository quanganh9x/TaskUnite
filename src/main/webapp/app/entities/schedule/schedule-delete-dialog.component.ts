import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISchedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';

@Component({
  templateUrl: './schedule-delete-dialog.component.html'
})
export class ScheduleDeleteDialogComponent {
  schedule: ISchedule;

  constructor(protected scheduleService: ScheduleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.scheduleService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'scheduleListModification',
        content: 'Deleted an schedule'
      });
      this.activeModal.dismiss(true);
    });
  }
}
