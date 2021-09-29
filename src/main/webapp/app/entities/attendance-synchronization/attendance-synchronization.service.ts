import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendanceSynchronization } from 'app/shared/model/attendance-synchronization.model';

type EntityResponseType = HttpResponse<IAttendanceSynchronization>;
type EntityArrayResponseType = HttpResponse<IAttendanceSynchronization[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceSynchronizationService {
  public resourceUrl = SERVER_API_URL + 'api/attendance-synchronizations';

  constructor(protected http: HttpClient) {}

  create(attendanceSynchronization: IAttendanceSynchronization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceSynchronization);
    return this.http
      .post<IAttendanceSynchronization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(attendanceSynchronization: IAttendanceSynchronization): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceSynchronization);
    return this.http
      .put<IAttendanceSynchronization>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttendanceSynchronization>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttendanceSynchronization[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(attendanceSynchronization: IAttendanceSynchronization): IAttendanceSynchronization {
    const copy: IAttendanceSynchronization = Object.assign({}, attendanceSynchronization, {
      fromDate:
        attendanceSynchronization.fromDate && attendanceSynchronization.fromDate.isValid()
          ? attendanceSynchronization.fromDate.format(DATE_FORMAT)
          : undefined,
      toDate:
        attendanceSynchronization.toDate && attendanceSynchronization.toDate.isValid()
          ? attendanceSynchronization.toDate.format(DATE_FORMAT)
          : undefined,
      startTime:
        attendanceSynchronization.startTime && attendanceSynchronization.startTime.isValid()
          ? attendanceSynchronization.startTime.toJSON()
          : undefined,
      endTime:
        attendanceSynchronization.endTime && attendanceSynchronization.endTime.isValid()
          ? attendanceSynchronization.endTime.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fromDate = res.body.fromDate ? moment(res.body.fromDate) : undefined;
      res.body.toDate = res.body.toDate ? moment(res.body.toDate) : undefined;
      res.body.startTime = res.body.startTime ? moment(res.body.startTime) : undefined;
      res.body.endTime = res.body.endTime ? moment(res.body.endTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attendanceSynchronization: IAttendanceSynchronization) => {
        attendanceSynchronization.fromDate = attendanceSynchronization.fromDate ? moment(attendanceSynchronization.fromDate) : undefined;
        attendanceSynchronization.toDate = attendanceSynchronization.toDate ? moment(attendanceSynchronization.toDate) : undefined;
        attendanceSynchronization.startTime = attendanceSynchronization.startTime ? moment(attendanceSynchronization.startTime) : undefined;
        attendanceSynchronization.endTime = attendanceSynchronization.endTime ? moment(attendanceSynchronization.endTime) : undefined;
      });
    }
    return res;
  }
}
