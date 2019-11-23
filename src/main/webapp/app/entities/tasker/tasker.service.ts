import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITasker } from 'app/shared/model/tasker.model';

type EntityResponseType = HttpResponse<ITasker>;
type EntityArrayResponseType = HttpResponse<ITasker[]>;

@Injectable({ providedIn: 'root' })
export class TaskerService {
  public resourceUrl = SERVER_API_URL + 'api/taskers';

  constructor(protected http: HttpClient) {}

  create(tasker: ITasker): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tasker);
    return this.http
      .post<ITasker>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tasker: ITasker): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tasker);
    return this.http
      .put<ITasker>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITasker>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITasker[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tasker: ITasker): ITasker {
    const copy: ITasker = Object.assign({}, tasker, {
      createdAt: tasker.createdAt != null && tasker.createdAt.isValid() ? tasker.createdAt.format(DATE_FORMAT) : null,
      updatedAt: tasker.updatedAt != null && tasker.updatedAt.isValid() ? tasker.updatedAt.format(DATE_FORMAT) : null,
      deletedAt: tasker.deletedAt != null && tasker.deletedAt.isValid() ? tasker.deletedAt.format(DATE_FORMAT) : null
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
      res.body.forEach((tasker: ITasker) => {
        tasker.createdAt = tasker.createdAt != null ? moment(tasker.createdAt) : null;
        tasker.updatedAt = tasker.updatedAt != null ? moment(tasker.updatedAt) : null;
        tasker.deletedAt = tasker.deletedAt != null ? moment(tasker.deletedAt) : null;
      });
    }
    return res;
  }
}
