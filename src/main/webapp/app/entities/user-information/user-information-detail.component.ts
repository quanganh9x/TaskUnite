import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserInformation } from 'app/shared/model/user-information.model';

@Component({
  selector: 'jhi-user-information-detail',
  templateUrl: './user-information-detail.component.html'
})
export class UserInformationDetailComponent implements OnInit {
  userInformation: IUserInformation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ userInformation }) => {
      this.userInformation = userInformation;
    });
  }

  previousState() {
    window.history.back();
  }
}
