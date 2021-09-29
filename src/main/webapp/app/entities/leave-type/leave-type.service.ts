import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILeaveType } from 'app/shared/model/leave-type.model';

type EntityResponseType = HttpResponse<ILeaveType>;
type EntityArrayResponseType = HttpResponse<ILeaveType[]>;

@Injectable({ providedIn: 'root' })
export class LeaveTypeService {
  public resourceUrl = SERVER_API_URL + 'api/leave-types';

  constructor(protected http: HttpClient) {}

  create(leaveType: ILeaveType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveType);
    return this.http
      .post<ILeaveType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(leaveType: ILeaveType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(leaveType);
    return this.http
      .put<ILeaveType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILeaveType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILeaveType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(leaveType: ILeaveType): ILeaveType {
    const copy: ILeaveType = Object.assign({}, leaveType, {
      startDate: leaveType.startDate && leaveType.startDate.isValid() ? leaveType.startDate.format(DATE_FORMAT) : undefined,
      endDate: leaveType.endDate && leaveType.endDate.isValid() ? leaveType.endDate.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((leaveType: ILeaveType) => {
        leaveType.startDate = leaveType.startDate ? moment(leaveType.startDate) : undefined;
        leaveType.endDate = leaveType.endDate ? moment(leaveType.endDate) : undefined;
      });
    }
    return res;
  }
}
