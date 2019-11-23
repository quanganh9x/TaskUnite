import { Moment } from 'moment';

export interface INotification {
  id?: number;
  content?: string;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  userId?: number;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public content?: string,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public userId?: number
  ) {}
}
