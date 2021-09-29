package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import software.cstl.domain.enumeration.LeaveBalanceStatus;

/**
 * A LeaveBalance.
 */
@Entity
@Table(name = "leave_balance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LeaveBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_days", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalDays;

    @NotNull
    @Column(name = "remaining_days", precision = 21, scale = 2, nullable = false)
    private BigDecimal remainingDays;

    @NotNull
    @Column(name = "jhi_from", nullable = false)
    private LocalDate from;

    @NotNull
    @Column(name = "jhi_to", nullable = false)
    private LocalDate to;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "last_synchronized_on")
    private Instant lastSynchronizedOn;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeaveBalanceStatus status;

    @NotNull
    @Column(name = "assessment_year", nullable = false)
    private Integer assessmentYear;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "leaveBalances", allowSetters = true)
    private Employee employee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "leaveBalances", allowSetters = true)
    private LeaveType leaveType;

    @ManyToOne
    @JsonIgnoreProperties(value = "leaveBalances", allowSetters = true)
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties(value = "leaveBalances", allowSetters = true)
    private Designation designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalDays() {
        return totalDays;
    }

    public LeaveBalance totalDays(BigDecimal totalDays) {
        this.totalDays = totalDays;
        return this;
    }

    public void setTotalDays(BigDecimal totalDays) {
        this.totalDays = totalDays;
    }

    public BigDecimal getRemainingDays() {
        return remainingDays;
    }

    public LeaveBalance remainingDays(BigDecimal remainingDays) {
        this.remainingDays = remainingDays;
        return this;
    }

    public void setRemainingDays(BigDecimal remainingDays) {
        this.remainingDays = remainingDays;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LeaveBalance from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public LeaveBalance to(LocalDate to) {
        this.to = to;
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public String getRemarks() {
        return remarks;
    }

    public LeaveBalance remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getLastSynchronizedOn() {
        return lastSynchronizedOn;
    }

    public LeaveBalance lastSynchronizedOn(Instant lastSynchronizedOn) {
        this.lastSynchronizedOn = lastSynchronizedOn;
        return this;
    }

    public void setLastSynchronizedOn(Instant lastSynchronizedOn) {
        this.lastSynchronizedOn = lastSynchronizedOn;
    }

    public LeaveBalanceStatus getStatus() {
        return status;
    }

    public LeaveBalance status(LeaveBalanceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(LeaveBalanceStatus status) {
        this.status = status;
    }

    public Integer getAssessmentYear() {
        return assessmentYear;
    }

    public LeaveBalance assessmentYear(Integer assessmentYear) {
        this.assessmentYear = assessmentYear;
        return this;
    }

    public void setAssessmentYear(Integer assessmentYear) {
        this.assessmentYear = assessmentYear;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LeaveBalance amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveBalance employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public LeaveBalance leaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
        return this;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Department getDepartment() {
        return department;
    }

    public LeaveBalance department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public LeaveBalance designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LeaveBalance)) {
            return false;
        }
        return id != null && id.equals(((LeaveBalance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveBalance{" +
            "id=" + getId() +
            ", totalDays=" + getTotalDays() +
            ", remainingDays=" + getRemainingDays() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", lastSynchronizedOn='" + getLastSynchronizedOn() + "'" +
            ", status='" + getStatus() + "'" +
            ", assessmentYear=" + getAssessmentYear() +
            ", amount=" + getAmount() +
            "}";
    }
}
