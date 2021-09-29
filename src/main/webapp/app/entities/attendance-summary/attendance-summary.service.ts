import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';
import { ReportUtil } from 'app/shared/util/report-util';

type EntityResponseType = HttpResponse<IAttendanceSummary>;
type EntityArrayResponseType = HttpResponse<IAttendanceSummary[]>;

@Injectable({ providedIn: 'root' })
export class AttendanceSummaryService {
  public resourceUrl = SERVER_API_URL + 'api/attendance-summaries';

  constructor(protected http: HttpClient) {}

  create(attendanceSummary: IAttendanceSummary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceSummary);
    return this.http
      .post<IAttendanceSummary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(attendanceSummary: IAttendanceSummary): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(attendanceSummary);
    return this.http
      .put<IAttendanceSummary>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  batchUpdate(attendanceSummaries: IAttendanceSummary[]): Observable<EntityArrayResponseType> {
    const copy = this.convertDateArrayFromClient(attendanceSummaries);
    return this.http
      .put<IAttendanceSummary[]>(`${this.resourceUrl}/batch`, copy, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAttendanceSummary>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAttendanceSummary[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(attendanceSummary: IAttendanceSummary): IAttendanceSummary {
    const copy: IAttendanceSummary = Object.assign({}, attendanceSummary, {
      inTime: attendanceSummary.inTime && attendanceSummary.inTime.isValid() ? attendanceSummary.inTime.toJSON() : undefined,
      outTime: attendanceSummary.outTime && attendanceSummary.outTime.isValid() ? attendanceSummary.outTime.toJSON() : undefined,
      attendanceDate:
        attendanceSummary.attendanceDate && attendanceSummary.attendanceDate.isValid()
          ? attendanceSummary.attendanceDate.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateArrayFromClient(attendanceSummaries: IAttendanceSummary[]): IAttendanceSummary[] {
    const copies: IAttendanceSummary[] = [];
    attendanceSummaries.forEach((attendanceSummary: IAttendanceSummary) => {
      const copy: IAttendanceSummary = Object.assign({}, attendanceSummary, {
        inTime: attendanceSummary.inTime && attendanceSummary.inTime.isValid() ? attendanceSummary.inTime.toJSON() : undefined,
        outTime: attendanceSummary.outTime && attendanceSummary.outTime.isValid() ? attendanceSummary.outTime.toJSON() : undefined,
        attendanceDate:
          attendanceSummary.attendanceDate && attendanceSummary.attendanceDate.isValid()
            ? attendanceSummary.attendanceDate.format(DATE_FORMAT)
            : undefined,
      });
      copies.push(copy);
    });
    return copies;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.inTime = res.body.inTime ? moment(res.body.inTime) : undefined;
      res.body.outTime = res.body.outTime ? moment(res.body.outTime) : undefined;
      res.body.attendanceDate = res.body.attendanceDate ? moment(res.body.attendanceDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((attendanceSummary: IAttendanceSummary) => {
        attendanceSummary.inTime = attendanceSummary.inTime ? moment(attendanceSummary.inTime) : undefined;
        attendanceSummary.outTime = attendanceSummary.outTime ? moment(attendanceSummary.outTime) : undefined;
        attendanceSummary.attendanceDate = attendanceSummary.attendanceDate ? moment(attendanceSummary.attendanceDate) : undefined;
      });
    }
    return res;
  }

  downloadDateToDateReport(req?: any): any {
    const options = createRequestOption(req);
    return this.http.get(`${this.resourceUrl}/report/dateToDate`, { params: options, responseType: 'blob' }).subscribe((data: any) => {
      ReportUtil.writeFileContent(data, 'application/pdf', `Attendance Summary Report`);
    });
  }

  downloadDailyReport(req?: any): any {
    const options = createRequestOption(req);
    return this.http.get(`${this.resourceUrl}/report/daily`, { params: options, responseType: 'blob' }).subscribe((data: any) => {
      ReportUtil.writeFileContent(data, 'application/pdf', `Attendance Summary Daily Report`);
    });
  }

  downloadMonthlyReport(monthId: number, yearId: number, req?: any): any {
    const options = createRequestOption(req);
    return this.http
      .get(`${this.resourceUrl}/report/month/monthly/${monthId}/year/${yearId}`, { params: options, responseType: 'blob' })
      .subscribe((data: any) => {
        ReportUtil.writeFileContent(data, 'application/pdf', `Attendance Summary Report (Monthly)`);
      });
  }

  downloadYearlyReport(yearId: number, req?: any): any {
    const options = createRequestOption(req);
    return this.http
      .get(`${this.resourceUrl}/report/yearly/year/${yearId}`, { params: options, responseType: 'blob' })
      .subscribe((data: any) => {
        ReportUtil.writeFileContent(data, 'application/pdf', `Attendance Summary Report (Yearly)`);
      });
  }
}
