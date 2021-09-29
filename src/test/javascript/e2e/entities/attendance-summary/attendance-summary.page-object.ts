import { element, by, ElementFinder } from 'protractor';

export class AttendanceSummaryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-attendance-summary div table .btn-danger'));
  title = element.all(by.css('jhi-attendance-summary div h2#page-heading span')).first();
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

export class AttendanceSummaryUpdatePage {
  pageTitle = element(by.id('jhi-attendance-summary-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  inTimeInput = element(by.id('field_inTime'));
  outTimeInput = element(by.id('field_outTime'));
  totalHoursInput = element(by.id('field_totalHours'));
  workingHoursInput = element(by.id('field_workingHours'));
  overtimeInput = element(by.id('field_overtime'));
  attendanceTypeSelect = element(by.id('field_attendanceType'));
  attendanceStatusSelect = element(by.id('field_attendanceStatus'));
  remarksInput = element(by.id('field_remarks'));
  attendanceDateInput = element(by.id('field_attendanceDate'));

  employeeSelect = element(by.id('field_employee'));
  employeeSalarySelect = element(by.id('field_employeeSalary'));
  departmentSelect = element(by.id('field_department'));
  designationSelect = element(by.id('field_designation'));
  lineSelect = element(by.id('field_line'));
  gradeSelect = element(by.id('field_grade'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getText();
  }

  async setInTimeInput(inTime: string): Promise<void> {
    await this.inTimeInput.sendKeys(inTime);
  }

  async getInTimeInput(): Promise<string> {
    return await this.inTimeInput.getAttribute('value');
  }

  async setOutTimeInput(outTime: string): Promise<void> {
    await this.outTimeInput.sendKeys(outTime);
  }

  async getOutTimeInput(): Promise<string> {
    return await this.outTimeInput.getAttribute('value');
  }

  async setTotalHoursInput(totalHours: string): Promise<void> {
    await this.totalHoursInput.sendKeys(totalHours);
  }

  async getTotalHoursInput(): Promise<string> {
    return await this.totalHoursInput.getAttribute('value');
  }

  async setWorkingHoursInput(workingHours: string): Promise<void> {
    await this.workingHoursInput.sendKeys(workingHours);
  }

  async getWorkingHoursInput(): Promise<string> {
    return await this.workingHoursInput.getAttribute('value');
  }

  async setOvertimeInput(overtime: string): Promise<void> {
    await this.overtimeInput.sendKeys(overtime);
  }

  async getOvertimeInput(): Promise<string> {
    return await this.overtimeInput.getAttribute('value');
  }

  async setAttendanceTypeSelect(attendanceType: string): Promise<void> {
    await this.attendanceTypeSelect.sendKeys(attendanceType);
  }

  async getAttendanceTypeSelect(): Promise<string> {
    return await this.attendanceTypeSelect.element(by.css('option:checked')).getText();
  }

  async attendanceTypeSelectLastOption(): Promise<void> {
    await this.attendanceTypeSelect.all(by.tagName('option')).last().click();
  }

  async setAttendanceStatusSelect(attendanceStatus: string): Promise<void> {
    await this.attendanceStatusSelect.sendKeys(attendanceStatus);
  }

  async getAttendanceStatusSelect(): Promise<string> {
    return await this.attendanceStatusSelect.element(by.css('option:checked')).getText();
  }

  async attendanceStatusSelectLastOption(): Promise<void> {
    await this.attendanceStatusSelect.all(by.tagName('option')).last().click();
  }

  async setRemarksInput(remarks: string): Promise<void> {
    await this.remarksInput.sendKeys(remarks);
  }

  async getRemarksInput(): Promise<string> {
    return await this.remarksInput.getAttribute('value');
  }

  async setAttendanceDateInput(attendanceDate: string): Promise<void> {
    await this.attendanceDateInput.sendKeys(attendanceDate);
  }

  async getAttendanceDateInput(): Promise<string> {
    return await this.attendanceDateInput.getAttribute('value');
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

  async employeeSalarySelectLastOption(): Promise<void> {
    await this.employeeSalarySelect.all(by.tagName('option')).last().click();
  }

  async employeeSalarySelectOption(option: string): Promise<void> {
    await this.employeeSalarySelect.sendKeys(option);
  }

  getEmployeeSalarySelect(): ElementFinder {
    return this.employeeSalarySelect;
  }

  async getEmployeeSalarySelectedOption(): Promise<string> {
    return await this.employeeSalarySelect.element(by.css('option:checked')).getText();
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

  async lineSelectLastOption(): Promise<void> {
    await this.lineSelect.all(by.tagName('option')).last().click();
  }

  async lineSelectOption(option: string): Promise<void> {
    await this.lineSelect.sendKeys(option);
  }

  getLineSelect(): ElementFinder {
    return this.lineSelect;
  }

  async getLineSelectedOption(): Promise<string> {
    return await this.lineSelect.element(by.css('option:checked')).getText();
  }

  async gradeSelectLastOption(): Promise<void> {
    await this.gradeSelect.all(by.tagName('option')).last().click();
  }

  async gradeSelectOption(option: string): Promise<void> {
    await this.gradeSelect.sendKeys(option);
  }

  getGradeSelect(): ElementFinder {
    return this.gradeSelect;
  }

  async getGradeSelectedOption(): Promise<string> {
    return await this.gradeSelect.element(by.css('option:checked')).getText();
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

export class AttendanceSummaryDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-attendanceSummary-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-attendanceSummary'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
