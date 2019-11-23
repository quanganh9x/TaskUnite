import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMaster } from 'app/shared/model/master.model';

@Component({
  selector: 'jhi-master-detail',
  templateUrl: './master-detail.component.html'
})
export class MasterDetailComponent implements OnInit {
  master: IMaster;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ master }) => {
      this.master = master;
    });
  }

  previousState() {
    window.history.back();
  }
}
