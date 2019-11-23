import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStatistic } from 'app/shared/model/statistic.model';
import { StatisticService } from './statistic.service';

@Component({
  templateUrl: './statistic-delete-dialog.component.html'
})
export class StatisticDeleteDialogComponent {
  statistic: IStatistic;

  constructor(protected statisticService: StatisticService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.statisticService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'statisticListModification',
        content: 'Deleted an statistic'
      });
      this.activeModal.dismiss(true);
    });
  }
}
