import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITasker, Tasker } from 'app/shared/model/tasker.model';
import { TaskerService } from './tasker.service';
import { IUserInformation } from 'app/shared/model/user-information.model';
import { UserInformationService } from 'app/entities/user-information/user-information.service';
import { ITaskCategory } from 'app/shared/model/task-category.model';
import { TaskCategoryService } from 'app/entities/task-category/task-category.service';

@Component({
  selector: 'jhi-tasker-update',
  templateUrl: './tasker-update.component.html'
})
export class TaskerUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUserInformation[];

  taskcategories: ITaskCategory[];
  createdAtDp: any;
  updatedAtDp: any;
  deletedAtDp: any;

  editForm = this.fb.group({
    id: [],
    price: [],
    status: [],
    createdAt: [],
    updatedAt: [],
    deletedAt: [],
    userId: [],
    taskCategories: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected taskerService: TaskerService,
    protected userInformationService: UserInformationService,
    protected taskCategoryService: TaskCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tasker }) => {
      this.updateForm(tasker);
    });
    this.userInformationService.query({ filter: 'tasker-is-null' }).subscribe(
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
    this.taskCategoryService
      .query()
      .subscribe(
        (res: HttpResponse<ITaskCategory[]>) => (this.taskcategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(tasker: ITasker) {
    this.editForm.patchValue({
      id: tasker.id,
      price: tasker.price,
      status: tasker.status,
      createdAt: tasker.createdAt,
      updatedAt: tasker.updatedAt,
      deletedAt: tasker.deletedAt,
      userId: tasker.userId,
      taskCategories: tasker.taskCategories
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tasker = this.createFromForm();
    if (tasker.id !== undefined) {
      this.subscribeToSaveResponse(this.taskerService.update(tasker));
    } else {
      this.subscribeToSaveResponse(this.taskerService.create(tasker));
    }
  }

  private createFromForm(): ITasker {
    return {
      ...new Tasker(),
      id: this.editForm.get(['id']).value,
      price: this.editForm.get(['price']).value,
      status: this.editForm.get(['status']).value,
      createdAt: this.editForm.get(['createdAt']).value,
      updatedAt: this.editForm.get(['updatedAt']).value,
      deletedAt: this.editForm.get(['deletedAt']).value,
      userId: this.editForm.get(['userId']).value,
      taskCategories: this.editForm.get(['taskCategories']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITasker>>) {
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

  trackTaskCategoryById(index: number, item: ITaskCategory) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
