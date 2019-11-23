import { Moment } from 'moment';
import { IAddress } from 'app/shared/model/address.model';

export interface IDistrict {
  id?: number;
  name?: string;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  addresses?: IAddress[];
  cityId?: number;
}

export class District implements IDistrict {
  constructor(
    public id?: number,
    public name?: string,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public addresses?: IAddress[],
    public cityId?: number
  ) {}
}
