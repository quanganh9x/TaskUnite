import { Moment } from 'moment';

export interface IPaymentInformation {
  id?: number;
  balance?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  userId?: number;
}

export class PaymentInformation implements IPaymentInformation {
  constructor(
    public id?: number,
    public balance?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public userId?: number
  ) {}
}
