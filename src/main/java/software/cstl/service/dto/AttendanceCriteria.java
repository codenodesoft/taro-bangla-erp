package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link software.cstl.domain.Attendance} entity. This class is used
 * in {@link software.cstl.web.rest.AttendanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /attendances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttendanceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter machineNo;

    private StringFilter employeeMachineId;

    private ZonedDateTimeFilter attendanceDateTime;

    private StringFilter remarks;

    private LongFilter attendanceDataUploadId;

    public AttendanceCriteria() {
    }

    public AttendanceCriteria(AttendanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.machineNo = other.machineNo == null ? null : other.machineNo.copy();
        this.employeeMachineId = other.employeeMachineId == null ? null : other.employeeMachineId.copy();
        this.attendanceDateTime = other.attendanceDateTime == null ? null : other.attendanceDateTime.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.attendanceDataUploadId = other.attendanceDataUploadId == null ? null : other.attendanceDataUploadId.copy();
    }

    @Override
    public AttendanceCriteria copy() {
        return new AttendanceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(StringFilter machineNo) {
        this.machineNo = machineNo;
    }

    public StringFilter getEmployeeMachineId() {
        return employeeMachineId;
    }

    public void setEmployeeMachineId(StringFilter employeeMachineId) {
        this.employeeMachineId = employeeMachineId;
    }

    public ZonedDateTimeFilter getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public void setAttendanceDateTime(ZonedDateTimeFilter attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getAttendanceDataUploadId() {
        return attendanceDataUploadId;
    }

    public void setAttendanceDataUploadId(LongFilter attendanceDataUploadId) {
        this.attendanceDataUploadId = attendanceDataUploadId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AttendanceCriteria that = (AttendanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(machineNo, that.machineNo) &&
            Objects.equals(employeeMachineId, that.employeeMachineId) &&
            Objects.equals(attendanceDateTime, that.attendanceDateTime) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(attendanceDataUploadId, that.attendanceDataUploadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        machineNo,
        employeeMachineId,
        attendanceDateTime,
        remarks,
        attendanceDataUploadId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (machineNo != null ? "machineNo=" + machineNo + ", " : "") +
                (employeeMachineId != null ? "employeeMachineId=" + employeeMachineId + ", " : "") +
                (attendanceDateTime != null ? "attendanceDateTime=" + attendanceDateTime + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (attendanceDataUploadId != null ? "attendanceDataUploadId=" + attendanceDataUploadId + ", " : "") +
            "}";
    }

}
