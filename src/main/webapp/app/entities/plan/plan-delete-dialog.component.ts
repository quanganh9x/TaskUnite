import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlan } from 'app/shared/model/plan.model';
import { PlanService } from './plan.service';

@Component({
  templateUrl: './plan-delete-dialog.component.html'
})
export class PlanDeleteDialogComponent {
  plan: IPlan;

  constructor(protected planService: PlanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.planService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'planListModification',
        content: 'Deleted an plan'
      });
      this.activeModal.dismiss(true);
    });
  }
}
