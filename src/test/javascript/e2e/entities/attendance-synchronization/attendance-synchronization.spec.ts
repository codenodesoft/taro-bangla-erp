import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  AttendanceSynchronizationComponentsPage,
  AttendanceSynchronizationDeleteDialog,
  AttendanceSynchronizationUpdatePage,
} from './attendance-synchronization.page-object';

const expect = chai.expect;

describe('AttendanceSynchronization e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let attendanceSynchronizationComponentsPage: AttendanceSynchronizationComponentsPage;
  let attendanceSynchronizationUpdatePage: AttendanceSynchronizationUpdatePage;
  let attendanceSynchronizationDeleteDialog: AttendanceSynchronizationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AttendanceSynchronizations', async () => {
    await navBarPage.goToEntity('attendance-synchronization');
    attendanceSynchronizationComponentsPage = new AttendanceSynchronizationComponentsPage();
    await browser.wait(ec.visibilityOf(attendanceSynchronizationComponentsPage.title), 5000);
    expect(await attendanceSynchronizationComponentsPage.getTitle()).to.eq('Attendance Synchronizations');
    await browser.wait(
      ec.or(
        ec.visibilityOf(attendanceSynchronizationComponentsPage.entities),
        ec.visibilityOf(attendanceSynchronizationComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create AttendanceSynchronization page', async () => {
    await attendanceSynchronizationComponentsPage.clickOnCreateButton();
    attendanceSynchronizationUpdatePage = new AttendanceSynchronizationUpdatePage();
    expect(await attendanceSynchronizationUpdatePage.getPageTitle()).to.eq('Create or edit a Attendance Synchronization');
    await attendanceSynchronizationUpdatePage.cancel();
  });

  it('should create and save AttendanceSynchronizations', async () => {
    const nbButtonsBeforeCreate = await attendanceSynchronizationComponentsPage.countDeleteButtons();

    await attendanceSynchronizationComponentsPage.clickOnCreateButton();

    await promise.all([
      attendanceSynchronizationUpdatePage.setFromDateInput('2000-12-31'),
      attendanceSynchronizationUpdatePage.setToDateInput('2000-12-31'),
      attendanceSynchronizationUpdatePage.setStartTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      attendanceSynchronizationUpdatePage.setEndTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      attendanceSynchronizationUpdatePage.statusSelectLastOption(),
    ]);

    expect(await attendanceSynchronizationUpdatePage.getFromDateInput()).to.eq(
      '2000-12-31',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await attendanceSynchronizationUpdatePage.getToDateInput()).to.eq(
      '2000-12-31',
      'Expected toDate value to be equals to 2000-12-31'
    );
    expect(await attendanceSynchronizationUpdatePage.getStartTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected startTime value to be equals to 2000-12-31'
    );
    expect(await attendanceSynchronizationUpdatePage.getEndTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected endTime value to be equals to 2000-12-31'
    );

    await attendanceSynchronizationUpdatePage.save();
    expect(await attendanceSynchronizationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await attendanceSynchronizationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last AttendanceSynchronization', async () => {
    const nbButtonsBeforeDelete = await attendanceSynchronizationComponentsPage.countDeleteButtons();
    await attendanceSynchronizationComponentsPage.clickOnLastDeleteButton();

    attendanceSynchronizationDeleteDialog = new AttendanceSynchronizationDeleteDialog();
    expect(await attendanceSynchronizationDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Attendance Synchronization?'
    );
    await attendanceSynchronizationDeleteDialog.clickOnConfirmButton();

    expect(await attendanceSynchronizationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
