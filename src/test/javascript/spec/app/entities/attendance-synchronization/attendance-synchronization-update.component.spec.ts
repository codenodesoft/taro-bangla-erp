import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AttendanceSynchronizationUpdateComponent } from 'app/entities/attendance-synchronization/attendance-synchronization-update.component';
import { AttendanceSynchronizationService } from 'app/entities/attendance-synchronization/attendance-synchronization.service';
import { AttendanceSynchronization } from 'app/shared/model/attendance-synchronization.model';

describe('Component Tests', () => {
  describe('AttendanceSynchronization Management Update Component', () => {
    let comp: AttendanceSynchronizationUpdateComponent;
    let fixture: ComponentFixture<AttendanceSynchronizationUpdateComponent>;
    let service: AttendanceSynchronizationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AttendanceSynchronizationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AttendanceSynchronizationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendanceSynchronizationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendanceSynchronizationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AttendanceSynchronization(123);
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
        const entity = new AttendanceSynchronization();
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
