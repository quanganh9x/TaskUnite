import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMaster } from 'app/shared/model/master.model';
import { MasterService } from './master.service';

@Component({
  templateUrl: './master-delete-dialog.component.html'
})
export class MasterDeleteDialogComponent {
  master: IMaster;

  constructor(protected masterService: MasterService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.masterService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'masterListModification',
        content: 'Deleted an master'
      });
      this.activeModal.dismiss(true);
    });
  }
}
