import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AttendanceSummaryService } from 'app/entities/attendance-summary/attendance-summary.service';
import { IAttendanceSummary, AttendanceSummary } from 'app/shared/model/attendance-summary.model';
import { AttendanceType } from 'app/shared/model/enumerations/attendance-type.model';
import { AttendanceStatus } from 'app/shared/model/enumerations/attendance-status.model';

describe('Service Tests', () => {
  describe('AttendanceSummary Service', () => {
    let injector: TestBed;
    let service: AttendanceSummaryService;
    let httpMock: HttpTestingController;
    let elemDefault: IAttendanceSummary;
    let expectedResult: IAttendanceSummary | IAttendanceSummary[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AttendanceSummaryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AttendanceSummary(
        0,
        currentDate,
        currentDate,
        0,
        0,
        0,
        AttendanceType.WEEKDAY,
        AttendanceStatus.PRESENT,
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            inTime: currentDate.format(DATE_TIME_FORMAT),
            outTime: currentDate.format(DATE_TIME_FORMAT),
            attendanceDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AttendanceSummary', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            inTime: currentDate.format(DATE_TIME_FORMAT),
            outTime: currentDate.format(DATE_TIME_FORMAT),
            attendanceDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inTime: currentDate,
            outTime: currentDate,
            attendanceDate: currentDate,
          },
          returnedFromService
        );

        service.create(new AttendanceSummary()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AttendanceSummary', () => {
        const returnedFromService = Object.assign(
          {
            inTime: currentDate.format(DATE_TIME_FORMAT),
            outTime: currentDate.format(DATE_TIME_FORMAT),
            totalHours: 'BBBBBB',
            workingHours: 'BBBBBB',
            overtime: 'BBBBBB',
            attendanceType: 'BBBBBB',
            attendanceStatus: 'BBBBBB',
            remarks: 'BBBBBB',
            attendanceDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inTime: currentDate,
            outTime: currentDate,
            attendanceDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AttendanceSummary', () => {
        const returnedFromService = Object.assign(
          {
            inTime: currentDate.format(DATE_TIME_FORMAT),
            outTime: currentDate.format(DATE_TIME_FORMAT),
            totalHours: 'BBBBBB',
            workingHours: 'BBBBBB',
            overtime: 'BBBBBB',
            attendanceType: 'BBBBBB',
            attendanceStatus: 'BBBBBB',
            remarks: 'BBBBBB',
            attendanceDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            inTime: currentDate,
            outTime: currentDate,
            attendanceDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AttendanceSummary', () => {
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
