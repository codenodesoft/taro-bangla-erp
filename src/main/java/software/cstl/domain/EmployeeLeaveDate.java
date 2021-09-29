package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EmployeeLeaveDate.
 */
@Entity
@Table(name = "employee_leave_date")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeLeaveDate extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "leave_date")
    private LocalDate leaveDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "employeeLeaveDates", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "employeeLeaveDates", allowSetters = true)
    private ScheduledEmployeeLeaveApplication scheduledLeaveApplication;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public EmployeeLeaveDate leaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
        return this;
    }

    public void setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public EmployeeLeaveDate employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ScheduledEmployeeLeaveApplication getScheduledLeaveApplication() {
        return scheduledLeaveApplication;
    }

    public EmployeeLeaveDate scheduledLeaveApplication(ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication) {
        this.scheduledLeaveApplication = scheduledEmployeeLeaveApplication;
        return this;
    }

    public void setScheduledLeaveApplication(ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication) {
        this.scheduledLeaveApplication = scheduledEmployeeLeaveApplication;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeLeaveDate)) {
            return false;
        }
        return id != null && id.equals(((EmployeeLeaveDate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeLeaveDate{" +
            "id=" + getId() +
            ", leaveDate='" + getLeaveDate() + "'" +
            "}";
    }
}
