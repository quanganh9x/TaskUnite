import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistrict } from 'app/shared/model/district.model';

@Component({
  selector: 'jhi-district-detail',
  templateUrl: './district-detail.component.html'
})
export class DistrictDetailComponent implements OnInit {
  district: IDistrict;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ district }) => {
      this.district = district;
    });
  }

  previousState() {
    window.history.back();
  }
}
