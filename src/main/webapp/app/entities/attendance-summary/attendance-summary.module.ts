import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CodeNodeErpSharedModule } from 'app/shared/shared.module';
import { AttendanceSummaryComponent } from './attendance-summary.component';
import { AttendanceSummaryDetailComponent } from './attendance-summary-detail.component';
import { AttendanceSummaryUpdateComponent } from './attendance-summary-update.component';
import { AttendanceSummaryDeleteDialogComponent } from './attendance-summary-delete-dialog.component';
import { attendanceSummaryRoute } from './attendance-summary.route';
import { AttendanceSummaryBatchUpdateComponent } from 'app/entities/attendance-summary/attendance-summary-batch-update.component';
import { AttendanceSummaryReportComponent } from 'app/entities/attendance-summary/attendance-summary-report.component';

@NgModule({
  imports: [CodeNodeErpSharedModule, RouterModule.forChild(attendanceSummaryRoute)],
  declarations: [
    AttendanceSummaryComponent,
    AttendanceSummaryDetailComponent,
    AttendanceSummaryUpdateComponent,
    AttendanceSummaryBatchUpdateComponent,
    AttendanceSummaryDeleteDialogComponent,
    AttendanceSummaryReportComponent,
  ],
  entryComponents: [AttendanceSummaryDeleteDialogComponent],
})
export class CodeNodeErpAttendanceSummaryModule {}
