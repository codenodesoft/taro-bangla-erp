import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Observable, Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';
import { AttendanceSummaryService } from 'app/entities/attendance-summary/attendance-summary.service';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { DesignationService } from 'app/entities/designation/designation.service';
import { Moment } from 'moment';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AttendanceType } from 'app/shared/model/enumerations/attendance-type.model';

@Component({
  selector: 'jhi-attendance-summary-batch-update',
  templateUrl: './attendance-summary-batch-update.component.html',
})
export class AttendanceSummaryBatchUpdateComponent implements OnInit, OnDestroy {
  attendanceSummaries?: IAttendanceSummary[];
  eventSubscriber?: Subscription;

  selectedDate?: string;
  departments?: IDepartment[];
  department?: IDepartment;
  designations?: IDesignation[];
  designation?: IDesignation;
  empId?: string;

  isSaving = false;
  attendanceType?: AttendanceType;

  constructor(
    protected attendanceSummaryService: AttendanceSummaryService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  private static today(): Moment {
    return moment();
  }

  loadAll(): void {
    if (this.empId && this.department && this.designation && this.selectedDate) {
      this.attendanceSummaryService
        .query({
          size: 5000,
          'empId.equals': this.empId,
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
          'attendanceDate.equals': this.selectedDate,
        })
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    } else if (this.empId && this.department && this.selectedDate) {
      this.attendanceSummaryService
        .query({
          size: 5000,
          'empId.equals': this.empId,
          'departmentId.equals': this.department.id,
          'attendanceDate.equals': this.selectedDate,
        })
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    } else if (this.empId && this.designation && this.selectedDate) {
      this.attendanceSummaryService
        .query({
          size: 5000,
          'empId.equals': this.empId,
          'designationId.equals': this.designation.id,
          'attendanceDate.equals': this.selectedDate,
        })
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    } else if (this.department && this.designation && this.selectedDate) {
      this.attendanceSummaryService
        .query({
          size: 5000,
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
          'attendanceDate.equals': this.selectedDate,
        })
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    } else if (this.empId && this.selectedDate) {
      this.attendanceSummaryService
        .query({
          size: 5000,
          'empId.equals': this.empId,
          'attendanceDate.equals': this.selectedDate,
        })
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    } else if (this.department && this.selectedDate) {
      this.attendanceSummaryService
        .query({
          size: 5000,
          'departmentId.equals': this.department.id,
          'attendanceDate.equals': this.selectedDate,
        })
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    } else if (this.designation && this.selectedDate) {
      this.attendanceSummaryService
        .query({
          size: 5000,
          'designationId.equals': this.designation.id,
          'attendanceDate.equals': this.selectedDate,
        })
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    } else if (this.selectedDate) {
      this.attendanceSummaryService
        .query({
          size: 5000,
          'attendanceDate.equals': this.selectedDate,
        })
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    } else {
      this.attendanceSummaryService
        .query({
          size: 10,
        })
        .subscribe((res: HttpResponse<IAttendanceSummary[]>) => (this.attendanceSummaries = res.body || []));
    }
  }

  ngOnInit(): void {
    this.selectedDate = AttendanceSummaryBatchUpdateComponent.today().format(DATE_FORMAT);
    this.departmentService.query({ size: 500 }).subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    this.designationService.query({ size: 500 }).subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
    this.loadAll();
    this.registerChangeInAttendanceSummaryBulks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttendanceSummary): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttendanceSummaryBulks(): void {
    this.eventSubscriber = this.eventManager.subscribe('attendanceSummaryBulkListModification', () => this.loadAll());
  }

  updateAll(): void {
    if (this.attendanceSummaries) {
      if (this.attendanceType) {
        this.isSaving = true;
        this.changeStatus();
        this.subscribeToSaveResponse(this.attendanceSummaryService.batchUpdate(this.attendanceSummaries));
      } else {
        alert('Please select an Attendance Type');
      }
    }
  }

  changeStatus(): void {
    this.attendanceSummaries?.forEach(val => {
      val.attendanceType = this.attendanceType;
    });
  }

  protected subscribeToSaveResponse(results: Observable<HttpResponse<IAttendanceSummary[]>>): void {
    results.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.loadAll();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
