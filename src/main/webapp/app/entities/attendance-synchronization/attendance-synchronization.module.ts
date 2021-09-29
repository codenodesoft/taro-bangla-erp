import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { AttendanceSynchronizationComponent } from './attendance-synchronization.component';
import { AttendanceSynchronizationDetailComponent } from './attendance-synchronization-detail.component';
import { AttendanceSynchronizationUpdateComponent } from './attendance-synchronization-update.component';
import { AttendanceSynchronizationDeleteDialogComponent } from './attendance-synchronization-delete-dialog.component';
import { attendanceSynchronizationRoute } from './attendance-synchronization.route';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(attendanceSynchronizationRoute)],
  declarations: [
    AttendanceSynchronizationComponent,
    AttendanceSynchronizationDetailComponent,
    AttendanceSynchronizationUpdateComponent,
    AttendanceSynchronizationDeleteDialogComponent,
  ],
  entryComponents: [AttendanceSynchronizationDeleteDialogComponent],
})
export class CodeNodeErpAttendanceSynchronizationModule {}
