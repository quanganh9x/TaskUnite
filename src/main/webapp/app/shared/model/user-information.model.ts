import { Moment } from 'moment';
import { IReview } from 'app/shared/model/review.model';
import { INotification } from 'app/shared/model/notification.model';

export interface IUserInformation {
  id?: number;
  address?: string;
  phone?: string;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  userId?: number;
  reviews?: IReview[];
  notifications?: INotification[];
  taskerId?: number;
  masterId?: number;
  addressId?: number;
  statisticId?: number;
}

export class UserInformation implements IUserInformation {
  constructor(
    public id?: number,
    public address?: string,
    public phone?: string,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public userId?: number,
    public reviews?: IReview[],
    public notifications?: INotification[],
    public taskerId?: number,
    public masterId?: number,
    public addressId?: number,
    public statisticId?: number
  ) {}
}
