import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { ReportUtil } from 'app/shared/util/report-util';

type EntityResponseType = HttpResponse<ILeaveBalance>;
type EntityArrayResponseType = HttpResponse<ILeaveBalance[]>;

@Injectable({ providedIn: 'root' })
export class LeaveBalanceService {
  public resourceUrl = SERVER_API_URL + 'api/leave-balances';

  constructor(protected http: HttpClient) {}

  create(leaveBalance: ILeaveBalance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveBalance);
    return this.http
      .post<ILeaveBalance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(leaveBalance: ILeaveBalance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveBalance);
    return this.http
      .put<ILeaveBalance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILeaveBalance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILeaveBalance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(leaveBalance: ILeaveBalance): ILeaveBalance {
    const copy: ILeaveBalance = Object.assign({}, leaveBalance, {
      from: leaveBalance.from && leaveBalance.from.isValid() ? leaveBalance.from.format(DATE_FORMAT) : undefined,
      to: leaveBalance.to && leaveBalance.to.isValid() ? leaveBalance.to.format(DATE_FORMAT) : undefined,
      lastSynchronizedOn:
        leaveBalance.lastSynchronizedOn && leaveBalance.lastSynchronizedOn.isValid() ? leaveBalance.lastSynchronizedOn.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.from = res.body.from ? moment(res.body.from) : undefined;
      res.body.to = res.body.to ? moment(res.body.to) : undefined;
      res.body.lastSynchronizedOn = res.body.lastSynchronizedOn ? moment(res.body.lastSynchronizedOn) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((leaveBalance: ILeaveBalance) => {
        leaveBalance.from = leaveBalance.from ? moment(leaveBalance.from) : undefined;
        leaveBalance.to = leaveBalance.to ? moment(leaveBalance.to) : undefined;
        leaveBalance.lastSynchronizedOn = leaveBalance.lastSynchronizedOn ? moment(leaveBalance.lastSynchronizedOn) : undefined;
      });
    }
    return res;
  }

  download(req?: any): any {
    const options = createRequestOption(req);
    return this.http.get(`${this.resourceUrl}/report`, { params: options, responseType: 'blob' }).subscribe((data: any) => {
      ReportUtil.writeFileContent(data, 'application/pdf', `Leave Balance Report`);
    });
  }
}
