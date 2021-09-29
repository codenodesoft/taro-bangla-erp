package software.cstl.service.dto;

import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.LeaveApplicationStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class LeaveApplicationDateMapDTO implements Serializable {

    private Long id;

    private LocalDate leaveAppliedDate;

    private LeaveApplicationStatus leaveApplicationStatus;

    private Long employeeId;

    private String employeeName;

    public LeaveApplicationDateMapDTO() {
    }

    public LeaveApplicationDateMapDTO(Long id, LocalDate leaveAppliedDate, LeaveApplicationStatus leaveApplicationStatus, Long employeeId, String employeeName) {
        this.id = id;
        this.leaveAppliedDate = leaveAppliedDate;
        this.leaveApplicationStatus = leaveApplicationStatus;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLeaveAppliedDate() {
        return leaveAppliedDate;
    }

    public void setLeaveAppliedDate(LocalDate leaveAppliedDate) {
        this.leaveAppliedDate = leaveAppliedDate;
    }

    public LeaveApplicationStatus getLeaveApplicationStatus() {
        return leaveApplicationStatus;
    }

    public void setLeaveApplicationStatus(LeaveApplicationStatus leaveApplicationStatus) {
        this.leaveApplicationStatus = leaveApplicationStatus;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveApplicationDateMapDTO)) {
            return false;
        }

        return id != null && id.equals(((LeaveApplicationDateMapDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplicationDateMapDTO{" +
            "id=" + getId() +
            ", leaveAppliedDate='" + getLeaveAppliedDate() + "'" +
            ", leaveApplicationStatus=" + getLeaveApplicationStatus() +
            ", employeeId=" + getEmployeeId() +
            ", employeeName=" + getEmployeeName() +
            "}";
    }
}
