import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LeaveBalanceService } from 'app/entities/leave-balance/leave-balance.service';
import { ILeaveBalance, LeaveBalance } from 'app/shared/model/leave-balance.model';
import { LeaveBalanceStatus } from 'app/shared/model/enumerations/leave-balance-status.model';

describe('Service Tests', () => {
  describe('LeaveBalance Service', () => {
    let injector: TestBed;
    let service: LeaveBalanceService;
    let httpMock: HttpTestingController;
    let elemDefault: ILeaveBalance;
    let expectedResult: ILeaveBalance | ILeaveBalance[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LeaveBalanceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new LeaveBalance(0, 0, 0, currentDate, currentDate, 'AAAAAAA', currentDate, LeaveBalanceStatus.ACTIVE, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            lastSynchronizedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a LeaveBalance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            lastSynchronizedOn: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate,
            lastSynchronizedOn: currentDate,
          },
          returnedFromService
        );

        service.create(new LeaveBalance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a LeaveBalance', () => {
        const returnedFromService = Object.assign(
          {
            totalDays: 1,
            remainingDays: 1,
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            remarks: 'BBBBBB',
            lastSynchronizedOn: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            assessmentYear: 1,
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate,
            lastSynchronizedOn: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of LeaveBalance', () => {
        const returnedFromService = Object.assign(
          {
            totalDays: 1,
            remainingDays: 1,
            from: currentDate.format(DATE_FORMAT),
            to: currentDate.format(DATE_FORMAT),
            remarks: 'BBBBBB',
            lastSynchronizedOn: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            assessmentYear: 1,
            amount: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            from: currentDate,
            to: currentDate,
            lastSynchronizedOn: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a LeaveBalance', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
