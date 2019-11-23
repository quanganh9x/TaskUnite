import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserInformation } from 'app/shared/model/user-information.model';

type EntityResponseType = HttpResponse<IUserInformation>;
type EntityArrayResponseType = HttpResponse<IUserInformation[]>;

@Injectable({ providedIn: 'root' })
export class UserInformationService {
  public resourceUrl = SERVER_API_URL + 'api/user-informations';

  constructor(protected http: HttpClient) {}

  create(userInformation: IUserInformation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userInformation);
    return this.http
      .post<IUserInformation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userInformation: IUserInformation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userInformation);
    return this.http
      .put<IUserInformation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserInformation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userInformation: IUserInformation): IUserInformation {
    const copy: IUserInformation = Object.assign({}, userInformation, {
      createdAt:
        userInformation.createdAt != null && userInformation.createdAt.isValid() ? userInformation.createdAt.format(DATE_FORMAT) : null,
      updatedAt:
        userInformation.updatedAt != null && userInformation.updatedAt.isValid() ? userInformation.updatedAt.format(DATE_FORMAT) : null,
      deletedAt:
        userInformation.deletedAt != null && userInformation.deletedAt.isValid() ? userInformation.deletedAt.format(DATE_FORMAT) : null
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
      res.body.forEach((userInformation: IUserInformation) => {
        userInformation.createdAt = userInformation.createdAt != null ? moment(userInformation.createdAt) : null;
        userInformation.updatedAt = userInformation.updatedAt != null ? moment(userInformation.updatedAt) : null;
        userInformation.deletedAt = userInformation.deletedAt != null ? moment(userInformation.deletedAt) : null;
      });
    }
    return res;
  }
}
