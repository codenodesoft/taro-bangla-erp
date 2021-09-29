import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAttendanceSynchronization, AttendanceSynchronization } from 'app/shared/model/attendance-synchronization.model';
import { AttendanceSynchronizationService } from './attendance-synchronization.service';

@Component({
  selector: 'jhi-attendance-synchronization-update',
  templateUrl: './attendance-synchronization-update.component.html',
})
export class AttendanceSynchronizationUpdateComponent implements OnInit {
  isSaving = false;
  fromDateDp: any;
  toDateDp: any;

  editForm = this.fb.group({
    id: [],
    fromDate: [null, [Validators.required]],
    toDate: [null, [Validators.required]],
    startTime: [],
    endTime: [],
    status: [],
  });

  constructor(
    protected attendanceSynchronizationService: AttendanceSynchronizationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendanceSynchronization }) => {
      if (!attendanceSynchronization.id) {
        const today = moment().startOf('day');
        attendanceSynchronization.startTime = today;
        attendanceSynchronization.endTime = today;
      }

      this.updateForm(attendanceSynchronization);
    });
  }

  updateForm(attendanceSynchronization: IAttendanceSynchronization): void {
    this.editForm.patchValue({
      id: attendanceSynchronization.id,
      fromDate: attendanceSynchronization.fromDate,
      toDate: attendanceSynchronization.toDate,
      startTime: attendanceSynchronization.startTime ? attendanceSynchronization.startTime.format(DATE_TIME_FORMAT) : null,
      endTime: attendanceSynchronization.endTime ? attendanceSynchronization.endTime.format(DATE_TIME_FORMAT) : null,
      status: attendanceSynchronization.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attendanceSynchronization = this.createFromForm();
    if (attendanceSynchronization.id !== undefined) {
      this.subscribeToSaveResponse(this.attendanceSynchronizationService.update(attendanceSynchronization));
    } else {
      this.subscribeToSaveResponse(this.attendanceSynchronizationService.create(attendanceSynchronization));
    }
  }

  private createFromForm(): IAttendanceSynchronization {
    return {
      ...new AttendanceSynchronization(),
      id: this.editForm.get(['id'])!.value,
      fromDate: this.editForm.get(['fromDate'])!.value,
      toDate: this.editForm.get(['toDate'])!.value,
      startTime: this.editForm.get(['startTime'])!.value ? moment(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined,
      endTime: this.editForm.get(['endTime'])!.value ? moment(this.editForm.get(['endTime'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendanceSynchronization>>): void {
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
}
