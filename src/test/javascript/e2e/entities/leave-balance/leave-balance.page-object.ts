import { element, by, ElementFinder } from 'protractor';

export class LeaveBalanceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-leave-balance div table .btn-danger'));
  title = element.all(by.css('jhi-leave-balance div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getText();
  }
}

export class LeaveBalanceUpdatePage {
  pageTitle = element(by.id('jhi-leave-balance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  totalDaysInput = element(by.id('field_totalDays'));
  remainingDaysInput = element(by.id('field_remainingDays'));
  fromInput = element(by.id('field_from'));
  toInput = element(by.id('field_to'));
  remarksInput = element(by.id('field_remarks'));
  lastSynchronizedOnInput = element(by.id('field_lastSynchronizedOn'));
  statusSelect = element(by.id('field_status'));
  assessmentYearInput = element(by.id('field_assessmentYear'));
  amountInput = element(by.id('field_amount'));

  employeeSelect = element(by.id('field_employee'));
  leaveTypeSelect = element(by.id('field_leaveType'));
  departmentSelect = element(by.id('field_department'));
  designationSelect = element(by.id('field_designation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setTotalDaysInput(totalDays: string): Promise<void> {
    await this.totalDaysInput.sendKeys(totalDays);
  }

  async getTotalDaysInput(): Promise<string> {
    return await this.totalDaysInput.getAttribute('value');
  }

  async setRemainingDaysInput(remainingDays: string): Promise<void> {
    await this.remainingDaysInput.sendKeys(remainingDays);
  }

  async getRemainingDaysInput(): Promise<string> {
    return await this.remainingDaysInput.getAttribute('value');
  }

  async setFromInput(from: string): Promise<void> {
    await this.fromInput.sendKeys(from);
  }

  async getFromInput(): Promise<string> {
    return await this.fromInput.getAttribute('value');
  }

  async setToInput(to: string): Promise<void> {
    await this.toInput.sendKeys(to);
  }

  async getToInput(): Promise<string> {
    return await this.toInput.getAttribute('value');
  }

  async setRemarksInput(remarks: string): Promise<void> {
    await this.remarksInput.sendKeys(remarks);
  }

  async getRemarksInput(): Promise<string> {
    return await this.remarksInput.getAttribute('value');
  }

  async setLastSynchronizedOnInput(lastSynchronizedOn: string): Promise<void> {
    await this.lastSynchronizedOnInput.sendKeys(lastSynchronizedOn);
  }

  async getLastSynchronizedOnInput(): Promise<string> {
    return await this.lastSynchronizedOnInput.getAttribute('value');
  }

  async setStatusSelect(status: string): Promise<void> {
    await this.statusSelect.sendKeys(status);
  }

  async getStatusSelect(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async setAssessmentYearInput(assessmentYear: string): Promise<void> {
    await this.assessmentYearInput.sendKeys(assessmentYear);
  }

  async getAssessmentYearInput(): Promise<string> {
    return await this.assessmentYearInput.getAttribute('value');
  }

  async setAmountInput(amount: string): Promise<void> {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput(): Promise<string> {
    return await this.amountInput.getAttribute('value');
  }

  async employeeSelectLastOption(): Promise<void> {
    await this.employeeSelect.all(by.tagName('option')).last().click();
  }

  async employeeSelectOption(option: string): Promise<void> {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect(): ElementFinder {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption(): Promise<string> {
    return await this.employeeSelect.element(by.css('option:checked')).getText();
  }

  async leaveTypeSelectLastOption(): Promise<void> {
    await this.leaveTypeSelect.all(by.tagName('option')).last().click();
  }

  async leaveTypeSelectOption(option: string): Promise<void> {
    await this.leaveTypeSelect.sendKeys(option);
  }

  getLeaveTypeSelect(): ElementFinder {
    return this.leaveTypeSelect;
  }

  async getLeaveTypeSelectedOption(): Promise<string> {
    return await this.leaveTypeSelect.element(by.css('option:checked')).getText();
  }

  async departmentSelectLastOption(): Promise<void> {
    await this.departmentSelect.all(by.tagName('option')).last().click();
  }

  async departmentSelectOption(option: string): Promise<void> {
    await this.departmentSelect.sendKeys(option);
  }

  getDepartmentSelect(): ElementFinder {
    return this.departmentSelect;
  }

  async getDepartmentSelectedOption(): Promise<string> {
    return await this.departmentSelect.element(by.css('option:checked')).getText();
  }

  async designationSelectLastOption(): Promise<void> {
    await this.designationSelect.all(by.tagName('option')).last().click();
  }

  async designationSelectOption(option: string): Promise<void> {
    await this.designationSelect.sendKeys(option);
  }

  getDesignationSelect(): ElementFinder {
    return this.designationSelect;
  }

  async getDesignationSelectedOption(): Promise<string> {
    return await this.designationSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class LeaveBalanceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-leaveBalance-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-leaveBalance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
