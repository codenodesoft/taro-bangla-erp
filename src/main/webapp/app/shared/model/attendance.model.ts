import { Moment } from 'moment';
import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';

export interface IAttendance {
  id?: number;
  machineNo?: string;
  employeeMachineId?: string;
  attendanceDateTime?: Moment;
  remarks?: string;
  attendanceDataUpload?: IAttendanceDataUpload;
}

export class Attendance implements IAttendance {
  constructor(
    public id?: number,
    public machineNo?: string,
    public employeeMachineId?: string,
    public attendanceDateTime?: Moment,
    public remarks?: string,
    public attendanceDataUpload?: IAttendanceDataUpload
  ) {}
}
