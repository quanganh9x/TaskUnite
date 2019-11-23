import { Moment } from 'moment';

export interface ISchedule {
  id?: number;
  from?: Moment;
  to?: Moment;
  duration?: number;
  taskerId?: number;
}

export class Schedule implements ISchedule {
  constructor(public id?: number, public from?: Moment, public to?: Moment, public duration?: number, public taskerId?: number) {}
}
