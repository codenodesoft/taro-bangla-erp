import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LeaveApplicationService } from './leave-application.service';
import { LeaveApplicationDeleteDialogComponent } from './leave-application-delete-dialog.component';
import { Moment } from 'moment';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { DesignationService } from 'app/entities/designation/designation.service';

@Component({
  selector: 'jhi-leave-application',
  templateUrl: './leave-application.component.html',
})
export class LeaveApplicationComponent implements OnInit, OnDestroy {
  leaveApplications?: ILeaveApplication[];
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
    protected leaveApplicationService: LeaveApplicationService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  private static today(): Moment {
    return moment();
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if (this.department && this.designation && this.empId && this.fromDate && this.toDate) {
      this.leaveApplicationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
          'empId.equals': this.empId,
          'from.greaterOrEqualThan': this.fromDate,
          'to.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<ILeaveApplication[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.department && this.designation && this.fromDate && this.toDate) {
      this.leaveApplicationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
          'from.greaterOrEqualThan': this.fromDate,
          'to.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<ILeaveApplication[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.department && this.empId && this.fromDate && this.toDate) {
      this.leaveApplicationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'departmentId.equals': this.department.id,
          'empId.equals': this.empId,
          'from.greaterOrEqualThan': this.fromDate,
          'to.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<ILeaveApplication[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.designation && this.empId && this.fromDate && this.toDate) {
      this.leaveApplicationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'designationId.equals': this.designation.id,
          'empId.equals': this.empId,
          'from.greaterOrEqualThan': this.fromDate,
          'to.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<ILeaveApplication[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.department && this.fromDate && this.toDate) {
      this.leaveApplicationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'departmentId.equals': this.department.id,
          'from.greaterOrEqualThan': this.fromDate,
          'to.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<ILeaveApplication[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.designation && this.fromDate && this.toDate) {
      this.leaveApplicationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'designationId.equals': this.designation.id,
          'from.greaterOrEqualThan': this.fromDate,
          'to.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<ILeaveApplication[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.empId && this.fromDate && this.toDate) {
      this.leaveApplicationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'empId.equals': this.empId,
          'from.greaterOrEqualThan': this.fromDate,
          'to.lessOrEqualThan': this.toDate,
        })
        .subscribe(
          (res: HttpResponse<ILeaveApplication[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else {
      this.leaveApplicationService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<ILeaveApplication[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.toDate = LeaveApplicationComponent.today().format(DATE_FORMAT);
    this.fromDate = LeaveApplicationComponent.today().format(DATE_FORMAT);
    this.departmentService.query({ size: 500 }).subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    this.designationService.query({ size: 500 }).subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
    this.handleNavigation();
    this.registerChangeInLeaveApplications();
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

  trackId(index: number, item: ILeaveApplication): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInLeaveApplications(): void {
    this.eventSubscriber = this.eventManager.subscribe('leaveApplicationListModification', () => this.loadPage());
  }

  delete(leaveApplication: ILeaveApplication): void {
    const modalRef = this.modalService.open(LeaveApplicationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.leaveApplication = leaveApplication;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ILeaveApplication[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/leave-application'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.leaveApplications = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
