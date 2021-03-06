import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMaster } from 'app/shared/model/master.model';

type EntityResponseType = HttpResponse<IMaster>;
type EntityArrayResponseType = HttpResponse<IMaster[]>;

@Injectable({ providedIn: 'root' })
export class MasterService {
  public resourceUrl = SERVER_API_URL + 'api/masters';

  constructor(protected http: HttpClient) {}

  create(master: IMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(master);
    return this.http
      .post<IMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(master: IMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(master);
    return this.http
      .put<IMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(master: IMaster): IMaster {
    const copy: IMaster = Object.assign({}, master, {
      createdAt: master.createdAt != null && master.createdAt.isValid() ? master.createdAt.toJSON() : null,
      updatedAt: master.updatedAt != null && master.updatedAt.isValid() ? master.updatedAt.toJSON() : null,
      deletedAt: master.deletedAt != null && master.deletedAt.isValid() ? master.deletedAt.toJSON() : null
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
      res.body.forEach((master: IMaster) => {
        master.createdAt = master.createdAt != null ? moment(master.createdAt) : null;
        master.updatedAt = master.updatedAt != null ? moment(master.updatedAt) : null;
        master.deletedAt = master.deletedAt != null ? moment(master.deletedAt) : null;
      });
    }
    return res;
  }
}
