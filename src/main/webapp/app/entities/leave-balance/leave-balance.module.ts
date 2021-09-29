import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { LeaveBalanceComponent } from './leave-balance.component';
import { LeaveBalanceDetailComponent } from './leave-balance-detail.component';
import { LeaveBalanceUpdateComponent } from './leave-balance-update.component';
import { LeaveBalanceDeleteDialogComponent } from './leave-balance-delete-dialog.component';
import { leaveBalanceRoute } from './leave-balance.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(leaveBalanceRoute)],
  declarations: [LeaveBalanceComponent, LeaveBalanceDetailComponent, LeaveBalanceUpdateComponent, LeaveBalanceDeleteDialogComponent],
  entryComponents: [LeaveBalanceDeleteDialogComponent],
})
export class CodeNodeErpLeaveBalanceModule {}
