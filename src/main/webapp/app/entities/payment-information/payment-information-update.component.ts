import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPaymentInformation, PaymentInformation } from 'app/shared/model/payment-information.model';
import { PaymentInformationService } from './payment-information.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-payment-information-update',
  templateUrl: './payment-information-update.component.html'
})
export class PaymentInformationUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    balance: [],
    createdAt: [],
    updatedAt: [],
    deletedAt: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected paymentInformationService: PaymentInformationService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ paymentInformation }) => {
      this.updateForm(paymentInformation);
    });
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(paymentInformation: IPaymentInformation) {
    this.editForm.patchValue({
      id: paymentInformation.id,
      balance: paymentInformation.balance,
      createdAt: paymentInformation.createdAt != null ? paymentInformation.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: paymentInformation.updatedAt != null ? paymentInformation.updatedAt.format(DATE_TIME_FORMAT) : null,
      deletedAt: paymentInformation.deletedAt != null ? paymentInformation.deletedAt.format(DATE_TIME_FORMAT) : null,
      userId: paymentInformation.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const paymentInformation = this.createFromForm();
    if (paymentInformation.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentInformationService.update(paymentInformation));
    } else {
      this.subscribeToSaveResponse(this.paymentInformationService.create(paymentInformation));
    }
  }

  private createFromForm(): IPaymentInformation {
    return {
      ...new PaymentInformation(),
      id: this.editForm.get(['id']).value,
      balance: this.editForm.get(['balance']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      deletedAt:
        this.editForm.get(['deletedAt']).value != null ? moment(this.editForm.get(['deletedAt']).value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentInformation>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
