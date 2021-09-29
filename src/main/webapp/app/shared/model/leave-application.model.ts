import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { LeaveApplicationStatus } from 'app/shared/model/enumerations/leave-application-status.model';

export interface ILeaveApplication {
  id?: number;
  from?: Moment;
  to?: Moment;
  totalDays?: number;
  reason?: any;
  attachmentContentType?: string;
  attachment?: any;
  status?: LeaveApplicationStatus;
  appliedBy?: string;
  appliedOn?: Moment;
  actionTakenBy?: string;
  actionTakenOn?: Moment;
  employee?: IEmployee;
  empId?: string;
  leaveType?: ILeaveType;
  department?: IDepartment;
  departmentId?: number;
  designation?: IDesignation;
  designationId?: number;
}

export class LeaveApplication implements ILeaveApplication {
  constructor(
    public id?: number,
    public from?: Moment,
    public to?: Moment,
    public totalDays?: number,
    public reason?: any,
    public attachmentContentType?: string,
    public attachment?: any,
    public status?: LeaveApplicationStatus,
    public appliedBy?: string,
    public appliedOn?: Moment,
    public actionTakenBy?: string,
    public actionTakenOn?: Moment,
    public employee?: IEmployee,
    public empId?: string,
    public leaveType?: ILeaveType,
    public department?: IDepartment,
    public departmentId?: number,
    public designation?: IDesignation,
    public designationId?: number
  ) {}
}
