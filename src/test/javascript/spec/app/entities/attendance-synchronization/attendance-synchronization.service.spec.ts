import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AttendanceSynchronizationService } from 'app/entities/attendance-synchronization/attendance-synchronization.service';
import { IAttendanceSynchronization, AttendanceSynchronization } from 'app/shared/model/attendance-synchronization.model';
import { SynchronizationStatus } from 'app/shared/model/enumerations/synchronization-status.model';

describe('Service Tests', () => {
  describe('AttendanceSynchronization Service', () => {
    let injector: TestBed;
    let service: AttendanceSynchronizationService;
    let httpMock: HttpTestingController;
    let elemDefault: IAttendanceSynchronization;
    let expectedResult: IAttendanceSynchronization | IAttendanceSynchronization[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AttendanceSynchronizationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AttendanceSynchronization(0, currentDate, currentDate, currentDate, currentDate, SynchronizationStatus.SUCCESS);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AttendanceSynchronization', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
            startTime: currentDate,
            endTime: currentDate,
          },
          returnedFromService
        );

        service.create(new AttendanceSynchronization()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AttendanceSynchronization', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
            startTime: currentDate,
            endTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AttendanceSynchronization', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_FORMAT),
            toDate: currentDate.format(DATE_FORMAT),
            startTime: currentDate.format(DATE_TIME_FORMAT),
            endTime: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            toDate: currentDate,
            startTime: currentDate,
            endTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AttendanceSynchronization', () => {
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
