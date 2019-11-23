import { Moment } from 'moment';
import { IPlan } from 'app/shared/model/plan.model';
import { IReview } from 'app/shared/model/review.model';
import { ITaskCategory } from 'app/shared/model/task-category.model';

export interface ITask {
  id?: number;
  description?: string;
  price?: number;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  plans?: IPlan[];
  reviews?: IReview[];
  taskCategories?: ITaskCategory[];
  taskerId?: number;
  masterId?: number;
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public description?: string,
    public price?: number,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public plans?: IPlan[],
    public reviews?: IReview[],
    public taskCategories?: ITaskCategory[],
    public taskerId?: number,
    public masterId?: number
  ) {}
}
