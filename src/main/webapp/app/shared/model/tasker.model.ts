import { Moment } from 'moment';
import { ISchedule } from 'app/shared/model/schedule.model';
import { IAddress } from 'app/shared/model/address.model';
import { ITask } from 'app/shared/model/task.model';
import { ITaskCategory } from 'app/shared/model/task-category.model';

export interface ITasker {
  id?: number;
  price?: number;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  userId?: number;
  schedules?: ISchedule[];
  workingAddresses?: IAddress[];
  tasks?: ITask[];
  taskCategories?: ITaskCategory[];
}

export class Tasker implements ITasker {
  constructor(
    public id?: number,
    public price?: number,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public userId?: number,
    public schedules?: ISchedule[],
    public workingAddresses?: IAddress[],
    public tasks?: ITask[],
    public taskCategories?: ITaskCategory[]
  ) {}
}
