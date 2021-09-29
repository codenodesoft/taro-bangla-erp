package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.AttendanceType;
import software.cstl.domain.enumeration.AttendanceStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.DurationFilter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link software.cstl.domain.AttendanceSummary} entity. This class is used
 * in {@link software.cstl.web.rest.AttendanceSummaryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attendance-summaries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttendanceSummaryCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AttendanceType
     */
    public static class AttendanceTypeFilter extends Filter<AttendanceType> {

        public AttendanceTypeFilter() {
        }

        public AttendanceTypeFilter(AttendanceTypeFilter filter) {
            super(filter);
        }

        @Override
        public AttendanceTypeFilter copy() {
            return new AttendanceTypeFilter(this);
        }

    }

    /**
     * Class for filtering AttendanceStatus
     */
    public static class AttendanceStatusFilter extends Filter<AttendanceStatus> {

        public AttendanceStatusFilter() {
        }

        public AttendanceStatusFilter(AttendanceStatusFilter filter) {
            super(filter);
        }

        @Override
        public AttendanceStatusFilter copy() {
            return new AttendanceStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter inTime;

    private ZonedDateTimeFilter outTime;

    private DurationFilter totalHours;

    private DurationFilter workingHours;

    private DurationFilter overtime;

    private AttendanceTypeFilter attendanceType;

    private AttendanceStatusFilter attendanceStatus;

    private StringFilter remarks;

    private LocalDateFilter attendanceDate;

    private LongFilter employeeId;

    private StringFilter empId;

    private LongFilter employeeSalaryId;

    private LongFilter departmentId;

    private LongFilter designationId;

    private LongFilter lineId;

    private LongFilter gradeId;

    public AttendanceSummaryCriteria() {
    }

    public AttendanceSummaryCriteria(AttendanceSummaryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.inTime = other.inTime == null ? null : other.inTime.copy();
        this.outTime = other.outTime == null ? null : other.outTime.copy();
        this.totalHours = other.totalHours == null ? null : other.totalHours.copy();
        this.workingHours = other.workingHours == null ? null : other.workingHours.copy();
        this.overtime = other.overtime == null ? null : other.overtime.copy();
        this.attendanceType = other.attendanceType == null ? null : other.attendanceType.copy();
        this.attendanceStatus = other.attendanceStatus == null ? null : other.attendanceStatus.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.attendanceDate = other.attendanceDate == null ? null : other.attendanceDate.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.empId = other.empId == null ? null : other.empId.copy();
        this.employeeSalaryId = other.employeeSalaryId == null ? null : other.employeeSalaryId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
        this.lineId = other.lineId == null ? null : other.lineId.copy();
        this.gradeId = other.gradeId == null ? null : other.gradeId.copy();
    }

    @Override
    public AttendanceSummaryCriteria copy() {
        return new AttendanceSummaryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getInTime() {
        return inTime;
    }

    public void setInTime(ZonedDateTimeFilter inTime) {
        this.inTime = inTime;
    }

    public ZonedDateTimeFilter getOutTime() {
        return outTime;
    }

    public void setOutTime(ZonedDateTimeFilter outTime) {
        this.outTime = outTime;
    }

    public DurationFilter getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(DurationFilter totalHours) {
        this.totalHours = totalHours;
    }

    public DurationFilter getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(DurationFilter workingHours) {
        this.workingHours = workingHours;
    }

    public DurationFilter getOvertime() {
        return overtime;
    }

    public void setOvertime(DurationFilter overtime) {
        this.overtime = overtime;
    }

    public AttendanceTypeFilter getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(AttendanceTypeFilter attendanceType) {
        this.attendanceType = attendanceType;
    }

    public AttendanceStatusFilter getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(AttendanceStatusFilter attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LocalDateFilter getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDateFilter attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getEmployeeSalaryId() {
        return employeeSalaryId;
    }

    public void setEmployeeSalaryId(LongFilter employeeSalaryId) {
        this.employeeSalaryId = employeeSalaryId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public LongFilter getDesignationId() {
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }

    public LongFilter getLineId() {
        return lineId;
    }

    public void setLineId(LongFilter lineId) {
        this.lineId = lineId;
    }

    public LongFilter getGradeId() {
        return gradeId;
    }

    public void setGradeId(LongFilter gradeId) {
        this.gradeId = gradeId;
    }


    public StringFilter getEmpId() {
        return empId;
    }

    public void setEmpId(StringFilter empId) {
        this.empId = empId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceSummaryCriteria that = (AttendanceSummaryCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(inTime, that.inTime) &&
                Objects.equals(outTime, that.outTime) &&
                Objects.equals(totalHours, that.totalHours) &&
                Objects.equals(workingHours, that.workingHours) &&
                Objects.equals(overtime, that.overtime) &&
                Objects.equals(attendanceType, that.attendanceType) &&
                Objects.equals(attendanceStatus, that.attendanceStatus) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(attendanceDate, that.attendanceDate) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(empId, that.empId) &&
                Objects.equals(employeeSalaryId, that.employeeSalaryId) &&
                Objects.equals(departmentId, that.departmentId) &&
                Objects.equals(designationId, that.designationId) &&
                Objects.equals(lineId, that.lineId) &&
                Objects.equals(gradeId, that.gradeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            inTime,
            outTime,
            totalHours,
            workingHours,
            overtime,
            attendanceType,
            attendanceStatus,
            remarks,
            attendanceDate,
            employeeId,
            empId,
            employeeSalaryId,
            departmentId,
            designationId,
            lineId,
            gradeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceSummaryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (inTime != null ? "inTime=" + inTime + ", " : "") +
            (outTime != null ? "outTime=" + outTime + ", " : "") +
            (totalHours != null ? "totalHours=" + totalHours + ", " : "") +
            (workingHours != null ? "workingHours=" + workingHours + ", " : "") +
            (overtime != null ? "overtime=" + overtime + ", " : "") +
            (attendanceType != null ? "attendanceType=" + attendanceType + ", " : "") +
            (attendanceStatus != null ? "attendanceStatus=" + attendanceStatus + ", " : "") +
            (remarks != null ? "remarks=" + remarks + ", " : "") +
            (attendanceDate != null ? "attendanceDate=" + attendanceDate + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (empId != null ? "empId=" + empId + ", " : "") +
            (employeeSalaryId != null ? "employeeSalaryId=" + employeeSalaryId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (designationId != null ? "designationId=" + designationId + ", " : "") +
            (lineId != null ? "lineId=" + lineId + ", " : "") +
            (gradeId != null ? "gradeId=" + gradeId + ", " : "") +
            "}";
    }

}
