import { Moment } from 'moment';
import { IDistrict } from 'app/shared/model/district.model';

export interface ICity {
  id?: number;
  name?: string;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  districts?: IDistrict[];
}

export class City implements ICity {
  constructor(
    public id?: number,
    public name?: string,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public districts?: IDistrict[]
  ) {}
}
