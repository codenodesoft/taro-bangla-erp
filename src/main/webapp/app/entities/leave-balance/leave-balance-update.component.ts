import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ILeaveBalance, LeaveBalance } from 'app/shared/model/leave-balance.model';
import { LeaveBalanceService } from './leave-balance.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { ILeaveType } from 'app/shared/model/leave-type.model';
import { LeaveTypeService } from 'app/entities/leave-type/leave-type.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';

type SelectableEntity = IEmployee | ILeaveType | IDepartment | IDesignation;

@Component({
  selector: 'jhi-leave-balance-update',
  templateUrl: './leave-balance-update.component.html',
})
export class LeaveBalanceUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  leavetypes: ILeaveType[] = [];
  departments: IDepartment[] = [];
  designations: IDesignation[] = [];
  fromDp: any;
  toDp: any;

  editForm = this.fb.group({
    id: [],
    totalDays: [null, [Validators.required]],
    remainingDays: [null, [Validators.required]],
    from: [null, [Validators.required]],
    to: [null, [Validators.required]],
    remarks: [],
    lastSynchronizedOn: [],
    status: [null, [Validators.required]],
    assessmentYear: [null, [Validators.required]],
    amount: [],
    employee: [null, Validators.required],
    leaveType: [null, Validators.required],
    department: [],
    designation: [],
  });

  constructor(
    protected leaveBalanceService: LeaveBalanceService,
    protected employeeService: EmployeeService,
    protected leaveTypeService: LeaveTypeService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leaveBalance }) => {
      if (!leaveBalance.id) {
        const today = moment().startOf('day');
        leaveBalance.lastSynchronizedOn = today;
      }

      this.updateForm(leaveBalance);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.leaveTypeService.query().subscribe((res: HttpResponse<ILeaveType[]>) => (this.leavetypes = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.designationService.query().subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));
    });
  }

  updateForm(leaveBalance: ILeaveBalance): void {
    this.editForm.patchValue({
      id: leaveBalance.id,
      totalDays: leaveBalance.totalDays,
      remainingDays: leaveBalance.remainingDays,
      from: leaveBalance.from,
      to: leaveBalance.to,
      remarks: leaveBalance.remarks,
      lastSynchronizedOn: leaveBalance.lastSynchronizedOn ? leaveBalance.lastSynchronizedOn.format(DATE_TIME_FORMAT) : null,
      status: leaveBalance.status,
      assessmentYear: leaveBalance.assessmentYear,
      amount: leaveBalance.amount,
      employee: leaveBalance.employee,
      leaveType: leaveBalance.leaveType,
      department: leaveBalance.department,
      designation: leaveBalance.designation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const leaveBalance = this.createFromForm();
    if (leaveBalance.id !== undefined) {
      this.subscribeToSaveResponse(this.leaveBalanceService.update(leaveBalance));
    } else {
      this.subscribeToSaveResponse(this.leaveBalanceService.create(leaveBalance));
    }
  }

  private createFromForm(): ILeaveBalance {
    return {
      ...new LeaveBalance(),
      id: this.editForm.get(['id'])!.value,
      totalDays: this.editForm.get(['totalDays'])!.value,
      remainingDays: this.editForm.get(['remainingDays'])!.value,
      from: this.editForm.get(['from'])!.value,
      to: this.editForm.get(['to'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      lastSynchronizedOn: this.editForm.get(['lastSynchronizedOn'])!.value
        ? moment(this.editForm.get(['lastSynchronizedOn'])!.value, DATE_TIME_FORMAT)
        : undefined,
      status: this.editForm.get(['status'])!.value,
      assessmentYear: this.editForm.get(['assessmentYear'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      leaveType: this.editForm.get(['leaveType'])!.value,
      department: this.editForm.get(['department'])!.value,
      designation: this.editForm.get(['designation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILeaveBalance>>): void {
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
}
