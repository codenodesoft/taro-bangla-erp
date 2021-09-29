import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AttendanceSummaryService } from './attendance-summary.service';
import * as moment from 'moment';
import { Moment } from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import { IConstant } from 'app/shared/model/constant.model';
import { MONTHS, YEARS } from 'app/shared/constants/common.constants';
import { AttendanceStatus } from 'app/shared/model/enumerations/attendance-status.model';

@Component({
  selector: 'jhi-attendance-summary-report',
  templateUrl: './attendance-summary-report.component.html',
})
export class AttendanceSummaryReportComponent implements OnInit {
  eventSubscriber?: Subscription;

  selectedDate?: string;
  fromDate?: string;
  toDate?: string;
  departments?: IDepartment[];
  department?: IDepartment;
  designations?: IDesignation[];
  designation?: IDesignation;
  empId?: string;

  years: IConstant[] = [];
  year?: IConstant;

  months: IConstant[] = [];
  month?: IConstant;

  status?: AttendanceStatus;

  constructor(
    protected attendanceSummaryService: AttendanceSummaryService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.status = AttendanceStatus.PRESENT;
  }

  private static today(): Moment {
    return moment();
  }

  ngOnInit(): void {
    const date = new Date();
    this.years = YEARS(2000, date.getFullYear());
    this.months = MONTHS;
    this.year = this.years[this.years.length - 1];
    this.month = this.months[date.getMonth() + 1];
    this.toDate = AttendanceSummaryReportComponent.today().format(DATE_FORMAT);
    this.fromDate = AttendanceSummaryReportComponent.today().format(DATE_FORMAT);
    this.selectedDate = AttendanceSummaryReportComponent.today().format(DATE_FORMAT);
    this.departmentService.query({ size: 500 }).subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    this.designationService.query({ size: 500 }).subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
  }

  downloadDateToDateReport(): void {
    if (this.fromDate && this.toDate && this.department && this.designation && this.empId) {
      this.attendanceSummaryService.downloadDateToDateReport({
        'attendanceDate.greaterOrEqualThan': this.fromDate,
        'attendanceDate.lessOrEqualThan': this.toDate,
        'departmentId.equals': this.department.id,
        'designationId.equals': this.designation.id,
        'empId.equals': this.empId,
      });
    } else if (this.fromDate && this.toDate && this.department && this.designation) {
      this.attendanceSummaryService.downloadDateToDateReport({
        'attendanceDate.greaterOrEqualThan': this.fromDate,
        'attendanceDate.lessOrEqualThan': this.toDate,
        'departmentId.equals': this.department.id,
        'designationId.equals': this.designation.id,
      });
    } else if (this.fromDate && this.toDate && this.department && this.empId) {
      this.attendanceSummaryService.downloadDateToDateReport({
        'attendanceDate.greaterOrEqualThan': this.fromDate,
        'attendanceDate.lessOrEqualThan': this.toDate,
        'departmentId.equals': this.department.id,
        'empId.equals': this.empId,
      });
    } else if (this.fromDate && this.toDate && this.designation && this.empId) {
      this.attendanceSummaryService.downloadDateToDateReport({
        'attendanceDate.greaterOrEqualThan': this.fromDate,
        'attendanceDate.lessOrEqualThan': this.toDate,
        'designationId.equals': this.designation.id,
        'empId.equals': this.empId,
      });
    } else if (this.fromDate && this.toDate && this.department) {
      this.attendanceSummaryService.downloadDateToDateReport({
        'attendanceDate.greaterOrEqualThan': this.fromDate,
        'attendanceDate.lessOrEqualThan': this.toDate,
        'departmentId.equals': this.department.id,
      });
    } else if (this.fromDate && this.toDate && this.designation) {
      this.attendanceSummaryService.downloadDateToDateReport({
        'attendanceDate.greaterOrEqualThan': this.fromDate,
        'attendanceDate.lessOrEqualThan': this.toDate,
        'designationId.equals': this.designation.id,
      });
    } else if (this.fromDate && this.toDate && this.empId) {
      this.attendanceSummaryService.downloadDateToDateReport({
        'attendanceDate.greaterOrEqualThan': this.fromDate,
        'attendanceDate.lessOrEqualThan': this.toDate,
        'empId.equals': this.empId,
      });
    } else if (this.fromDate && this.toDate) {
      this.attendanceSummaryService.downloadDateToDateReport({
        'attendanceDate.greaterOrEqualThan': this.fromDate,
        'attendanceDate.lessOrEqualThan': this.toDate,
      });
    } else {
      alert('Please fill from date and to date and emp id');
    }
  }

