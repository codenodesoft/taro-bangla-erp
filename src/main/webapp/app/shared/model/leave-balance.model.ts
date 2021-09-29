import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { LeaveBalanceStatus } from 'app/shared/model/enumerations/leave-balance-status.model';

export interface ILeaveBalance {
  id?: number;
  totalDays?: number;
  remainingDays?: number;
  from?: Moment;
  to?: Moment;
  remarks?: string;
  lastSynchronizedOn?: Moment;
  status?: LeaveBalanceStatus;
  assessmentYear?: number;
  amount?: number;
  employee?: IEmployee;
  empId?: string;
  leaveType?: ILeaveType;
  department?: IDepartment;
  departmentId?: number;
  designation?: IDesignation;
  designationId?: number;
}

export class LeaveBalance implements ILeaveBalance {
  constructor(
    public id?: number,
    public totalDays?: number,
    public remainingDays?: number,
    public from?: Moment,
    public to?: Moment,
    public remarks?: string,
    public lastSynchronizedOn?: Moment,
    public status?: LeaveBalanceStatus,
    public assessmentYear?: number,
    public amount?: number,
    public employee?: IEmployee,
    public empId?: string,
    public leaveType?: ILeaveType,
    public department?: IDepartment,
    public departmentId?: number,
    public designation?: IDesignation,
    public designationId?: number
  ) {}
}
