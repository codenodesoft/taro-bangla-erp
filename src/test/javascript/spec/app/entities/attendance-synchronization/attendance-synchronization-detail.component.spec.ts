import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AttendanceSynchronizationDetailComponent } from 'app/entities/attendance-synchronization/attendance-synchronization-detail.component';
import { AttendanceSynchronization } from 'app/shared/model/attendance-synchronization.model';

describe('Component Tests', () => {
  describe('AttendanceSynchronization Management Detail Component', () => {
    let comp: AttendanceSynchronizationDetailComponent;
    let fixture: ComponentFixture<AttendanceSynchronizationDetailComponent>;
    const route = ({ data: of({ attendanceSynchronization: new AttendanceSynchronization(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AttendanceSynchronizationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AttendanceSynchronizationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendanceSynchronizationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load attendanceSynchronization on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attendanceSynchronization).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
