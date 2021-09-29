import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAttendanceSummary, AttendanceSummary } from 'app/shared/model/attendance-summary.model';
import { AttendanceSummaryService } from './attendance-summary.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';
import { IEmployeeSalary } from 'app/shared/model/employee-salary.model';
import { EmployeeSalaryService } from 'app/entities/employee-salary/employee-salary.service';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department/department.service';
import { IDesignation } from 'app/shared/model/designation.model';
import { DesignationService } from 'app/entities/designation/designation.service';
import { ILine } from 'app/shared/model/line.model';
import { LineService } from 'app/entities/line/line.service';
import { IGrade } from 'app/shared/model/grade.model';
import { GradeService } from 'app/entities/grade/grade.service';

type SelectableEntity = IEmployee | IEmployeeSalary | IDepartment | IDesignation | ILine | IGrade;

@Component({
  selector: 'jhi-attendance-summary-update',
  templateUrl: './attendance-summary-update.component.html',
})
export class AttendanceSummaryUpdateComponent implements OnInit {
  isSaving = false;
  employees: IEmployee[] = [];
  employeesalaries: IEmployeeSalary[] = [];
  departments: IDepartment[] = [];
  designations: IDesignation[] = [];
  lines: ILine[] = [];
  grades: IGrade[] = [];
  attendanceDateDp: any;

  employeeId?: string;

  editForm = this.fb.group({
    id: [],
    inTime: [],
    outTime: [],
    totalHours: [],
    workingHours: [],
    overtime: [],
    attendanceType: [],
    attendanceStatus: [],
    remarks: [],
    attendanceDate: [],
    employee: [],
    employeeSalary: [],
    department: [],
    designation: [],
    line: [],
    grade: [],
  });

  constructor(
    protected attendanceSummaryService: AttendanceSummaryService,
    protected employeeService: EmployeeService,
    protected employeeSalaryService: EmployeeSalaryService,
    protected departmentService: DepartmentService,
    protected designationService: DesignationService,
    protected lineService: LineService,
    protected gradeService: GradeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendanceSummary }) => {
      if (!attendanceSummary.id) {
        const today = moment().startOf('day');
        attendanceSummary.inTime = today;
        attendanceSummary.outTime = today;
      }

      this.updateForm(attendanceSummary);

      this.employeeService.query().subscribe((res: HttpResponse<IEmployee[]>) => (this.employees = res.body || []));

      this.employeeSalaryService.query().subscribe((res: HttpResponse<IEmployeeSalary[]>) => (this.employeesalaries = res.body || []));

      this.departmentService.query().subscribe((res: HttpResponse<IDepartment[]>) => (this.departments = res.body || []));

      this.designationService.query().subscribe((res: HttpResponse<IDesignation[]>) => (this.designations = res.body || []));

      this.lineService.query().subscribe((res: HttpResponse<ILine[]>) => (this.lines = res.body || []));

      this.gradeService.query().subscribe((res: HttpResponse<IGrade[]>) => (this.grades = res.body || []));
    });
  }

  updateForm(attendanceSummary: IAttendanceSummary): void {
    this.editForm.patchValue({
      id: attendanceSummary.id,
      inTime: attendanceSummary.inTime ? attendanceSummary.inTime.format(DATE_TIME_FORMAT) : null,
      outTime: attendanceSummary.outTime ? attendanceSummary.outTime.format(DATE_TIME_FORMAT) : null,
      totalHours: attendanceSummary.totalHours,
      workingHours: attendanceSummary.workingHours,
      overtime: attendanceSummary.overtime,
      attendanceType: attendanceSummary.attendanceType,
      attendanceStatus: attendanceSummary.attendanceStatus,
      remarks: attendanceSummary.remarks,
      attendanceDate: attendanceSummary.attendanceDate,
      employee: attendanceSummary.employee,
      employeeSalary: attendanceSummary.employeeSalary,
      department: attendanceSummary.department,
      designation: attendanceSummary.designation,
      line: attendanceSummary.line,
      grade: attendanceSummary.grade,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attendanceSummary = this.createFromForm();
    if (attendanceSummary.id !== undefined) {
      this.subscribeToSaveResponse(this.attendanceSummaryService.update(attendanceSummary));
    } else {
      this.subscribeToSaveResponse(this.attendanceSummaryService.create(attendanceSummary));
    }
  }

  private createFromForm(): IAttendanceSummary {
    return {
      ...new AttendanceSummary(),
      id: this.editForm.get(['id'])!.value,
      inTime: this.editForm.get(['inTime'])!.value ? moment(this.editForm.get(['inTime'])!.value, DATE_TIME_FORMAT) : undefined,
      outTime: this.editForm.get(['outTime'])!.value ? moment(this.editForm.get(['outTime'])!.value, DATE_TIME_FORMAT) : undefined,
      totalHours: this.editForm.get(['totalHours'])!.value,
      workingHours: this.editForm.get(['workingHours'])!.value,
      overtime: this.editForm.get(['overtime'])!.value,
      attendanceType: this.editForm.get(['attendanceType'])!.value,
      attendanceStatus: this.editForm.get(['attendanceStatus'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      attendanceDate: this.editForm.get(['attendanceDate'])!.value,
      employee: this.editForm.get(['employee'])!.value,
      employeeSalary: this.editForm.get(['employeeSalary'])!.value,
      department: this.editForm.get(['department'])!.value,
      designation: this.editForm.get(['designation'])!.value,
      line: this.editForm.get(['line'])!.value,
      grade: this.editForm.get(['grade'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendanceSummary>>): void {
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
