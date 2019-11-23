import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaskCategory } from 'app/shared/model/task-category.model';

type EntityResponseType = HttpResponse<ITaskCategory>;
type EntityArrayResponseType = HttpResponse<ITaskCategory[]>;

@Injectable({ providedIn: 'root' })
export class TaskCategoryService {
  public resourceUrl = SERVER_API_URL + 'api/task-categories';

  constructor(protected http: HttpClient) {}

  create(taskCategory: ITaskCategory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskCategory);
    return this.http
      .post<ITaskCategory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskCategory: ITaskCategory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskCategory);
    return this.http
      .put<ITaskCategory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskCategory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taskCategory: ITaskCategory): ITaskCategory {
    const copy: ITaskCategory = Object.assign({}, taskCategory, {
      createdAt: taskCategory.createdAt != null && taskCategory.createdAt.isValid() ? taskCategory.createdAt.format(DATE_FORMAT) : null,
      updatedAt: taskCategory.updatedAt != null && taskCategory.updatedAt.isValid() ? taskCategory.updatedAt.format(DATE_FORMAT) : null,
      deletedAt: taskCategory.deletedAt != null && taskCategory.deletedAt.isValid() ? taskCategory.deletedAt.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
      res.body.deletedAt = res.body.deletedAt != null ? moment(res.body.deletedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((taskCategory: ITaskCategory) => {
        taskCategory.createdAt = taskCategory.createdAt != null ? moment(taskCategory.createdAt) : null;
        taskCategory.updatedAt = taskCategory.updatedAt != null ? moment(taskCategory.updatedAt) : null;
        taskCategory.deletedAt = taskCategory.deletedAt != null ? moment(taskCategory.deletedAt) : null;
      });
    }
    return res;
  }
}
