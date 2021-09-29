import { element, by, ElementFinder } from 'protractor';

export class AttendanceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-attendance div table .btn-danger'));
  title = element.all(by.css('jhi-attendance div h2#page-heading span')).first();
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

export class AttendanceUpdatePage {
  pageTitle = element(by.id('jhi-attendance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  machineNoInput = element(by.id('field_machineNo'));
  employeeMachineIdInput = element(by.id('field_employeeMachineId'));
  attendanceDateTimeInput = element(by.id('field_attendanceDateTime'));
  remarksInput = element(by.id('field_remarks'));

  attendanceDataUploadSelect = element(by.id('field_attendanceDataUpload'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setMachineNoInput(machineNo: string): Promise<void> {
    await this.machineNoInput.sendKeys(machineNo);
  }

  async getMachineNoInput(): Promise<string> {
    return await this.machineNoInput.getAttribute('value');
  }

  async setEmployeeMachineIdInput(employeeMachineId: string): Promise<void> {
    await this.employeeMachineIdInput.sendKeys(employeeMachineId);
  }

  async getEmployeeMachineIdInput(): Promise<string> {
    return await this.employeeMachineIdInput.getAttribute('value');
  }

  async setAttendanceDateTimeInput(attendanceDateTime: string): Promise<void> {
    await this.attendanceDateTimeInput.sendKeys(attendanceDateTime);
  }

  async getAttendanceDateTimeInput(): Promise<string> {
    return await this.attendanceDateTimeInput.getAttribute('value');
  }

  async setRemarksInput(remarks: string): Promise<void> {
    await this.remarksInput.sendKeys(remarks);
  }

  async getRemarksInput(): Promise<string> {
    return await this.remarksInput.getAttribute('value');
  }

  async attendanceDataUploadSelectLastOption(): Promise<void> {
    await this.attendanceDataUploadSelect.all(by.tagName('option')).last().click();
  }

  async attendanceDataUploadSelectOption(option: string): Promise<void> {
    await this.attendanceDataUploadSelect.sendKeys(option);
  }

  getAttendanceDataUploadSelect(): ElementFinder {
    return this.attendanceDataUploadSelect;
  }

  async getAttendanceDataUploadSelectedOption(): Promise<string> {
    return await this.attendanceDataUploadSelect.element(by.css('option:checked')).getText();
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

export class AttendanceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-attendance-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-attendance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
