import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDistrict } from 'app/shared/model/district.model';
import { DistrictService } from './district.service';

@Component({
  templateUrl: './district-delete-dialog.component.html'
})
export class DistrictDeleteDialogComponent {
  district: IDistrict;

  constructor(protected districtService: DistrictService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.districtService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'districtListModification',
        content: 'Deleted an district'
      });
      this.activeModal.dismiss(true);
    });
  }
}
