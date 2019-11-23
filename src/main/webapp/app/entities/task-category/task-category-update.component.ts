import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITaskCategory, TaskCategory } from 'app/shared/model/task-category.model';
import { TaskCategoryService } from './task-category.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { ITasker } from 'app/shared/model/tasker.model';
import { TaskerService } from 'app/entities/tasker/tasker.service';

@Component({
  selector: 'jhi-task-category-update',
  templateUrl: './task-category-update.component.html'
})
export class TaskCategoryUpdateComponent implements OnInit {
  isSaving: boolean;

  tasks: ITask[];

  taskers: ITasker[];
  createdAtDp: any;
  updatedAtDp: any;
  deletedAtDp: any;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    image: [],
    minPrice: [],
    status: [],
    createdAt: [],
    updatedAt: [],
    deletedAt: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected taskCategoryService: TaskCategoryService,
    protected taskService: TaskService,
    protected taskerService: TaskerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ taskCategory }) => {
      this.updateForm(taskCategory);
    });
    this.taskService
      .query()
      .subscribe((res: HttpResponse<ITask[]>) => (this.tasks = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.taskerService
      .query()
      .subscribe((res: HttpResponse<ITasker[]>) => (this.taskers = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(taskCategory: ITaskCategory) {
    this.editForm.patchValue({
      id: taskCategory.id,
      name: taskCategory.name,
      description: taskCategory.description,
      image: taskCategory.image,
      minPrice: taskCategory.minPrice,
      status: taskCategory.status,
      createdAt: taskCategory.createdAt,
      updatedAt: taskCategory.updatedAt,
      deletedAt: taskCategory.deletedAt
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const taskCategory = this.createFromForm();
    if (taskCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.taskCategoryService.update(taskCategory));
    } else {
      this.subscribeToSaveResponse(this.taskCategoryService.create(taskCategory));
    }
  }

  private createFromForm(): ITaskCategory {
    return {
      ...new TaskCategory(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      image: this.editForm.get(['image']).value,
      minPrice: this.editForm.get(['minPrice']).value,
      status: this.editForm.get(['status']).value,
      createdAt: this.editForm.get(['createdAt']).value,
      updatedAt: this.editForm.get(['updatedAt']).value,
      deletedAt: this.editForm.get(['deletedAt']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskCategory>>) {
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

  trackTaskerById(index: number, item: ITasker) {
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
