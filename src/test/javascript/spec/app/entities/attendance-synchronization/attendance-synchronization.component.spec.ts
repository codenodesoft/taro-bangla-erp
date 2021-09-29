import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { CodeNodeErpTestModule } from '../../../test.module';
import { AttendanceSynchronizationComponent } from 'app/entities/attendance-synchronization/attendance-synchronization.component';
import { AttendanceSynchronizationService } from 'app/entities/attendance-synchronization/attendance-synchronization.service';
import { AttendanceSynchronization } from 'app/shared/model/attendance-synchronization.model';

describe('Component Tests', () => {
  describe('AttendanceSynchronization Management Component', () => {
    let comp: AttendanceSynchronizationComponent;
    let fixture: ComponentFixture<AttendanceSynchronizationComponent>;
    let service: AttendanceSynchronizationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [AttendanceSynchronizationComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(AttendanceSynchronizationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AttendanceSynchronizationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AttendanceSynchronizationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AttendanceSynchronization(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.attendanceSynchronizations && comp.attendanceSynchronizations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AttendanceSynchronization(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.attendanceSynchronizations && comp.attendanceSynchronizations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
