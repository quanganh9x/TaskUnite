import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISchedule, Schedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';
import { ITasker } from 'app/shared/model/tasker.model';
import { TaskerService } from 'app/entities/tasker/tasker.service';

@Component({
  selector: 'jhi-schedule-update',
  templateUrl: './schedule-update.component.html'
})
export class ScheduleUpdateComponent implements OnInit {
  isSaving: boolean;

  taskers: ITasker[];
  fromDp: any;
  toDp: any;

  editForm = this.fb.group({
    id: [],
    from: [],
    to: [],
    duration: [],
    taskerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected scheduleService: ScheduleService,
    protected taskerService: TaskerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ schedule }) => {
      this.updateForm(schedule);
    });
    this.taskerService
      .query()
      .subscribe((res: HttpResponse<ITasker[]>) => (this.taskers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(schedule: ISchedule) {
    this.editForm.patchValue({
      id: schedule.id,
      from: schedule.from,
      to: schedule.to,
      duration: schedule.duration,
      taskerId: schedule.taskerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const schedule = this.createFromForm();
    if (schedule.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleService.update(schedule));
    } else {
      this.subscribeToSaveResponse(this.scheduleService.create(schedule));
    }
  }

  private createFromForm(): ISchedule {
    return {
      ...new Schedule(),
      id: this.editForm.get(['id']).value,
      from: this.editForm.get(['from']).value,
      to: this.editForm.get(['to']).value,
      duration: this.editForm.get(['duration']).value,
      taskerId: this.editForm.get(['taskerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchedule>>) {
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

  trackTaskerById(index: number, item: ITasker) {
    return item.id;
  }
}
