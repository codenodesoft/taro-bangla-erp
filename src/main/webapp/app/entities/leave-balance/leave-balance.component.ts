import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILeaveBalance } from 'app/shared/model/leave-balance.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LeaveBalanceService } from './leave-balance.service';
import { LeaveBalanceDeleteDialogComponent } from './leave-balance-delete-dialog.component';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IConstant } from 'app/shared/model/constant.model';
import { YEARS } from 'app/shared/constants/common.constants';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { DesignationService } from 'app/entities/designation/designation.service';

@Component({
  selector: 'jhi-leave-balance',
  templateUrl: './leave-balance.component.html',
})
export class LeaveBalanceComponent implements OnInit, OnDestroy {
  leaveBalances?: ILeaveBalance[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  years?: IConstant[];
  year?: number;
  departments?: IDepartment[];
  department?: IDepartment;
  designations?: IDesignation[];
  designation?: IDesignation;
  empId?: string;

  constructor(
    protected leaveBalanceService: LeaveBalanceService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.year = new Date().getFullYear();
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    if (this.year && this.department && this.designation && this.empId) {
      this.leaveBalanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'assessmentYear.equals': this.year,
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
          'empId.equals': this.empId,
        })
        .subscribe(
          (res: HttpResponse<ILeaveBalance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.year && this.department && this.designation) {
      this.leaveBalanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'assessmentYear.equals': this.year,
          'departmentId.equals': this.department.id,
          'designationId.equals': this.designation.id,
        })
        .subscribe(
          (res: HttpResponse<ILeaveBalance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.year && this.department && this.empId) {
      this.leaveBalanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'assessmentYear.equals': this.year,
          'departmentId.equals': this.department.id,
          'empId.equals': this.empId,
        })
        .subscribe(
          (res: HttpResponse<ILeaveBalance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.year && this.designation && this.empId) {
      this.leaveBalanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'assessmentYear.equals': this.year,
          'designationId.equals': this.designation.id,
          'empId.equals': this.empId,
        })
        .subscribe(
          (res: HttpResponse<ILeaveBalance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.year && this.department) {
      this.leaveBalanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'assessmentYear.equals': this.year,
          'departmentId.equals': this.department.id,
        })
        .subscribe(
          (res: HttpResponse<ILeaveBalance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.year && this.designation) {
      this.leaveBalanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'assessmentYear.equals': this.year,
          'designationId.equals': this.designation.id,
        })
        .subscribe(
          (res: HttpResponse<ILeaveBalance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else if (this.year && this.empId) {
      this.leaveBalanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
          'assessmentYear.equals': this.year,
          'empId.equals': this.empId,
        })
        .subscribe(
          (res: HttpResponse<ILeaveBalance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    } else {
      this.leaveBalanceService
        .query({
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort(),
        })
        .subscribe(
          (res: HttpResponse<ILeaveBalance[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
          () => this.onError()
        );
    }
  }

  ngOnInit(): void {
    this.years = YEARS(2000, new Date().getFullYear());
    this.departmentService.query({ size: 500 }).subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    this.designationService.query({ size: 500 }).subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
    this.handleNavigation();
    this.registerChangeInLeaveBalances();
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

  trackId(index: number, item: ILeaveBalance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLeaveBalances(): void {
    this.eventSubscriber = this.eventManager.subscribe('leaveBalanceListModification', () => this.loadPage());
  }

  delete(leaveBalance: ILeaveBalance): void {
    const modalRef = this.modalService.open(LeaveBalanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.leaveBalance = leaveBalance;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ILeaveBalance[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/leave-balance'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.leaveBalances = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  download(): void {
    if (this.year && this.department && this.designation && this.empId) {
      this.leaveBalanceService.download({
        'assessmentYear.equals': this.year,
        'departmentId.equals': this.department.id,
        'designationId.equals': this.designation.id,
        'empId.equals': this.empId,
      });
    } else if (this.year && this.department && this.designation) {
      this.leaveBalanceService.download({
        'assessmentYear.equals': this.year,
        'departmentId.equals': this.department.id,
        'designationId.equals': this.designation.id,
      });
    } else if (this.year && this.department && this.empId) {
      this.leaveBalanceService.download({
        'assessmentYear.equals': this.year,
        'departmentId.equals': this.department.id,
        'empId.equals': this.empId,
      });
    } else if (this.year && this.designation && this.empId) {
      this.leaveBalanceService.download({
        'assessmentYear.equals': this.year,
        'designationId.equals': this.designation.id,
        'empId.equals': this.empId,
      });
    } else if (this.year && this.department) {
      this.leaveBalanceService.download({
        'assessmentYear.equals': this.year,
        'departmentId.equals': this.department.id,
      });
    } else if (this.year && this.designation) {
      this.leaveBalanceService.download({
        'assessmentYear.equals': this.year,
        'designationId.equals': this.designation.id,
      });
    } else if (this.year && this.empId) {
      this.leaveBalanceService.download({
        'assessmentYear.equals': this.year,
        'empId.equals': this.empId,
      });
    } else if (this.year) {
      this.leaveBalanceService.download({
        'assessmentYear.equals': this.year,
      });
    } else {
      alert('Please fill up the form correctly');
    }
  }
}
