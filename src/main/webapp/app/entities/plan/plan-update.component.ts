import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IPlan, Plan } from 'app/shared/model/plan.model';
import { PlanService } from './plan.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';

@Component({
  selector: 'jhi-plan-update',
  templateUrl: './plan-update.component.html'
})
export class PlanUpdateComponent implements OnInit {
  isSaving: boolean;

  tasks: ITask[];
  fromDp: any;
  toDp: any;

  editForm = this.fb.group({
    id: [],
    from: [],
    to: [],
    duration: [],
    taskId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected planService: PlanService,
    protected taskService: TaskService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ plan }) => {
      this.updateForm(plan);
    });
    this.taskService
      .query()
      .subscribe((res: HttpResponse<ITask[]>) => (this.tasks = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(plan: IPlan) {
    this.editForm.patchValue({
      id: plan.id,
      from: plan.from,
      to: plan.to,
      duration: plan.duration,
      taskId: plan.taskId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const plan = this.createFromForm();
    if (plan.id !== undefined) {
      this.subscribeToSaveResponse(this.planService.update(plan));
    } else {
      this.subscribeToSaveResponse(this.planService.create(plan));
    }
  }

  private createFromForm(): IPlan {
    return {
      ...new Plan(),
      id: this.editForm.get(['id']).value,
      from: this.editForm.get(['from']).value,
      to: this.editForm.get(['to']).value,
      duration: this.editForm.get(['duration']).value,
      taskId: this.editForm.get(['taskId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlan>>) {
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

  trackTaskById(index: number, item: ITask) {
    return item.id;
  }
}
