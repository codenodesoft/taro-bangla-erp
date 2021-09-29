import { element, by, ElementFinder } from 'protractor';

export class LeaveTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-leave-type div table .btn-danger'));
  title = element.all(by.css('jhi-leave-type div h2#page-heading span')).first();
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

export class LeaveTypeUpdatePage {
  pageTitle = element(by.id('jhi-leave-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameSelect = element(by.id('field_name'));
  totalDaysInput = element(by.id('field_totalDays'));
  startDateInput = element(by.id('field_startDate'));
  endDateInput = element(by.id('field_endDate'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setNameSelect(name: string): Promise<void> {
    await this.nameSelect.sendKeys(name);
  }

  async getNameSelect(): Promise<string> {
    return await this.nameSelect.element(by.css('option:checked')).getText();
  }

  async nameSelectLastOption(): Promise<void> {
    await this.nameSelect.all(by.tagName('option')).last().click();
  }

  async setTotalDaysInput(totalDays: string): Promise<void> {
    await this.totalDaysInput.sendKeys(totalDays);
  }

  async getTotalDaysInput(): Promise<string> {
    return await this.totalDaysInput.getAttribute('value');
  }

  async setStartDateInput(startDate: string): Promise<void> {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput(): Promise<string> {
    return await this.startDateInput.getAttribute('value');
  }

  async setEndDateInput(endDate: string): Promise<void> {
    await this.endDateInput.sendKeys(endDate);
  }

  async getEndDateInput(): Promise<string> {
    return await this.endDateInput.getAttribute('value');
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

export class LeaveTypeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-leaveType-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-leaveType'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
