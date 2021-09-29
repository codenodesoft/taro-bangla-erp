import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { AttendanceSynchronization, IAttendanceSynchronization } from 'app/shared/model/attendance-synchronization.model';
import { AttendanceSynchronizationService } from './attendance-synchronization.service';
import { AttendanceSynchronizationComponent } from './attendance-synchronization.component';
import { AttendanceSynchronizationDetailComponent } from './attendance-synchronization-detail.component';
import { AttendanceSynchronizationUpdateComponent } from './attendance-synchronization-update.component';

@Injectable({ providedIn: 'root' })
export class AttendanceSynchronizationResolve implements Resolve<IAttendanceSynchronization> {
  constructor(private service: AttendanceSynchronizationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAttendanceSynchronization> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((attendanceSynchronization: HttpResponse<AttendanceSynchronization>) => {
          if (attendanceSynchronization.body) {
            return of(attendanceSynchronization.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AttendanceSynchronization());
  }
}

export const attendanceSynchronizationRoute: Routes = [
  {
    path: '',
    component: AttendanceSynchronizationComponent,
    data: {
      authorities: [Authority.ADMIN, Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'AttendanceSynchronizations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AttendanceSynchronizationDetailComponent,
    resolve: {
      attendanceSynchronization: AttendanceSynchronizationResolve,
    },
    data: {
      authorities: [Authority.ADMIN, Authority.USER],
      pageTitle: 'AttendanceSynchronizations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AttendanceSynchronizationUpdateComponent,
    resolve: {
      attendanceSynchronization: AttendanceSynchronizationResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'AttendanceSynchronizations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AttendanceSynchronizationUpdateComponent,
    resolve: {
      attendanceSynchronization: AttendanceSynchronizationResolve,
    },
    data: {
      authorities: [Authority.ADMIN],
      pageTitle: 'AttendanceSynchronizations',
    },
    canActivate: [UserRouteAccessService],
  },
];
