import { Moment } from 'moment';

export interface IReview {
  id?: number;
  content?: string;
  point?: number;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  taskId?: number;
  userId?: number;
}

export class Review implements IReview {
  constructor(
    public id?: number,
    public content?: string,
    public point?: number,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public taskId?: number,
    public userId?: number
  ) {}
}
