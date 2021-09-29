import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWeekend } from 'app/shared/model/weekend.model';

type EntityResponseType = HttpResponse<IWeekend>;
type EntityArrayResponseType = HttpResponse<IWeekend[]>;

@Injectable({ providedIn: 'root' })
export class WeekendService {
  public resourceUrl = SERVER_API_URL + 'api/weekends';

  constructor(protected http: HttpClient) {}

  create(weekend: IWeekend): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(weekend);
    return this.http
      .post<IWeekend>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(weekend: IWeekend): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(weekend);
    return this.http
      .put<IWeekend>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWeekend>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWeekend[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(weekend: IWeekend): IWeekend {
    const copy: IWeekend = Object.assign({}, weekend, {
      startDate: weekend.startDate && weekend.startDate.isValid() ? weekend.startDate.format(DATE_FORMAT) : undefined,
      endDate: weekend.endDate && weekend.endDate.isValid() ? weekend.endDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((weekend: IWeekend) => {
        weekend.startDate = weekend.startDate ? moment(weekend.startDate) : undefined;
        weekend.endDate = weekend.endDate ? moment(weekend.endDate) : undefined;
      });
    }
    return res;
  }
}
