import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeaveBalance } from 'app/shared/model/leave-balance.model';
import { LeaveBalanceService } from './leave-balance.service';

@Component({
  templateUrl: './leave-balance-delete-dialog.component.html',
})
export class LeaveBalanceDeleteDialogComponent {
  leaveBalance?: ILeaveBalance;

  constructor(
    protected leaveBalanceService: LeaveBalanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leaveBalanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('leaveBalanceListModification');
      this.activeModal.close();
    });
  }
}
