import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CodeNodeErpTestModule } from '../../../test.module';
import { LeaveBalanceDetailComponent } from 'app/entities/leave-balance/leave-balance-detail.component';
import { LeaveBalance } from 'app/shared/model/leave-balance.model';

describe('Component Tests', () => {
  describe('LeaveBalance Management Detail Component', () => {
    let comp: LeaveBalanceDetailComponent;
    let fixture: ComponentFixture<LeaveBalanceDetailComponent>;
    const route = ({ data: of({ leaveBalance: new LeaveBalance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CodeNodeErpTestModule],
        declarations: [LeaveBalanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LeaveBalanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LeaveBalanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load leaveBalance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.leaveBalance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
