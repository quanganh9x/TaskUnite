import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITaskCategory } from 'app/shared/model/task-category.model';

@Component({
  selector: 'jhi-task-category-detail',
  templateUrl: './task-category-detail.component.html'
})
export class TaskCategoryDetailComponent implements OnInit {
  taskCategory: ITaskCategory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ taskCategory }) => {
      this.taskCategory = taskCategory;
    });
  }

  previousState() {
    window.history.back();
  }
}
