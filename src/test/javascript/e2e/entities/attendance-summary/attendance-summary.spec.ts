import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AttendanceSummaryComponentsPage,
  AttendanceSummaryDeleteDialog,
  AttendanceSummaryUpdatePage,
} from './attendance-summary.page-object';

const expect = chai.expect;

describe('AttendanceSummary e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let attendanceSummaryComponentsPage: AttendanceSummaryComponentsPage;
  let attendanceSummaryUpdatePage: AttendanceSummaryUpdatePage;
  let attendanceSummaryDeleteDialog: AttendanceSummaryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AttendanceSummaries', async () => {
    await navBarPage.goToEntity('attendance-summary');
    attendanceSummaryComponentsPage = new AttendanceSummaryComponentsPage();
    await browser.wait(ec.visibilityOf(attendanceSummaryComponentsPage.title), 5000);
    expect(await attendanceSummaryComponentsPage.getTitle()).to.eq('Attendance Summaries');
    await browser.wait(
      ec.or(ec.visibilityOf(attendanceSummaryComponentsPage.entities), ec.visibilityOf(attendanceSummaryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create AttendanceSummary page', async () => {
    await attendanceSummaryComponentsPage.clickOnCreateButton();
    attendanceSummaryUpdatePage = new AttendanceSummaryUpdatePage();
    expect(await attendanceSummaryUpdatePage.getPageTitle()).to.eq('Create or edit a Attendance Summary');
    await attendanceSummaryUpdatePage.cancel();
  });

  it('should create and save AttendanceSummaries', async () => {
    const nbButtonsBeforeCreate = await attendanceSummaryComponentsPage.countDeleteButtons();

    await attendanceSummaryComponentsPage.clickOnCreateButton();

    await promise.all([
      attendanceSummaryUpdatePage.setInTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      attendanceSummaryUpdatePage.setOutTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      attendanceSummaryUpdatePage.setTotalHoursInput('PT12S'),
      attendanceSummaryUpdatePage.setWorkingHoursInput('PT12S'),
      attendanceSummaryUpdatePage.setOvertimeInput('PT12S'),
      attendanceSummaryUpdatePage.attendanceTypeSelectLastOption(),
      attendanceSummaryUpdatePage.attendanceStatusSelectLastOption(),
      attendanceSummaryUpdatePage.setRemarksInput('remarks'),
      attendanceSummaryUpdatePage.setAttendanceDateInput('2000-12-31'),
      attendanceSummaryUpdatePage.employeeSelectLastOption(),
      attendanceSummaryUpdatePage.employeeSalarySelectLastOption(),
      attendanceSummaryUpdatePage.departmentSelectLastOption(),
      attendanceSummaryUpdatePage.designationSelectLastOption(),
      attendanceSummaryUpdatePage.lineSelectLastOption(),
      attendanceSummaryUpdatePage.gradeSelectLastOption(),
    ]);

    expect(await attendanceSummaryUpdatePage.getInTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected inTime value to be equals to 2000-12-31'
    );
    expect(await attendanceSummaryUpdatePage.getOutTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected outTime value to be equals to 2000-12-31'
    );
    expect(await attendanceSummaryUpdatePage.getTotalHoursInput()).to.contain('12', 'Expected totalHours value to be equals to 12');
    expect(await attendanceSummaryUpdatePage.getWorkingHoursInput()).to.contain('12', 'Expected workingHours value to be equals to 12');
    expect(await attendanceSummaryUpdatePage.getOvertimeInput()).to.contain('12', 'Expected overtime value to be equals to 12');
    expect(await attendanceSummaryUpdatePage.getRemarksInput()).to.eq('remarks', 'Expected Remarks value to be equals to remarks');
    expect(await attendanceSummaryUpdatePage.getAttendanceDateInput()).to.eq(
      '2000-12-31',
      'Expected attendanceDate value to be equals to 2000-12-31'
    );

    await attendanceSummaryUpdatePage.save();
    expect(await attendanceSummaryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await attendanceSummaryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AttendanceSummary', async () => {
    const nbButtonsBeforeDelete = await attendanceSummaryComponentsPage.countDeleteButtons();
    await attendanceSummaryComponentsPage.clickOnLastDeleteButton();

    attendanceSummaryDeleteDialog = new AttendanceSummaryDeleteDialog();
    expect(await attendanceSummaryDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Attendance Summary?');
    await attendanceSummaryDeleteDialog.clickOnConfirmButton();

    expect(await attendanceSummaryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
