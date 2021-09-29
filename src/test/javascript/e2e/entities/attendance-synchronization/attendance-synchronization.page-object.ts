import { element, by, ElementFinder } from 'protractor';

export class AttendanceSynchronizationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-attendance-synchronization div table .btn-danger'));
  title = element.all(by.css('jhi-attendance-synchronization div h2#page-heading span')).first();
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

export class AttendanceSynchronizationUpdatePage {
  pageTitle = element(by.id('jhi-attendance-synchronization-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  toDateInput = element(by.id('field_toDate'));
  startTimeInput = element(by.id('field_startTime'));
  endTimeInput = element(by.id('field_endTime'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setFromDateInput(fromDate: string): Promise<void> {
    await this.fromDateInput.sendKeys(fromDate);
  }

  async getFromDateInput(): Promise<string> {
    return await this.fromDateInput.getAttribute('value');
  }

  async setToDateInput(toDate: string): Promise<void> {
    await this.toDateInput.sendKeys(toDate);
  }

  async getToDateInput(): Promise<string> {
    return await this.toDateInput.getAttribute('value');
  }

  async setStartTimeInput(startTime: string): Promise<void> {
    await this.startTimeInput.sendKeys(startTime);
  }

  async getStartTimeInput(): Promise<string> {
    return await this.startTimeInput.getAttribute('value');
  }

  async setEndTimeInput(endTime: string): Promise<void> {
    await this.endTimeInput.sendKeys(endTime);
  }

  async getEndTimeInput(): Promise<string> {
    return await this.endTimeInput.getAttribute('value');
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

export class AttendanceSynchronizationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-attendanceSynchronization-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-attendanceSynchronization'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
