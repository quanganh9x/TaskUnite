import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IStatistic, Statistic } from 'app/shared/model/statistic.model';
import { StatisticService } from './statistic.service';
import { IUserInformation } from 'app/shared/model/user-information.model';
import { UserInformationService } from 'app/entities/user-information/user-information.service';

@Component({
  selector: 'jhi-statistic-update',
  templateUrl: './statistic-update.component.html'
})
export class StatisticUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUserInformation[];
  createdAtDp: any;
  updatedAtDp: any;
  deletedAtDp: any;

  editForm = this.fb.group({
    id: [],
    level: [],
    experience: [],
    completedTask: [],
    incompletedTask: [],
    rating: [],
    ranking: [],
    bonus: [],
    createdAt: [],
    updatedAt: [],
    deletedAt: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected statisticService: StatisticService,
    protected userInformationService: UserInformationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ statistic }) => {
      this.updateForm(statistic);
    });
    this.userInformationService.query({ filter: 'statistic-is-null' }).subscribe(
      (res: HttpResponse<IUserInformation[]>) => {
        if (!this.editForm.get('userId').value) {
          this.users = res.body;
        } else {
          this.userInformationService
            .find(this.editForm.get('userId').value)
            .subscribe(
              (subRes: HttpResponse<IUserInformation>) => (this.users = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  updateForm(statistic: IStatistic) {
    this.editForm.patchValue({
      id: statistic.id,
      level: statistic.level,
      experience: statistic.experience,
      completedTask: statistic.completedTask,
      incompletedTask: statistic.incompletedTask,
      rating: statistic.rating,
      ranking: statistic.ranking,
      bonus: statistic.bonus,
      createdAt: statistic.createdAt,
      updatedAt: statistic.updatedAt,
      deletedAt: statistic.deletedAt,
      userId: statistic.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const statistic = this.createFromForm();
    if (statistic.id !== undefined) {
      this.subscribeToSaveResponse(this.statisticService.update(statistic));
    } else {
      this.subscribeToSaveResponse(this.statisticService.create(statistic));
    }
  }

  private createFromForm(): IStatistic {
    return {
      ...new Statistic(),
      id: this.editForm.get(['id']).value,
      level: this.editForm.get(['level']).value,
      experience: this.editForm.get(['experience']).value,
      completedTask: this.editForm.get(['completedTask']).value,
      incompletedTask: this.editForm.get(['incompletedTask']).value,
      rating: this.editForm.get(['rating']).value,
      ranking: this.editForm.get(['ranking']).value,
      bonus: this.editForm.get(['bonus']).value,
      createdAt: this.editForm.get(['createdAt']).value,
      updatedAt: this.editForm.get(['updatedAt']).value,
      deletedAt: this.editForm.get(['deletedAt']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatistic>>) {
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

  trackUserInformationById(index: number, item: IUserInformation) {
    return item.id;
  }
}
