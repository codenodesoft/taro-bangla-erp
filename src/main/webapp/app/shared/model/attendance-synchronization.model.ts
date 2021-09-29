import { Moment } from 'moment';
import { SynchronizationStatus } from 'app/shared/model/enumerations/synchronization-status.model';

export interface IAttendanceSynchronization {
  id?: number;
  fromDate?: Moment;
  toDate?: Moment;
  startTime?: Moment;
  endTime?: Moment;
  status?: SynchronizationStatus;
}

export class AttendanceSynchronization implements IAttendanceSynchronization {
  constructor(
    public id?: number,
    public fromDate?: Moment,
    public toDate?: Moment,
    public startTime?: Moment,
    public endTime?: Moment,
    public status?: SynchronizationStatus
  ) {}
}
