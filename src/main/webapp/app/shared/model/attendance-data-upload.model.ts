import { Moment } from 'moment';

export interface IAttendanceDataUpload {
  id?: number;
  fileUploadContentType?: string;
  fileUpload?: any;
  createdOn?: Moment;
}

export class AttendanceDataUpload implements IAttendanceDataUpload {
  constructor(public id?: number, public fileUploadContentType?: string, public fileUpload?: any, public createdOn?: Moment) {}
}
