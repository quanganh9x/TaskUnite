import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IUserInformation, UserInformation } from 'app/shared/model/user-information.model';
import { UserInformationService } from './user-information.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ITasker } from 'app/shared/model/tasker.model';
import { TaskerService } from 'app/entities/tasker/tasker.service';
import { IMaster } from 'app/shared/model/master.model';
import { MasterService } from 'app/entities/master/master.service';
import { IStatistic } from 'app/shared/model/statistic.model';
import { StatisticService } from 'app/entities/statistic/statistic.service';

@Component({
  selector: 'jhi-user-information-update',
  templateUrl: './user-information-update.component.html'
})
export class UserInformationUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  taskers: ITasker[];

  masters: IMaster[];

  statistics: IStatistic[];

  editForm = this.fb.group({
    id: [],
    gender: [],
    address: [],
    phone: [],
    status: [],
    createdAt: [],
    updatedAt: [],
    deletedAt: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected userInformationService: UserInformationService,
    protected userService: UserService,
    protected taskerService: TaskerService,
    protected masterService: MasterService,
    protected statisticService: StatisticService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userInformation }) => {
      this.updateForm(userInformation);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.taskerService
      .query()
      .subscribe((res: HttpResponse<ITasker[]>) => (this.taskers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.masterService
      .query()
      .subscribe((res: HttpResponse<IMaster[]>) => (this.masters = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.statisticService
      .query()
      .subscribe((res: HttpResponse<IStatistic[]>) => (this.statistics = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(userInformation: IUserInformation) {
    this.editForm.patchValue({
      id: userInformation.id,
      gender: userInformation.gender,
      address: userInformation.address,
      phone: userInformation.phone,
      status: userInformation.status,
      createdAt: userInformation.createdAt != null ? userInformation.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: userInformation.updatedAt != null ? userInformation.updatedAt.format(DATE_TIME_FORMAT) : null,
      deletedAt: userInformation.deletedAt != null ? userInformation.deletedAt.format(DATE_TIME_FORMAT) : null,
      userId: userInformation.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userInformation = this.createFromForm();
    if (userInformation.id !== undefined) {
      this.subscribeToSaveResponse(this.userInformationService.update(userInformation));
    } else {
      this.subscribeToSaveResponse(this.userInformationService.create(userInformation));
    }
  }

  private createFromForm(): IUserInformation {
    return {
      ...new UserInformation(),
      id: this.editForm.get(['id']).value,
      gender: this.editForm.get(['gender']).value,
      address: this.editForm.get(['address']).value,
      phone: this.editForm.get(['phone']).value,
      status: this.editForm.get(['status']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      deletedAt:
        this.editForm.get(['deletedAt']).value != null ? moment(this.editForm.get(['deletedAt']).value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserInformation>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackTaskerById(index: number, item: ITasker) {
    return item.id;
  }

  trackMasterById(index: number, item: IMaster) {
    return item.id;
  }

  trackStatisticById(index: number, item: IStatistic) {
    return item.id;
  }
}
