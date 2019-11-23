import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITasker } from 'app/shared/model/tasker.model';

@Component({
  selector: 'jhi-tasker-detail',
  templateUrl: './tasker-detail.component.html'
})
export class TaskerDetailComponent implements OnInit {
  tasker: ITasker;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tasker }) => {
      this.tasker = tasker;
    });
  }

  previousState() {
    window.history.back();
  }
}
