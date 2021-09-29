import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';

type EntityResponseType = HttpResponse<IAttendanceDataUpload>;
type EntityArrayResponseType = HttpResponse<IAttendanceDataUpload[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceDataUploadService {
  public resourceUrl = SERVER_API_URL + 'api/attendance-data-uploads';

  constructor(protected http: HttpClient) {}

  create(attendanceDataUpload: IAttendanceDataUpload): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceDataUpload);
    return this.http
      .post<IAttendanceDataUpload>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(attendanceDataUpload: IAttendanceDataUpload): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceDataUpload);
    return this.http
      .put<IAttendanceDataUpload>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttendanceDataUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttendanceDataUpload[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(attendanceDataUpload: IAttendanceDataUpload): IAttendanceDataUpload {
    const copy: IAttendanceDataUpload = Object.assign({}, attendanceDataUpload, {
      createdOn:
        attendanceDataUpload.createdOn && attendanceDataUpload.createdOn.isValid() ? attendanceDataUpload.createdOn.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdOn = res.body.createdOn ? moment(res.body.createdOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attendanceDataUpload: IAttendanceDataUpload) => {
        attendanceDataUpload.createdOn = attendanceDataUpload.createdOn ? moment(attendanceDataUpload.createdOn) : undefined;
      });
    }
    return res;
  }
}
