import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendanceSynchronization } from 'app/shared/model/attendance-synchronization.model';

@Component({
  selector: 'jhi-attendance-synchronization-detail',
  templateUrl: './attendance-synchronization-detail.component.html',
})
export class AttendanceSynchronizationDetailComponent implements OnInit {
  attendanceSynchronization: IAttendanceSynchronization | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendanceSynchronization }) => (this.attendanceSynchronization = attendanceSynchronization));
  }

  previousState(): void {
    window.history.back();
  }
}
