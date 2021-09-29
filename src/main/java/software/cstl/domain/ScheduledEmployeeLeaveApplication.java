package software.cstl.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A ScheduledEmployeeLeaveApplication.
 */
@Entity
@Table(name = "sch_emp_leave_app")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ScheduledEmployeeLeaveApplication extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "leave_application_id")
    private Long leaveApplicationId;

    @Column(name = "executed_on")
    private Instant executedOn;

    @OneToMany(mappedBy = "scheduledLeaveApplication", cascade = {CascadeType.ALL})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmployeeLeaveDate> employeeLeaveDates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeaveApplicationId() {
        return leaveApplicationId;
    }

    public ScheduledEmployeeLeaveApplication leaveApplicationId(Long leaveApplicationId) {
        this.leaveApplicationId = leaveApplicationId;
        return this;
    }

    public void setLeaveApplicationId(Long leaveApplicationId) {
        this.leaveApplicationId = leaveApplicationId;
    }

    public Instant getExecutedOn() {
        return executedOn;
    }

    public ScheduledEmployeeLeaveApplication executedOn(Instant executedOn) {
        this.executedOn = executedOn;
        return this;
    }

    public void setExecutedOn(Instant executedOn) {
        this.executedOn = executedOn;
    }

    public Set<EmployeeLeaveDate> getEmployeeLeaveDates() {
        return employeeLeaveDates;
    }

    public ScheduledEmployeeLeaveApplication employeeLeaveDates(Set<EmployeeLeaveDate> employeeLeaveDates) {
        this.employeeLeaveDates = employeeLeaveDates;
        return this;
    }

    public ScheduledEmployeeLeaveApplication addEmployeeLeaveDate(EmployeeLeaveDate employeeLeaveDate) {
        this.employeeLeaveDates.add(employeeLeaveDate);
        employeeLeaveDate.setScheduledLeaveApplication(this);
        return this;
    }

    public ScheduledEmployeeLeaveApplication removeEmployeeLeaveDate(EmployeeLeaveDate employeeLeaveDate) {
        this.employeeLeaveDates.remove(employeeLeaveDate);
        employeeLeaveDate.setScheduledLeaveApplication(null);
        return this;
    }

    public void setEmployeeLeaveDates(Set<EmployeeLeaveDate> employeeLeaveDates) {
        this.employeeLeaveDates = employeeLeaveDates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduledEmployeeLeaveApplication)) {
            return false;
        }
        return id != null && id.equals(((ScheduledEmployeeLeaveApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduledEmployeeLeaveApplication{" +
            "id=" + getId() +
            ", leaveApplicationId=" + getLeaveApplicationId() +
            ", executedOn='" + getExecutedOn() + "'" +
            "}";
    }
}
