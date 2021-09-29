package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.LeaveApplicationStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link software.cstl.domain.LeaveApplication} entity. This class is used
 * in {@link software.cstl.web.rest.LeaveApplicationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeaveApplicationCriteria implements Serializable, Criteria {
    /**
     * Class for filtering LeaveApplicationStatus
     */
    public static class LeaveApplicationStatusFilter extends Filter<LeaveApplicationStatus> {

        public LeaveApplicationStatusFilter() {
        }

        public LeaveApplicationStatusFilter(LeaveApplicationStatusFilter filter) {
            super(filter);
        }

        @Override
        public LeaveApplicationStatusFilter copy() {
            return new LeaveApplicationStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter from;

    private LocalDateFilter to;

    private BigDecimalFilter totalDays;

    private LeaveApplicationStatusFilter status;

    private StringFilter appliedBy;

    private InstantFilter appliedOn;

    private StringFilter actionTakenBy;

    private InstantFilter actionTakenOn;

    private LongFilter employeeId;

    private StringFilter empId;

    private LongFilter leaveTypeId;

    private LongFilter departmentId;

    private LongFilter designationId;

    public LeaveApplicationCriteria() {
    }

    public LeaveApplicationCriteria(LeaveApplicationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.from = other.from == null ? null : other.from.copy();
        this.to = other.to == null ? null : other.to.copy();
        this.totalDays = other.totalDays == null ? null : other.totalDays.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.appliedBy = other.appliedBy == null ? null : other.appliedBy.copy();
        this.appliedOn = other.appliedOn == null ? null : other.appliedOn.copy();
        this.actionTakenBy = other.actionTakenBy == null ? null : other.actionTakenBy.copy();
        this.actionTakenOn = other.actionTakenOn == null ? null : other.actionTakenOn.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.empId = other.empId == null ? null : other.empId.copy();
        this.leaveTypeId = other.leaveTypeId == null ? null : other.leaveTypeId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
    }

    @Override
    public LeaveApplicationCriteria copy() {
        return new LeaveApplicationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFrom() {
        return from;
    }

    public void setFrom(LocalDateFilter from) {
        this.from = from;
    }

    public LocalDateFilter getTo() {
        return to;
    }

    public void setTo(LocalDateFilter to) {
        this.to = to;
    }

    public BigDecimalFilter getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(BigDecimalFilter totalDays) {
        this.totalDays = totalDays;
    }

    public LeaveApplicationStatusFilter getStatus() {
        return status;
    }

    public void setStatus(LeaveApplicationStatusFilter status) {
        this.status = status;
    }

    public StringFilter getAppliedBy() {
        return appliedBy;
    }

    public void setAppliedBy(StringFilter appliedBy) {
        this.appliedBy = appliedBy;
    }

    public InstantFilter getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(InstantFilter appliedOn) {
        this.appliedOn = appliedOn;
    }

    public StringFilter getActionTakenBy() {
        return actionTakenBy;
    }

    public void setActionTakenBy(StringFilter actionTakenBy) {
        this.actionTakenBy = actionTakenBy;
    }

    public InstantFilter getActionTakenOn() {
        return actionTakenOn;
    }

    public void setActionTakenOn(InstantFilter actionTakenOn) {
        this.actionTakenOn = actionTakenOn;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(LongFilter leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
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
        final LeaveApplicationCriteria that = (LeaveApplicationCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(totalDays, that.totalDays) &&
                Objects.equals(status, that.status) &&
                Objects.equals(appliedBy, that.appliedBy) &&
                Objects.equals(appliedOn, that.appliedOn) &&
                Objects.equals(actionTakenBy, that.actionTakenBy) &&
                Objects.equals(actionTakenOn, that.actionTakenOn) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(empId, that.empId) &&
                Objects.equals(leaveTypeId, that.leaveTypeId) &&
                Objects.equals(departmentId, that.departmentId) &&
                Objects.equals(designationId, that.designationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            from,
            to,
            totalDays,
            status,
            appliedBy,
            appliedOn,
            actionTakenBy,
            actionTakenOn,
            employeeId,
            empId,
            leaveTypeId,
            departmentId,
            designationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplicationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (from != null ? "from=" + from + ", " : "") +
            (to != null ? "to=" + to + ", " : "") +
            (totalDays != null ? "totalDays=" + totalDays + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (appliedBy != null ? "appliedBy=" + appliedBy + ", " : "") +
            (appliedOn != null ? "appliedOn=" + appliedOn + ", " : "") +
            (actionTakenBy != null ? "actionTakenBy=" + actionTakenBy + ", " : "") +
            (actionTakenOn != null ? "actionTakenOn=" + actionTakenOn + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (empId != null ? "empId=" + empId + ", " : "") +
            (leaveTypeId != null ? "leaveTypeId=" + leaveTypeId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (designationId != null ? "designationId=" + designationId + ", " : "") +
            "}";
    }

}
