package software.cstl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.Duration;

import software.cstl.domain.enumeration.AttendanceType;

import software.cstl.domain.enumeration.AttendanceStatus;

/**
 * A AttendanceSummary.
 */
@Entity
@Table(name = "attendance_summary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttendanceSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "in_time")
    private ZonedDateTime inTime;

    @Column(name = "out_time")
    private ZonedDateTime outTime;

    @Column(name = "total_hours")
    private Duration totalHours;

    @Column(name = "working_hours")
    private Duration workingHours;

    @Column(name = "overtime")
    private Duration overtime;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_type")
    private AttendanceType attendanceType;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_status")
    private AttendanceStatus attendanceStatus;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendanceSummaries", allowSetters = true)
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendanceSummaries", allowSetters = true)
    private EmployeeSalary employeeSalary;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendanceSummaries", allowSetters = true)
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendanceSummaries", allowSetters = true)
    private Designation designation;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendanceSummaries", allowSetters = true)
    private Line line;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendanceSummaries", allowSetters = true)
    private Grade grade;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getInTime() {
        return inTime;
    }

    public AttendanceSummary inTime(ZonedDateTime inTime) {
        this.inTime = inTime;
        return this;
    }

    public void setInTime(ZonedDateTime inTime) {
        this.inTime = inTime;
    }

    public ZonedDateTime getOutTime() {
        return outTime;
    }

    public AttendanceSummary outTime(ZonedDateTime outTime) {
        this.outTime = outTime;
        return this;
    }

    public void setOutTime(ZonedDateTime outTime) {
        this.outTime = outTime;
    }

    public Duration getTotalHours() {
        return totalHours;
    }

    public AttendanceSummary totalHours(Duration totalHours) {
        this.totalHours = totalHours;
        return this;
    }

    public void setTotalHours(Duration totalHours) {
        this.totalHours = totalHours;
    }

    public Duration getWorkingHours() {
        return workingHours;
    }

    public AttendanceSummary workingHours(Duration workingHours) {
        this.workingHours = workingHours;
        return this;
    }

    public void setWorkingHours(Duration workingHours) {
        this.workingHours = workingHours;
    }

    public Duration getOvertime() {
        return overtime;
    }

    public AttendanceSummary overtime(Duration overtime) {
        this.overtime = overtime;
        return this;
    }

    public void setOvertime(Duration overtime) {
        this.overtime = overtime;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    public AttendanceSummary attendanceType(AttendanceType attendanceType) {
        this.attendanceType = attendanceType;
        return this;
    }

    public void setAttendanceType(AttendanceType attendanceType) {
        this.attendanceType = attendanceType;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    public AttendanceSummary attendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
        return this;
    }

    public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public AttendanceSummary remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public AttendanceSummary attendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
        return this;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public AttendanceSummary employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeSalary getEmployeeSalary() {
        return employeeSalary;
    }

    public AttendanceSummary employeeSalary(EmployeeSalary employeeSalary) {
        this.employeeSalary = employeeSalary;
        return this;
    }

    public void setEmployeeSalary(EmployeeSalary employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public Department getDepartment() {
        return department;
    }

    public AttendanceSummary department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public AttendanceSummary designation(Designation designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(Designation designation) {
        this.designation = designation;
    }

    public Line getLine() {
        return line;
    }

    public AttendanceSummary line(Line line) {
        this.line = line;
        return this;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Grade getGrade() {
        return grade;
    }

    public AttendanceSummary grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendanceSummary)) {
            return false;
        }
        return id != null && id.equals(((AttendanceSummary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendanceSummary{" +
            "id=" + getId() +
            ", inTime='" + getInTime() + "'" +
            ", outTime='" + getOutTime() + "'" +
            ", totalHours='" + getTotalHours() + "'" +
            ", workingHours='" + getWorkingHours() + "'" +
            ", overtime='" + getOvertime() + "'" +
            ", attendanceType='" + getAttendanceType() + "'" +
            ", attendanceStatus='" + getAttendanceStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", attendanceDate='" + getAttendanceDate() + "'" +
            "}";
    }
}
