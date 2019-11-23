import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStatistic } from 'app/shared/model/statistic.model';

type EntityResponseType = HttpResponse<IStatistic>;
type EntityArrayResponseType = HttpResponse<IStatistic[]>;

@Injectable({ providedIn: 'root' })
export class StatisticService {
  public resourceUrl = SERVER_API_URL + 'api/statistics';

  constructor(protected http: HttpClient) {}

  create(statistic: IStatistic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(statistic);
    return this.http
      .post<IStatistic>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(statistic: IStatistic): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(statistic);
    return this.http
      .put<IStatistic>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStatistic>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStatistic[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(statistic: IStatistic): IStatistic {
    const copy: IStatistic = Object.assign({}, statistic, {
      createdAt: statistic.createdAt != null && statistic.createdAt.isValid() ? statistic.createdAt.format(DATE_FORMAT) : null,
      updatedAt: statistic.updatedAt != null && statistic.updatedAt.isValid() ? statistic.updatedAt.format(DATE_FORMAT) : null,
      deletedAt: statistic.deletedAt != null && statistic.deletedAt.isValid() ? statistic.deletedAt.format(DATE_FORMAT) : null
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
      res.body.forEach((statistic: IStatistic) => {
        statistic.createdAt = statistic.createdAt != null ? moment(statistic.createdAt) : null;
        statistic.updatedAt = statistic.updatedAt != null ? moment(statistic.updatedAt) : null;
        statistic.deletedAt = statistic.deletedAt != null ? moment(statistic.deletedAt) : null;
      });
    }
    return res;
  }
}
