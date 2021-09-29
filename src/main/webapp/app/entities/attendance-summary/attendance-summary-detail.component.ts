import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';

@Component({
  selector: 'jhi-attendance-summary-detail',
  templateUrl: './attendance-summary-detail.component.html',
})
export class AttendanceSummaryDetailComponent implements OnInit {
  attendanceSummary: IAttendanceSummary | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attendanceSummary }) => (this.attendanceSummary = attendanceSummary));
  }

  previousState(): void {
    window.history.back();
  }
}
