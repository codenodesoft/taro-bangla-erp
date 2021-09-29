package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Attendance.
 */
@Entity
@Table(name = "attendance")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "machine_no", nullable = false)
    private String machineNo;

    @NotNull
    @Column(name = "employee_machine_id", nullable = false)
    private String employeeMachineId;

    @NotNull
    @Column(name = "attendance_date_time", nullable = false)
    private ZonedDateTime attendanceDateTime;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendances", allowSetters = true)
    private AttendanceDataUpload attendanceDataUpload;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public Attendance machineNo(String machineNo) {
        this.machineNo = machineNo;
        return this;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getEmployeeMachineId() {
        return employeeMachineId;
    }

    public Attendance employeeMachineId(String employeeMachineId) {
        this.employeeMachineId = employeeMachineId;
        return this;
    }

    public void setEmployeeMachineId(String employeeMachineId) {
        this.employeeMachineId = employeeMachineId;
    }

    public ZonedDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public Attendance attendanceDateTime(ZonedDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
        return this;
    }

    public void setAttendanceDateTime(ZonedDateTime attendanceDateTime) {
        this.attendanceDateTime = attendanceDateTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public Attendance remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public AttendanceDataUpload getAttendanceDataUpload() {
        return attendanceDataUpload;
    }

    public Attendance attendanceDataUpload(AttendanceDataUpload attendanceDataUpload) {
        this.attendanceDataUpload = attendanceDataUpload;
        return this;
    }

    public void setAttendanceDataUpload(AttendanceDataUpload attendanceDataUpload) {
        this.attendanceDataUpload = attendanceDataUpload;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendance)) {
            return false;
        }
        return id != null && id.equals(((Attendance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attendance{" +
            "id=" + getId() +
            ", machineNo='" + getMachineNo() + "'" +
            ", employeeMachineId='" + getEmployeeMachineId() + "'" +
            ", attendanceDateTime='" + getAttendanceDateTime() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
