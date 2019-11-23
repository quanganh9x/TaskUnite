import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentInformation } from 'app/shared/model/payment-information.model';

type EntityResponseType = HttpResponse<IPaymentInformation>;
type EntityArrayResponseType = HttpResponse<IPaymentInformation[]>;

@Injectable({ providedIn: 'root' })
export class PaymentInformationService {
  public resourceUrl = SERVER_API_URL + 'api/payment-informations';

  constructor(protected http: HttpClient) {}

  create(paymentInformation: IPaymentInformation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentInformation);
    return this.http
      .post<IPaymentInformation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paymentInformation: IPaymentInformation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentInformation);
    return this.http
      .put<IPaymentInformation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaymentInformation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentInformation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paymentInformation: IPaymentInformation): IPaymentInformation {
    const copy: IPaymentInformation = Object.assign({}, paymentInformation, {
      createdAt:
        paymentInformation.createdAt != null && paymentInformation.createdAt.isValid()
          ? paymentInformation.createdAt.format(DATE_FORMAT)
          : null,
      updatedAt:
        paymentInformation.updatedAt != null && paymentInformation.updatedAt.isValid()
          ? paymentInformation.updatedAt.format(DATE_FORMAT)
          : null,
      deletedAt:
        paymentInformation.deletedAt != null && paymentInformation.deletedAt.isValid()
          ? paymentInformation.deletedAt.format(DATE_FORMAT)
          : null
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
      res.body.forEach((paymentInformation: IPaymentInformation) => {
        paymentInformation.createdAt = paymentInformation.createdAt != null ? moment(paymentInformation.createdAt) : null;
        paymentInformation.updatedAt = paymentInformation.updatedAt != null ? moment(paymentInformation.updatedAt) : null;
        paymentInformation.deletedAt = paymentInformation.deletedAt != null ? moment(paymentInformation.deletedAt) : null;
      });
    }
    return res;
  }
}
