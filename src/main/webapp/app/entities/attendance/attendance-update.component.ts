import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAttendance, Attendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from './attendance.service';
import { IAttendanceDataUpload } from 'app/shared/model/attendance-data-upload.model';
import { AttendanceDataUploadService } from 'app/entities/attendance-data-upload/attendance-data-upload.service';

@Component({
  selector: 'jhi-attendance-update',
  templateUrl: './attendance-update.component.html',
})
export class AttendanceUpdateComponent implements OnInit {
  isSaving = false;
  attendancedatauploads: IAttendanceDataUpload[] = [];

  editForm = this.fb.group({
    id: [],
    machineNo: [null, [Validators.required]],
    employeeMachineId: [null, [Validators.required]],
    attendanceDateTime: [null, [Validators.required]],
    remarks: [],
    attendanceDataUpload: [],
  });

  constructor(
    protected attendanceService: AttendanceService,
    protected attendanceDataUploadService: AttendanceDataUploadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendance }) => {
      if (!attendance.id) {
        const today = moment().startOf('day');
        attendance.attendanceDateTime = today;
      }

      this.updateForm(attendance);

      this.attendanceDataUploadService
        .query()
        .subscribe((res: HttpResponse<IAttendanceDataUpload[]>) => (this.attendancedatauploads = res.body || []));
    });
  }

  updateForm(attendance: IAttendance): void {
    this.editForm.patchValue({
      id: attendance.id,
      machineNo: attendance.machineNo,
      employeeMachineId: attendance.employeeMachineId,
      attendanceDateTime: attendance.attendanceDateTime ? attendance.attendanceDateTime.format(DATE_TIME_FORMAT) : null,
      remarks: attendance.remarks,
      attendanceDataUpload: attendance.attendanceDataUpload,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attendance = this.createFromForm();
    if (attendance.id !== undefined) {
      this.subscribeToSaveResponse(this.attendanceService.update(attendance));
    } else {
      this.subscribeToSaveResponse(this.attendanceService.create(attendance));
    }
  }

  private createFromForm(): IAttendance {
    return {
      ...new Attendance(),
      id: this.editForm.get(['id'])!.value,
      machineNo: this.editForm.get(['machineNo'])!.value,
      employeeMachineId: this.editForm.get(['employeeMachineId'])!.value,
      attendanceDateTime: this.editForm.get(['attendanceDateTime'])!.value
        ? moment(this.editForm.get(['attendanceDateTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      remarks: this.editForm.get(['remarks'])!.value,
      attendanceDataUpload: this.editForm.get(['attendanceDataUpload'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendance>>): void {
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

  trackById(index: number, item: IAttendanceDataUpload): any {
    return item.id;
  }
}
