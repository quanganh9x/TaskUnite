import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskCategory } from 'app/shared/model/task-category.model';
import { TaskCategoryService } from './task-category.service';

@Component({
  templateUrl: './task-category-delete-dialog.component.html'
})
export class TaskCategoryDeleteDialogComponent {
  taskCategory: ITaskCategory;

  constructor(
    protected taskCategoryService: TaskCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.taskCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'taskCategoryListModification',
        content: 'Deleted an taskCategory'
      });
      this.activeModal.dismiss(true);
    });
  }
}
