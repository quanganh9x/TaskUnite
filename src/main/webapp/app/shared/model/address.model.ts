import { Moment } from 'moment';

export interface IAddress {
  id?: number;
  street?: string;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  userId?: number;
  dictrictId?: number;
  taskerId?: number;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public street?: string,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public userId?: number,
    public dictrictId?: number,
    public taskerId?: number
  ) {}
}
