import { Moment } from 'moment';
import { ITask } from 'app/shared/model/task.model';

export interface IMaster {
  id?: number;
  status?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  deletedAt?: Moment;
  userId?: number;
  tasks?: ITask[];
}

export class Master implements IMaster {
  constructor(
    public id?: number,
    public status?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public deletedAt?: Moment,
    public userId?: number,
    public tasks?: ITask[]
  ) {}
}
