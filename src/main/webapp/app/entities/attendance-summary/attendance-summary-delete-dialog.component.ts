import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendanceSummary } from 'app/shared/model/attendance-summary.model';
import { AttendanceSummaryService } from './attendance-summary.service';

@Component({
  templateUrl: './attendance-summary-delete-dialog.component.html',
})
export class AttendanceSummaryDeleteDialogComponent {
  attendanceSummary?: IAttendanceSummary;

  constructor(
    protected attendanceSummaryService: AttendanceSummaryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attendanceSummaryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attendanceSummaryListModification');
      this.activeModal.close();
    });
  }
}
