import { Moment } from 'moment';

export interface IPlan {
  id?: number;
  from?: Moment;
  to?: Moment;
  duration?: number;
  taskId?: number;
}

export class Plan implements IPlan {
  constructor(public id?: number, public from?: Moment, public to?: Moment, public duration?: number, public taskId?: number) {}
}
