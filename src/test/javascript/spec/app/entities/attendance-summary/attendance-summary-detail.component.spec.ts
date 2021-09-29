import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AttendanceSummaryDetailComponent } from 'app/entities/attendance-summary/attendance-summary-detail.component';
import { AttendanceSummary } from 'app/shared/model/attendance-summary.model';

describe('Component Tests', () => {
  describe('AttendanceSummary Management Detail Component', () => {
    let comp: AttendanceSummaryDetailComponent;
    let fixture: ComponentFixture<AttendanceSummaryDetailComponent>;
    const route = ({ data: of({ attendanceSummary: new AttendanceSummary(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AttendanceSummaryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AttendanceSummaryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AttendanceSummaryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load attendanceSummary on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.attendanceSummary).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
