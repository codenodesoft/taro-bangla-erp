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

import software.cstl.domain.enumeration.LeaveApplicationStatus;

/**
 * A LeaveApplication.
 */
@Entity
@Table(name = "leave_application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LeaveApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_from", nullable = false)
    private LocalDate from;

    @NotNull
    @Column(name = "jhi_to", nullable = false)
    private LocalDate to;

    @NotNull
    @Column(name = "total_days", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalDays;

    @Lob
    @Column(name = "reason")
    private String reason;

    @Lob
    @Column(name = "attachment")
    private byte[] attachment;

    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeaveApplicationStatus status;

    @Column(name = "applied_by")
    private String appliedBy;

    @Column(name = "applied_on")
    private Instant appliedOn;

    @Column(name = "action_taken_by")
    private String actionTakenBy;

    @Column(name = "action_taken_on")
    private Instant actionTakenOn;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "leaveApplications", allowSetters = true)
    private Employee employee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "leaveApplications", allowSetters = true)
    private LeaveType leaveType;

    @ManyToOne
    @JsonIgnoreProperties(value = "leaveApplications", allowSetters = true)
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties(value = "leaveApplications", allowSetters = true)
    private Designation designation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LeaveApplication from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public LeaveApplication to(LocalDate to) {
        this.to = to;
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public BigDecimal getTotalDays() {
        return totalDays;
    }

    public LeaveApplication totalDays(BigDecimal totalDays) {
        this.totalDays = totalDays;
        return this;
    }

    public void setTotalDays(BigDecimal totalDays) {
        this.totalDays = totalDays;
    }

    public String getReason() {
        return reason;
    }

    public LeaveApplication reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public LeaveApplication attachment(byte[] attachment) {
        this.attachment = attachment;
        return this;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentContentType() {
        return attachmentContentType;
    }

    public LeaveApplication attachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
        return this;
    }

    public void setAttachmentContentType(String attachmentContentType) {
        this.attachmentContentType = attachmentContentType;
    }

    public LeaveApplicationStatus getStatus() {
        return status;
    }

    public LeaveApplication status(LeaveApplicationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(LeaveApplicationStatus status) {
        this.status = status;
    }

    public String getAppliedBy() {
        return appliedBy;
    }

    public LeaveApplication appliedBy(String appliedBy) {
        this.appliedBy = appliedBy;
        return this;
    }

    public void setAppliedBy(String appliedBy) {
        this.appliedBy = appliedBy;
    }

    public Instant getAppliedOn() {
        return appliedOn;
    }

    public LeaveApplication appliedOn(Instant appliedOn) {
        this.appliedOn = appliedOn;
        return this;
    }

    public void setAppliedOn(Instant appliedOn) {
        this.appliedOn = appliedOn;
    }

    public String getActionTakenBy() {
        return actionTakenBy;
    }

    public LeaveApplication actionTakenBy(String actionTakenBy) {
        this.actionTakenBy = actionTakenBy;
        return this;
    }

    public void setActionTakenBy(String actionTakenBy) {
        this.actionTakenBy = actionTakenBy;
    }

    public Instant getActionTakenOn() {
        return actionTakenOn;
    }

    public LeaveApplication actionTakenOn(Instant actionTakenOn) {
        this.actionTakenOn = actionTakenOn;
        return this;
    }

    public void setActionTakenOn(Instant actionTakenOn) {
        this.actionTakenOn = actionTakenOn;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveApplication employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public LeaveApplication leaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
        return this;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public Department getDepartment() {
        return department;
    }

    public LeaveApplication department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public LeaveApplication designation(Designation designation) {
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
        if (!(o instanceof LeaveApplication)) {
            return false;
        }
        return id != null && id.equals(((LeaveApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveApplication{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", totalDays=" + getTotalDays() +
            ", reason='" + getReason() + "'" +
            ", attachment='" + getAttachment() + "'" +
            ", attachmentContentType='" + getAttachmentContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", appliedBy='" + getAppliedBy() + "'" +
            ", appliedOn='" + getAppliedOn() + "'" +
            ", actionTakenBy='" + getActionTakenBy() + "'" +
            ", actionTakenOn='" + getActionTakenOn() + "'" +
            "}";
    }
}
