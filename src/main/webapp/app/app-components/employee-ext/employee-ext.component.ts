import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, ParamMap, Router, Data } from '@angular/router';
import { Subscription, combineLatest } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployee } from 'app/shared/model/employee.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EmployeeExtService } from './employee-ext.service';
import { EmployeeExtDeleteDialogComponent } from './employee-ext-delete-dialog.component';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { EmployeeComponent } from 'app/entities/employee/employee.component';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { ILine } from 'app/shared/model/line.model';
import { Filter, IFilter } from 'app/shared/model/filter.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { EmployeeStatus } from 'app/shared/model/enumerations/employee-status.model';

@Component({
  selector: 'jhi-employee',
  templateUrl: './employee-ext.component.html',
})
export class EmployeeExtComponent extends EmployeeComponent implements OnInit, OnDestroy {
  employees?: IEmployee[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  status!: EmployeeStatus;
  query = {};

  departmentId!: number | null | undefined;
  empId!: string | null | undefined;
  departments?: IDepartment[] = [];

  constructor(
    public employeeService: EmployeeExtService,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    private stateStorageService: StateStorageService,
    protected departmentService: DepartmentService
  ) {
    super(employeeService, activatedRoute, dataUtils, router, eventManager, modalService);
  }

  registerChangeInEmployees(): void {
    this.eventSubscriber = this.eventManager.subscribe('employeeListModification', () => {
      this.loadPage();
    });
  }

  ngOnInit(): void {
    // storing the url in the session, it will help in return from employee detail to employee list (with url parameters)
    this.stateStorageService.storeCustomUrl(this.router.routerState.snapshot.url);
    this.employeeService.clearEmployeeId();
    this.handleNavigation();
    this.registerChangeInEmployees();

    this.activatedRoute.data.subscribe(() => {
      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));
    });
  }

  fetchByStatus(page?: number, dontNavigate?: boolean): void {
    this.employeeService.departmentId = undefined;
    this.employeeService.empId = undefined;
    this.loadPage(1);
  }

  fetchByDept(page?: number, dontNavigate?: boolean): void {
    this.employeeService.status = undefined;
    this.employeeService.empId = undefined;
    this.loadPage(1);
  }

  fetch(page?: number, dontNavigate?: boolean): void {
    this.employeeService.departmentId = undefined;
    this.employeeService.status = undefined;
    this.loadPage(1);
  }

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;
    this.query = {};
    this.query['page'] = pageToLoad - 1;
    this.query['size'] = this.itemsPerPage;
    this.query['sort'] = this.sort();
    if (this.employeeService.status) this.query['status.equals'] = this.employeeService.status;
    if (this.employeeService.departmentId) this.query['departmentId.equals'] = this.employeeService.departmentId;
    if (this.employeeService.empId) this.query['empId.equals'] = this.employeeService.empId;
    this.employeeService.query(this.query).subscribe(
      (res: HttpResponse<IEmployee[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
      () => this.onError()
    );
  }

  fetchAll(): void {
    this.employeeService.empId = undefined;
    this.employeeService.status = undefined;
    this.employeeService.departmentId = undefined;
    this.loadPage();
  }

  downloadReport(): void {
    this.employeeService.downloadEmployeeReport(this.employeeService.status!).subscribe(data => {
      const file = new Blob([data], { type: 'application/pdf' });
      const pdfData = URL.createObjectURL(file);
      const link = document.createElement('a');
      link.href = pdfData;
      link.download = 'employee-report.pdf';
      link.click();
    });
  }
}
