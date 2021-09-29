import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { ILine } from 'app/shared/model/line.model';
import { IGrade } from 'app/shared/model/grade.model';
import { AttendanceType } from 'app/shared/model/enumerations/attendance-type.model';
import { AttendanceStatus } from 'app/shared/model/enumerations/attendance-status.model';

export interface IAttendanceSummary {
  id?: number;
  inTime?: Moment;
  outTime?: Moment;
  totalHours?: number;
  workingHours?: number;
  overtime?: number;
  attendanceType?: AttendanceType;
  attendanceStatus?: AttendanceStatus;
  remarks?: string;
  attendanceDate?: Moment;
  employee?: IEmployee;
  empId?: string;
  employeeSalary?: IEmployeeSalary;
  department?: IDepartment;
  departmentId?: number;
  designation?: IDesignation;
  designationId?: number;
  line?: ILine;
  grade?: IGrade;
}

export class AttendanceSummary implements IAttendanceSummary {
  constructor(
    public id?: number,
    public inTime?: Moment,
    public outTime?: Moment,
    public totalHours?: number,
    public workingHours?: number,
    public overtime?: number,
    public attendanceType?: AttendanceType,
    public attendanceStatus?: AttendanceStatus,
    public remarks?: string,
    public attendanceDate?: Moment,
    public employee?: IEmployee,
    public empId?: string,
    public employeeSalary?: IEmployeeSalary,
    public department?: IDepartment,
    public departmentId?: number,
    public designation?: IDesignation,
    public designationId?: number,
    public line?: ILine,
    public grade?: IGrade
  ) {}
}
