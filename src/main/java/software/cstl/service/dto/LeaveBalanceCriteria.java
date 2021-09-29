package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.LeaveBalanceStatus;
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
 * Criteria class for the {@link software.cstl.domain.LeaveBalance} entity. This class is used
 * in {@link software.cstl.web.rest.LeaveBalanceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /leave-balances?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeaveBalanceCriteria implements Serializable, Criteria {
    /**
     * Class for filtering LeaveBalanceStatus
     */
    public static class LeaveBalanceStatusFilter extends Filter<LeaveBalanceStatus> {

        public LeaveBalanceStatusFilter() {
        }

        public LeaveBalanceStatusFilter(LeaveBalanceStatusFilter filter) {
            super(filter);
        }

        @Override
        public LeaveBalanceStatusFilter copy() {
            return new LeaveBalanceStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter totalDays;

    private BigDecimalFilter remainingDays;

    private LocalDateFilter from;

    private LocalDateFilter to;

    private StringFilter remarks;

    private InstantFilter lastSynchronizedOn;

    private LeaveBalanceStatusFilter status;

    private IntegerFilter assessmentYear;

    private BigDecimalFilter amount;

    private LongFilter employeeId;

    private StringFilter empId;

    private LongFilter leaveTypeId;

    private LongFilter departmentId;

    private LongFilter designationId;

    public LeaveBalanceCriteria() {
    }

    public LeaveBalanceCriteria(LeaveBalanceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalDays = other.totalDays == null ? null : other.totalDays.copy();
        this.remainingDays = other.remainingDays == null ? null : other.remainingDays.copy();
        this.from = other.from == null ? null : other.from.copy();
        this.to = other.to == null ? null : other.to.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.lastSynchronizedOn = other.lastSynchronizedOn == null ? null : other.lastSynchronizedOn.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.assessmentYear = other.assessmentYear == null ? null : other.assessmentYear.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.empId = other.empId == null ? null : other.empId.copy();
        this.leaveTypeId = other.leaveTypeId == null ? null : other.leaveTypeId.copy();
        this.departmentId = other.departmentId == null ? null : other.departmentId.copy();
        this.designationId = other.designationId == null ? null : other.designationId.copy();
    }

    @Override
    public LeaveBalanceCriteria copy() {
        return new LeaveBalanceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(BigDecimalFilter totalDays) {
        this.totalDays = totalDays;
    }

    public BigDecimalFilter getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(BigDecimalFilter remainingDays) {
        this.remainingDays = remainingDays;
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

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public InstantFilter getLastSynchronizedOn() {
        return lastSynchronizedOn;
    }

    public void setLastSynchronizedOn(InstantFilter lastSynchronizedOn) {
        this.lastSynchronizedOn = lastSynchronizedOn;
    }

    public LeaveBalanceStatusFilter getStatus() {
        return status;
    }

    public void setStatus(LeaveBalanceStatusFilter status) {
        this.status = status;
    }

    public IntegerFilter getAssessmentYear() {
        return assessmentYear;
    }

    public void setAssessmentYear(IntegerFilter assessmentYear) {
        this.assessmentYear = assessmentYear;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
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
        final LeaveBalanceCriteria that = (LeaveBalanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(totalDays, that.totalDays) &&
                Objects.equals(remainingDays, that.remainingDays) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(lastSynchronizedOn, that.lastSynchronizedOn) &&
                Objects.equals(status, that.status) &&
                Objects.equals(assessmentYear, that.assessmentYear) &&
                Objects.equals(amount, that.amount) &&
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
            totalDays,
            remainingDays,
            from,
            to,
            remarks,
            lastSynchronizedOn,
            status,
            assessmentYear,
            amount,
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
        return "LeaveBalanceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (totalDays != null ? "totalDays=" + totalDays + ", " : "") +
            (remainingDays != null ? "remainingDays=" + remainingDays + ", " : "") +
            (from != null ? "from=" + from + ", " : "") +
            (to != null ? "to=" + to + ", " : "") +
            (remarks != null ? "remarks=" + remarks + ", " : "") +
            (lastSynchronizedOn != null ? "lastSynchronizedOn=" + lastSynchronizedOn + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (assessmentYear != null ? "assessmentYear=" + assessmentYear + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (empId != null ? "empId=" + empId + ", " : "") +
            (leaveTypeId != null ? "leaveTypeId=" + leaveTypeId + ", " : "") +
            (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            (designationId != null ? "designationId=" + designationId + ", " : "") +
            "}";
    }

}
