import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReview, Review } from 'app/shared/model/review.model';
import { ReviewService } from './review.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { IUserInformation } from 'app/shared/model/user-information.model';
import { UserInformationService } from 'app/entities/user-information/user-information.service';

@Component({
  selector: 'jhi-review-update',
  templateUrl: './review-update.component.html'
})
export class ReviewUpdateComponent implements OnInit {
  isSaving: boolean;

  tasks: ITask[];

  userinformations: IUserInformation[];
  createdAtDp: any;
  updatedAtDp: any;
  deletedAtDp: any;

  editForm = this.fb.group({
    id: [],
    content: [],
    point: [],
    status: [],
    createdAt: [],
    updatedAt: [],
    deletedAt: [],
    taskId: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected reviewService: ReviewService,
    protected taskService: TaskService,
    protected userInformationService: UserInformationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ review }) => {
      this.updateForm(review);
    });
    this.taskService
      .query()
      .subscribe((res: HttpResponse<ITask[]>) => (this.tasks = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.userInformationService
      .query()
      .subscribe(
        (res: HttpResponse<IUserInformation[]>) => (this.userinformations = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(review: IReview) {
    this.editForm.patchValue({
      id: review.id,
      content: review.content,
      point: review.point,
      status: review.status,
      createdAt: review.createdAt,
      updatedAt: review.updatedAt,
      deletedAt: review.deletedAt,
      taskId: review.taskId,
      userId: review.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const review = this.createFromForm();
    if (review.id !== undefined) {
      this.subscribeToSaveResponse(this.reviewService.update(review));
    } else {
      this.subscribeToSaveResponse(this.reviewService.create(review));
    }
  }

  private createFromForm(): IReview {
    return {
      ...new Review(),
      id: this.editForm.get(['id']).value,
      content: this.editForm.get(['content']).value,
      point: this.editForm.get(['point']).value,
      status: this.editForm.get(['status']).value,
      createdAt: this.editForm.get(['createdAt']).value,
      updatedAt: this.editForm.get(['updatedAt']).value,
      deletedAt: this.editForm.get(['deletedAt']).value,
      taskId: this.editForm.get(['taskId']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReview>>) {
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

  trackUserInformationById(index: number, item: IUserInformation) {
    return item.id;
  }
}
