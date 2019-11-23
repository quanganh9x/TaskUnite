import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDistrict } from 'app/shared/model/district.model';

type EntityResponseType = HttpResponse<IDistrict>;
type EntityArrayResponseType = HttpResponse<IDistrict[]>;

@Injectable({ providedIn: 'root' })
export class DistrictService {
  public resourceUrl = SERVER_API_URL + 'api/districts';

  constructor(protected http: HttpClient) {}

  create(district: IDistrict): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(district);
    return this.http
      .post<IDistrict>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(district: IDistrict): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(district);
    return this.http
      .put<IDistrict>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDistrict>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDistrict[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(district: IDistrict): IDistrict {
    const copy: IDistrict = Object.assign({}, district, {
      createdAt: district.createdAt != null && district.createdAt.isValid() ? district.createdAt.format(DATE_FORMAT) : null,
      updatedAt: district.updatedAt != null && district.updatedAt.isValid() ? district.updatedAt.format(DATE_FORMAT) : null,
      deletedAt: district.deletedAt != null && district.deletedAt.isValid() ? district.deletedAt.format(DATE_FORMAT) : null
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
      res.body.forEach((district: IDistrict) => {
        district.createdAt = district.createdAt != null ? moment(district.createdAt) : null;
        district.updatedAt = district.updatedAt != null ? moment(district.updatedAt) : null;
        district.deletedAt = district.deletedAt != null ? moment(district.deletedAt) : null;
      });
    }
    return res;
  }
}
