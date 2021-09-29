import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AttendanceService } from 'app/entities/attendance/attendance.service';
import { IAttendance, Attendance } from 'app/shared/model/attendance.model';

describe('Service Tests', () => {
  describe('Attendance Service', () => {
    let injector: TestBed;
    let service: AttendanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IAttendance;
    let expectedResult: IAttendance | IAttendance[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AttendanceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Attendance(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            attendanceDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Attendance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            attendanceDateTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            attendanceDateTime: currentDate,
          },
          returnedFromService
        );

        service.create(new Attendance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Attendance', () => {
        const returnedFromService = Object.assign(
          {
            machineNo: 'BBBBBB',
            employeeMachineId: 'BBBBBB',
            attendanceDateTime: currentDate.format(DATE_TIME_FORMAT),
            remarks: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            attendanceDateTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Attendance', () => {
        const returnedFromService = Object.assign(
          {
            machineNo: 'BBBBBB',
            employeeMachineId: 'BBBBBB',
            attendanceDateTime: currentDate.format(DATE_TIME_FORMAT),
            remarks: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            attendanceDateTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Attendance', () => {
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
