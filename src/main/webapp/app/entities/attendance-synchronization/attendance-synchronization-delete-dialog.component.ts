import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAttendanceSynchronization } from 'app/shared/model/attendance-synchronization.model';
import { AttendanceSynchronizationService } from './attendance-synchronization.service';

@Component({
  templateUrl: './attendance-synchronization-delete-dialog.component.html',
})
export class AttendanceSynchronizationDeleteDialogComponent {
  attendanceSynchronization?: IAttendanceSynchronization;

  constructor(
    protected attendanceSynchronizationService: AttendanceSynchronizationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.attendanceSynchronizationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('attendanceSynchronizationListModification');
      this.activeModal.close();
    });
  }
}
