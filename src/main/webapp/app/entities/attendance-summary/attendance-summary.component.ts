import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AttendanceSummaryService } from './attendance-summary.service';
import { AttendanceSummaryDeleteDialogComponent } from './attendance-summary-delete-dialog.component';
import { Moment } from 'moment';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { DatePipe } from '@angular/common';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';

@Component({
  selector: 'jhi-attendance-summary',
  templateUrl: './attendance-summary.component.html',
})
export class AttendanceSummaryComponent implements OnInit, OnDestroy {
  attendanceSummaries?: IAttendanceSummary[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  fromDate?: string;
  toDate?: string;
  departments?: IDepartment[];
  department?: IDepartment;
  designations?: IDesignation[];
  designation?: IDesignation;
  empId?: string;

  constructor(
    protected attendanceSummaryService: AttendanceSummaryService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  private static today(): Moment {
    return moment();
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if (this.empId && this.department && this.designation && this.fromDate && this.toDate) {
      this.attendanceSummaryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'empId.equals': this.empId,
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<IAttendanceSummary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.empId && this.department && this.fromDate && this.toDate) {
      this.attendanceSummaryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'empId.equals': this.empId,
          'departmentId.equals': this.department.id,
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<IAttendanceSummary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.empId && this.designation && this.fromDate && this.toDate) {
      this.attendanceSummaryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'empId.equals': this.empId,
          'designationId.equals': this.designation.id,
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<IAttendanceSummary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.department && this.designation && this.fromDate && this.toDate) {
      this.attendanceSummaryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<IAttendanceSummary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.empId && this.fromDate && this.toDate) {
      this.attendanceSummaryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'empId.equals': this.empId,
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<IAttendanceSummary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.department && this.fromDate && this.toDate) {
      this.attendanceSummaryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'departmentId.equals': this.department.id,
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<IAttendanceSummary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.designation && this.fromDate && this.toDate) {
      this.attendanceSummaryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'designationId.equals': this.designation.id,
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<IAttendanceSummary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.fromDate && this.toDate) {
      this.attendanceSummaryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'attendanceDate.greaterOrEqualThan': this.fromDate,
          'attendanceDate.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<IAttendanceSummary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else {
      this.attendanceSummaryService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<IAttendanceSummary[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.toDate = AttendanceSummaryComponent.today().format(DATE_FORMAT);
    this.fromDate = AttendanceSummaryComponent.today().format(DATE_FORMAT);
    this.departmentService.query({ size: 500 }).subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    this.designationService.query({ size: 500 }).subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
    this.handleNavigation();
    this.registerChangeInAttendanceSummaries();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
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

  registerChangeInAttendanceSummaries(): void {
    this.eventSubscriber = this.eventManager.subscribe('attendanceSummaryListModification', () => this.loadPage());
  }

  delete(attendanceSummary: IAttendanceSummary): void {
    const modalRef = this.modalService.open(AttendanceSummaryDeleteDialogComponent, {
      size: 'lg',
      backdrop: 'static',
    });
    modalRef.componentInstance.attendanceSummary = attendanceSummary;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAttendanceSummary[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/attendance-summary'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.attendanceSummaries = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
