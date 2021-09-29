import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AttendanceSummaryUpdateComponent } from 'app/entities/attendance-summary/attendance-summary-update.component';
import { AttendanceSummaryService } from 'app/entities/attendance-summary/attendance-summary.service';
import { AttendanceSummary } from 'app/shared/model/attendance-summary.model';

describe('Component Tests', () => {
  describe('AttendanceSummary Management Update Component', () => {
    let comp: AttendanceSummaryUpdateComponent;
    let fixture: ComponentFixture<AttendanceSummaryUpdateComponent>;
    let service: AttendanceSummaryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AttendanceSummaryUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AttendanceSummaryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendanceSummaryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendanceSummaryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendanceSummary(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendanceSummary();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
