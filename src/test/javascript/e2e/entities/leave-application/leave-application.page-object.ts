import { element, by, ElementFinder } from 'protractor';

export class LeaveApplicationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-leave-application div table .btn-danger'));
  title = element.all(by.css('jhi-leave-application div h2#page-heading span')).first();
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

export class LeaveApplicationUpdatePage {
  pageTitle = element(by.id('jhi-leave-application-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromInput = element(by.id('field_from'));
  toInput = element(by.id('field_to'));
  totalDaysInput = element(by.id('field_totalDays'));
  reasonInput = element(by.id('field_reason'));
  attachmentInput = element(by.id('file_attachment'));
  statusSelect = element(by.id('field_status'));
  appliedByInput = element(by.id('field_appliedBy'));
  appliedOnInput = element(by.id('field_appliedOn'));
  actionTakenByInput = element(by.id('field_actionTakenBy'));
  actionTakenOnInput = element(by.id('field_actionTakenOn'));

  employeeSelect = element(by.id('field_employee'));
  leaveTypeSelect = element(by.id('field_leaveType'));
  departmentSelect = element(by.id('field_department'));
  designationSelect = element(by.id('field_designation'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
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

  async setTotalDaysInput(totalDays: string): Promise<void> {
    await this.totalDaysInput.sendKeys(totalDays);
  }

  async getTotalDaysInput(): Promise<string> {
    return await this.totalDaysInput.getAttribute('value');
  }

  async setReasonInput(reason: string): Promise<void> {
    await this.reasonInput.sendKeys(reason);
  }

  async getReasonInput(): Promise<string> {
    return await this.reasonInput.getAttribute('value');
  }

  async setAttachmentInput(attachment: string): Promise<void> {
    await this.attachmentInput.sendKeys(attachment);
  }

  async getAttachmentInput(): Promise<string> {
    return await this.attachmentInput.getAttribute('value');
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

  async setAppliedByInput(appliedBy: string): Promise<void> {
    await this.appliedByInput.sendKeys(appliedBy);
  }

  async getAppliedByInput(): Promise<string> {
    return await this.appliedByInput.getAttribute('value');
  }

  async setAppliedOnInput(appliedOn: string): Promise<void> {
    await this.appliedOnInput.sendKeys(appliedOn);
  }

  async getAppliedOnInput(): Promise<string> {
    return await this.appliedOnInput.getAttribute('value');
  }

  async setActionTakenByInput(actionTakenBy: string): Promise<void> {
    await this.actionTakenByInput.sendKeys(actionTakenBy);
  }

  async getActionTakenByInput(): Promise<string> {
    return await this.actionTakenByInput.getAttribute('value');
  }

  async setActionTakenOnInput(actionTakenOn: string): Promise<void> {
    await this.actionTakenOnInput.sendKeys(actionTakenOn);
  }

  async getActionTakenOnInput(): Promise<string> {
    return await this.actionTakenOnInput.getAttribute('value');
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

export class LeaveApplicationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-leaveApplication-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-leaveApplication'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