  downloadMonthlyReport(): void {
    if (this.month && this.year) {
      if (this.month.id && this.year.id) {
        if (this.month.id !== 0) {
          if (this.department && this.designation && this.empId) {
            this.attendanceSummaryService.downloadMonthlyReport(this.month.id, this.year.id, {
              'departmentId.equals': this.department.id,
              'designationId.equals': this.designation.id,
              'empId.equals': this.empId,
            });
          } else if (this.department && this.designation) {
            this.attendanceSummaryService.downloadMonthlyReport(this.month.id, this.year.id, {
              'departmentId.equals': this.department.id,
              'designationId.equals': this.designation.id,
            });
          } else if (this.department && this.empId) {
            this.attendanceSummaryService.downloadMonthlyReport(this.month.id, this.year.id, {
              'departmentId.equals': this.department.id,
              'empId.equals': this.empId,
            });
          } else if (this.designation && this.empId) {
            this.attendanceSummaryService.downloadMonthlyReport(this.month.id, this.year.id, {
              'designationId.equals': this.designation.id,
              'empId.equals': this.empId,
            });
          } else if (this.department) {
            this.attendanceSummaryService.downloadMonthlyReport(this.month.id, this.year.id, {
              'departmentId.equals': this.department.id,
            });
          } else if (this.designation) {
            this.attendanceSummaryService.downloadMonthlyReport(this.month.id, this.year.id, {
              'designationId.equals': this.designation.id,
            });
          } else if (this.empId) {
            this.attendanceSummaryService.downloadMonthlyReport(this.month.id, this.year.id, {
              'empId.equals': this.empId,
            });
          } else {
            this.attendanceSummaryService.downloadMonthlyReport(this.month.id, this.year.id, {});
          }
        } else {
          alert('Please select a month');
        }
      } else {
        alert('Please fill from properly');
      }
    } else {
      alert('Please fill from properly');
    }
  }

  downloadYearlyReport(): void {
    if (this.year && this.year.id) {
      if (this.department && this.designation && this.empId) {
        this.attendanceSummaryService.downloadYearlyReport(this.year.id, {
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
          'empId.equals': this.empId,
        });
      } else if (this.department && this.designation) {
        this.attendanceSummaryService.downloadYearlyReport(this.year.id, {
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
        });
      } else if (this.department && this.empId) {
        this.attendanceSummaryService.downloadYearlyReport(this.year.id, {
          'departmentId.equals': this.department.id,
          'empId.equals': this.empId,
        });
      } else if (this.designation && this.empId) {
        this.attendanceSummaryService.downloadYearlyReport(this.year.id, {
          'designationId.equals': this.designation.id,
          'empId.equals': this.empId,
        });
      } else if (this.department) {
        this.attendanceSummaryService.downloadYearlyReport(this.year.id, {
          'departmentId.equals': this.department.id,
        });
      } else if (this.designation) {
        this.attendanceSummaryService.downloadYearlyReport(this.year.id, {
          'designationId.equals': this.designation.id,
        });
      } else if (this.empId) {
        this.attendanceSummaryService.downloadYearlyReport(this.year.id, {
          'empId.equals': this.empId,
        });
      } else {
        this.attendanceSummaryService.downloadYearlyReport(this.year.id, {
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        });
      }
    } else {
      alert('Please select a year');
    }
  }

  downloadDailyReport(): void {
    if (this.selectedDate && this.status) {
      if (this.department && this.designation && this.empId) {
        this.attendanceSummaryService.downloadDailyReport({
          'attendanceDate.equals': this.selectedDate,
          'attendanceStatus.equals': this.status,
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
          'empId.equals': this.empId,
        });
      } else if (this.department && this.designation) {
        this.attendanceSummaryService.downloadDailyReport({
          'attendanceDate.equals': this.selectedDate,
          'attendanceStatus.equals': this.status,
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
        });
      } else if (this.department && this.empId) {
        this.attendanceSummaryService.downloadDailyReport({
          'attendanceDate.equals': this.selectedDate,
          'attendanceStatus.equals': this.status,
          'departmentId.equals': this.department.id,
          'empId.equals': this.empId,
        });
      } else if (this.designation && this.empId) {
        this.attendanceSummaryService.downloadDailyReport({
          'attendanceDate.equals': this.selectedDate,
          'attendanceStatus.equals': this.status,
          'designationId.equals': this.designation.id,
          'empId.equals': this.empId,
        });
      } else if (this.department) {
        this.attendanceSummaryService.downloadDailyReport({
          'attendanceDate.equals': this.selectedDate,
          'attendanceStatus.equals': this.status,
          'departmentId.equals': this.department.id,
        });
      } else if (this.designation) {
        this.attendanceSummaryService.downloadDailyReport({
          'attendanceDate.equals': this.selectedDate,
          'attendanceStatus.equals': this.status,
          'designationId.equals': this.designation.id,
        });
      } else if (this.empId) {
        this.attendanceSummaryService.downloadDailyReport({
          'attendanceDate.equals': this.selectedDate,
          'attendanceStatus.equals': this.status,
          'empId.equals': this.empId,
        });
      } else {
        this.attendanceSummaryService.downloadDailyReport({
          'attendanceDate.equals': this.selectedDate,
          'attendanceStatus.equals': this.status,
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        });
      }
    } else {
      alert('Please fill up the form properly');
    }
  }
}
