import { Moment } from 'moment';
import { LeaveTypeName } from 'app/shared/model/enumerations/leave-type-name.model';
import { LeaveTypeStatus } from 'app/shared/model/enumerations/leave-type-status.model';

export interface ILeaveType {
  id?: number;
  name?: LeaveTypeName;
  totalDays?: number;
  startDate?: Moment;
  endDate?: Moment;
  status?: LeaveTypeStatus;
}

export class LeaveType implements ILeaveType {
  constructor(
    public id?: number,
    public name?: LeaveTypeName,
    public totalDays?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public status?: LeaveTypeStatus
  ) {}
}
