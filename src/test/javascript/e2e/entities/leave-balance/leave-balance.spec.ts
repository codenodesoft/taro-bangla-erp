import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LeaveBalanceComponentsPage,
  /* LeaveBalanceDeleteDialog, */
  LeaveBalanceUpdatePage,
} from './leave-balance.page-object';

const expect = chai.expect;

describe('LeaveBalance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let leaveBalanceComponentsPage: LeaveBalanceComponentsPage;
  let leaveBalanceUpdatePage: LeaveBalanceUpdatePage;
  /* let leaveBalanceDeleteDialog: LeaveBalanceDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LeaveBalances', async () => {
    await navBarPage.goToEntity('leave-balance');
    leaveBalanceComponentsPage = new LeaveBalanceComponentsPage();
    await browser.wait(ec.visibilityOf(leaveBalanceComponentsPage.title), 5000);
    expect(await leaveBalanceComponentsPage.getTitle()).to.eq('Leave Balances');
    await browser.wait(
      ec.or(ec.visibilityOf(leaveBalanceComponentsPage.entities), ec.visibilityOf(leaveBalanceComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LeaveBalance page', async () => {
    await leaveBalanceComponentsPage.clickOnCreateButton();
    leaveBalanceUpdatePage = new LeaveBalanceUpdatePage();
    expect(await leaveBalanceUpdatePage.getPageTitle()).to.eq('Create or edit a Leave Balance');
    await leaveBalanceUpdatePage.cancel();
  });

  /* it('should create and save LeaveBalances', async () => {
        const nbButtonsBeforeCreate = await leaveBalanceComponentsPage.countDeleteButtons();

        await leaveBalanceComponentsPage.clickOnCreateButton();

        await promise.all([
            leaveBalanceUpdatePage.setTotalDaysInput('5'),
            leaveBalanceUpdatePage.setRemainingDaysInput('5'),
            leaveBalanceUpdatePage.setFromInput('2000-12-31'),
            leaveBalanceUpdatePage.setToInput('2000-12-31'),
            leaveBalanceUpdatePage.setRemarksInput('remarks'),
            leaveBalanceUpdatePage.setLastSynchronizedOnInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            leaveBalanceUpdatePage.statusSelectLastOption(),
            leaveBalanceUpdatePage.setAssessmentYearInput('5'),
            leaveBalanceUpdatePage.setAmountInput('5'),
            leaveBalanceUpdatePage.employeeSelectLastOption(),
            leaveBalanceUpdatePage.leaveTypeSelectLastOption(),
            leaveBalanceUpdatePage.departmentSelectLastOption(),
            leaveBalanceUpdatePage.designationSelectLastOption(),
        ]);

        expect(await leaveBalanceUpdatePage.getTotalDaysInput()).to.eq('5', 'Expected totalDays value to be equals to 5');
        expect(await leaveBalanceUpdatePage.getRemainingDaysInput()).to.eq('5', 'Expected remainingDays value to be equals to 5');
        expect(await leaveBalanceUpdatePage.getFromInput()).to.eq('2000-12-31', 'Expected from value to be equals to 2000-12-31');
        expect(await leaveBalanceUpdatePage.getToInput()).to.eq('2000-12-31', 'Expected to value to be equals to 2000-12-31');
        expect(await leaveBalanceUpdatePage.getRemarksInput()).to.eq('remarks', 'Expected Remarks value to be equals to remarks');
        expect(await leaveBalanceUpdatePage.getLastSynchronizedOnInput()).to.contain('2001-01-01T02:30', 'Expected lastSynchronizedOn value to be equals to 2000-12-31');
        expect(await leaveBalanceUpdatePage.getAssessmentYearInput()).to.eq('5', 'Expected assessmentYear value to be equals to 5');
        expect(await leaveBalanceUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');

        await leaveBalanceUpdatePage.save();
        expect(await leaveBalanceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await leaveBalanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last LeaveBalance', async () => {
        const nbButtonsBeforeDelete = await leaveBalanceComponentsPage.countDeleteButtons();
        await leaveBalanceComponentsPage.clickOnLastDeleteButton();

        leaveBalanceDeleteDialog = new LeaveBalanceDeleteDialog();
        expect(await leaveBalanceDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Leave Balance?');
        await leaveBalanceDeleteDialog.clickOnConfirmButton();

        expect(await leaveBalanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
