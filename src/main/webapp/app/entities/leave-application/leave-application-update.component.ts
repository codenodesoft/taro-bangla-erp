import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ILeaveApplication, LeaveApplication } from 'app/shared/model/leave-application.model';
import { LeaveApplicationService } from './leave-application.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type/leave-type.service';
import { IDepartment } from 'app/shared/model/department.model';
import { IDesignation } from 'app/shared/model/designation.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { DesignationService } from 'app/entities/designation/designation.service';

type SelectableEntity = IEmployee | ILeaveType | IDepartment | IDesignation;

@Component({
  selector: 'jhi-leave-application-update',
  templateUrl: './leave-application-update.component.html',
})
export class LeaveApplicationUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  leavetypes: ILeaveType[] = [];
  departments: IDepartment[] = [];
  designations: IDesignation[] = [];
  fromDp: any;
  toDp: any;

  employeeId?: string;

  editForm = this.fb.group({
    id: [],
    from: [null, [Validators.required]],
    to: [null, [Validators.required]],
    totalDays: [null, [Validators.required]],
    reason: [],
    attachment: [],
    attachmentContentType: [],
    status: [null, [Validators.required]],
    appliedBy: [],
    appliedOn: [],
    actionTakenBy: [],
    actionTakenOn: [],
    employee: [null, Validators.required],
    leaveType: [null, Validators.required],
    department: [],
    designation: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected leaveApplicationService: LeaveApplicationService,
    protected employeeService: EmployeeService,
    protected leaveTypeService: LeaveTypeService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leaveApplication }) => {
      if (!leaveApplication.id) {
        const today = moment().startOf('day');
        leaveApplication.appliedOn = today;
        leaveApplication.actionTakenOn = today;
      }

      this.updateForm(leaveApplication);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.leaveTypeService.query().subscribe((res: HttpResponse<ILeaveType[]>) => (this.leavetypes = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.designationService.query().subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
    });

    this.onChanges();
  }

  updateForm(leaveApplication: ILeaveApplication): void {
    this.editForm.patchValue({
      id: leaveApplication.id,
      from: leaveApplication.from,
      to: leaveApplication.to,
      totalDays: leaveApplication.totalDays,
      reason: leaveApplication.reason,
      attachment: leaveApplication.attachment,
      attachmentContentType: leaveApplication.attachmentContentType,
      status: leaveApplication.status,
      appliedBy: leaveApplication.appliedBy,
      appliedOn: leaveApplication.appliedOn ? leaveApplication.appliedOn.format(DATE_TIME_FORMAT) : null,
      actionTakenBy: leaveApplication.actionTakenBy,
      actionTakenOn: leaveApplication.actionTakenOn ? leaveApplication.actionTakenOn.format(DATE_TIME_FORMAT) : null,
      employee: leaveApplication.employee,
      leaveType: leaveApplication.leaveType,
      department: leaveApplication.department,
      designation: leaveApplication.designation,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('codeNodeErpApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leaveApplication = this.createFromForm();
    if (leaveApplication.id !== undefined) {
      this.subscribeToSaveResponse(this.leaveApplicationService.update(leaveApplication));
    } else {
      this.subscribeToSaveResponse(this.leaveApplicationService.create(leaveApplication));
    }
  }

  private createFromForm(): ILeaveApplication {
    return {
      ...new LeaveApplication(),
      id: this.editForm.get(['id'])!.value,
      from: this.editForm.get(['from'])!.value,
      to: this.editForm.get(['to'])!.value,
      totalDays: this.editForm.get(['totalDays'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      attachmentContentType: this.editForm.get(['attachmentContentType'])!.value,
      attachment: this.editForm.get(['attachment'])!.value,
      status: this.editForm.get(['status'])!.value,
      appliedBy: this.editForm.get(['appliedBy'])!.value,
      appliedOn: this.editForm.get(['appliedOn'])!.value ? moment(this.editForm.get(['appliedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      actionTakenBy: this.editForm.get(['actionTakenBy'])!.value,
      actionTakenOn: this.editForm.get(['actionTakenOn'])!.value
        ? moment(this.editForm.get(['actionTakenOn'])!.value, DATE_TIME_FORMAT)
        : undefined,
      employee: this.editForm.get(['employee'])!.value,
      leaveType: this.editForm.get(['leaveType'])!.value,
      department: this.editForm.get(['department'])!.value,
      designation: this.editForm.get(['designation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveApplication>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  onChanges(): void {
    this.editForm.get('from')!.valueChanges.subscribe(() => {
      this.updateTotalDays();
    });

    this.editForm.get('to')!.valueChanges.subscribe(() => {
      this.updateTotalDays();
    });
  }

  private updateTotalDays(): void {
    const fromDate = new Date(this.editForm.get('from')!.value);
    const toDate = new Date(this.editForm.get('to')!.value);
    const diff = toDate.getTime() - fromDate.getTime();
    this.editForm.patchValue({
      totalDays: diff / (1000 * 60 * 60 * 24) + 1,
    });
  }

  searchEmployee(): void {
    if (this.employeeId) {
      this.employeeService
        .query({
          'empId.equals': this.employeeId,
        })
        .subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    } else {
      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));
    }
  }
}
