import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILeaveApplication } from 'app/shared/model/leave-application.model';

@Component({
  selector: 'jhi-leave-application-detail',
  templateUrl: './leave-application-detail.component.html',
})
export class LeaveApplicationDetailComponent implements OnInit {
  leaveApplication: ILeaveApplication | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ leaveApplication }) => (this.leaveApplication = leaveApplication));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
