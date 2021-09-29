package software.cstl.service;

import org.checkerframework.checker.units.qual.A;
import software.cstl.domain.Attendance;
import software.cstl.domain.AttendanceSummary;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.enumeration.*;
import software.cstl.repository.AttendanceSummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.repository.EmployeeRepository;
import software.cstl.repository.EmployeeSalaryRepository;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AttendanceSummary}.
 */
@Service
@Transactional
public class AttendanceSummaryService {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryService.class);

    private final AttendanceSummaryRepository attendanceSummaryRepository;

    private final AttendanceService attendanceService;

    private final EmployeeRepository employeeRepository;

    private final EmployeeSalaryRepository employeeSalaryRepository;

    private final WeekendDateMapService weekendDateMapService;

    private final HolidayDateMapService holidayDateMapService;

    private final LeaveApplicationDateMapService leaveApplicationDateMapService;

    public AttendanceSummaryService(AttendanceSummaryRepository attendanceSummaryRepository, AttendanceService attendanceService, EmployeeRepository employeeRepository, EmployeeSalaryRepository employeeSalaryRepository, WeekendDateMapService weekendDateMapService, HolidayDateMapService holidayDateMapService, LeaveApplicationDateMapService leaveApplicationDateMapService) {
        this.attendanceSummaryRepository = attendanceSummaryRepository;
        this.attendanceService = attendanceService;
        this.employeeRepository = employeeRepository;
        this.employeeSalaryRepository = employeeSalaryRepository;
        this.weekendDateMapService = weekendDateMapService;
        this.holidayDateMapService = holidayDateMapService;
        this.leaveApplicationDateMapService = leaveApplicationDateMapService;
    }

    /**
     * Save a attendanceSummary.
     *
     * @param attendanceSummary the entity to save.
     * @return the persisted entity.
     */
    public AttendanceSummary save(AttendanceSummary attendanceSummary) {
        log.debug("Request to save AttendanceSummary : {}", attendanceSummary);
        return attendanceSummaryRepository.save(attendanceSummary);
    }

    /**
     * Save list of attendanceSummaries.
     *
     * @param attendanceSummaries the list of entities to save.
     * @return the list of persisted entities.
     */
    public List<AttendanceSummary> save(List<AttendanceSummary> attendanceSummaries) {
        log.debug("Request to save bulk AttendanceSummary : {}", attendanceSummaries);
        return attendanceSummaryRepository.saveAll(attendanceSummaries);
    }

    /**
     * Get all the attendanceSummaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceSummary> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceSummaries");
        return attendanceSummaryRepository.findAll(pageable);
    }


    /**
     * Get one attendanceSummary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceSummary> findOne(Long id) {
        log.debug("Request to get AttendanceSummary : {}", id);
        return attendanceSummaryRepository.findById(id);
    }

    /**
     * Delete the attendanceSummary by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttendanceSummary : {}", id);
        attendanceSummaryRepository.deleteById(id);
    }

    /**
     * Sync attendanceSummary.
     *
     * @param localDate the date of synchronization.
     */
    @Transactional
    public void syncAttendanceSummary(LocalDate localDate) {
        log.debug("Request to sync AttendanceSummary : {}", localDate);

        List<Employee> employees = employeeRepository.findAllByStatusEqualsAndJoiningDateLessThanEqual(EmployeeStatus.ACTIVE, localDate);
        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository.findAllByEmployeeInAndStatusEquals(employees, ActiveStatus.ACTIVE);
        boolean isWeekend = weekendDateMapService.isWeekend(localDate);
        boolean isHoliday = holidayDateMapService.isHoliday(localDate);
        ZonedDateTime startFrameTime = getZonedDateTime(localDate, 6, 0, 0);
        ZonedDateTime endFrameTime = getZonedDateTime(localDate.plusDays(1), 5, 59, 59);
        List<AttendanceSummary> summaries = attendanceSummaryRepository.findAllByAttendanceDateEquals(localDate);
        List<Attendance> attendances = attendanceService.findAllByAttendanceDateTimeBetween(startFrameTime, endFrameTime);
        List<AttendanceSummary> attendanceSummaries = new ArrayList<>();

        for (Employee employee : employees) {
            List<Attendance> list = findAttendances(attendances, employee);
            EmployeeSalary employeeSalary = findEmployeeSalary(employeeSalaries, employee);
            boolean isLeaveTaken = leaveApplicationDateMapService.isLeaveTaken(employee, localDate);
            AttendanceSummary attendanceSummary = getAttendanceSummary(summaries, isWeekend, isHoliday, isLeaveTaken, employee, list, localDate, employeeSalary);
            attendanceSummaries.add(attendanceSummary);
        }
        save(attendanceSummaries);
    }

    private ZonedDateTime getZonedDateTime(LocalDate localDate, int hour, int minute, int second) {
        return ZonedDateTime.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), hour, minute, second, 0, ZoneId.of("Asia/Dhaka"));
    }

    private List<Attendance> findAttendances(List<Attendance> attendances, Employee employee) {
        List<Attendance> attendanceList = new ArrayList<>();
        if (employee.getAttendanceMachineId() != null) {
            for (Attendance attendance : attendances) {
                if (employee.getAttendanceMachineId().equals(attendance.getEmployeeMachineId())) {
                    attendanceList.add(attendance);
                }
            }
        }
        return attendanceList;
    }

    private EmployeeSalary findEmployeeSalary(List<EmployeeSalary> employeeSalaries, Employee employee) {
        for (EmployeeSalary employeeSalary : employeeSalaries) {
            if (employeeSalary.getEmployee() != null) {
                if (employeeSalary.getEmployee().getId().equals(employee.getId())) {
                    return employeeSalary;
                }
            }
        }
        return null;
    }

    private EmployeeSalary findEmployeeSalary(Employee employee, LocalDate localDate) {
        List<Employee> employees = employeeRepository.findAllByStatusEqualsAndJoiningDateLessThanEqual(EmployeeStatus.ACTIVE, localDate);
        List<EmployeeSalary> employeeSalaries = employeeSalaryRepository.findAllByEmployeeInAndStatusEquals(employees, ActiveStatus.ACTIVE);

        for (EmployeeSalary employeeSalary : employeeSalaries) {
            if (employeeSalary.getEmployee().getId().equals(employee.getId())) {
                return employeeSalary;
            }
        }
        return null;
    }

    private AttendanceSummary getAttendanceSummary(List<AttendanceSummary> summaries, boolean isWeekend, boolean isHoliday, boolean isLeaveTaken, Employee employee, List<Attendance> list, LocalDate attendanceDate, EmployeeSalary employeeSalary) {
        AttendanceSummary currentRecord = exists(summaries, employee);
        Long id = currentRecord == null ? null : currentRecord.getId();
        ZonedDateTime inTime = findInTime(list);
        ZonedDateTime outTime = findOutTime(list, inTime);
        Duration totalHours = calculateTotalHours(inTime, outTime);
        Duration workingHours = calculateWorkingHours(inTime, outTime);
        Duration overTime = calculateOverTime(employee, inTime, outTime);
        AttendanceType attendanceType = determineAttendanceType(isWeekend, isHoliday, isLeaveTaken);
        AttendanceStatus attendanceStatus = determineAttendanceStatus(inTime, outTime, attendanceType);
        String text = "SG: ("+ Instant.now() + ": " + list.stream().map(Attendance::getId).collect(Collectors.toList()) + ")";
        String remarks = currentRecord == null ? text : currentRecord.getRemarks() + ", " + text;
        return build(id, attendanceDate, inTime, outTime, totalHours, workingHours, overTime, attendanceType, attendanceStatus, employee, employeeSalary, remarks);
    }

    private AttendanceSummary exists(List<AttendanceSummary> summaries, Employee employee) {
        for (AttendanceSummary attendanceSummary : summaries) {
            if (attendanceSummary.getEmployee().getId().equals(employee.getId())) {
                return attendanceSummary;
            }
        }
        return null;
    }

    private ZonedDateTime findInTime(List<Attendance> attendances) {
        ZonedDateTime inTime = attendances.size() > 0 ? attendances.get(0).getAttendanceDateTime() : null;
        for (Attendance attendance : attendances) {
            if (attendance.getAttendanceDateTime().isBefore(inTime))
                inTime = attendance.getAttendanceDateTime();
        }
        return inTime;
    }

    private ZonedDateTime findOutTime(List<Attendance> attendances, ZonedDateTime inTime) {
        ZonedDateTime outTime = attendances.size() > 0 ? attendances.get(0).getAttendanceDateTime() : null;
        for (Attendance attendance : attendances) {
            if (attendance.getAttendanceDateTime().isAfter(outTime))
                outTime = attendance.getAttendanceDateTime();
        }
        return inTime == null ? null : inTime.equals(outTime) ? null : outTime;
    }

    private Duration calculateTotalHours(ZonedDateTime inTime, ZonedDateTime outTime) {
        if (inTime == null || outTime == null)
            return null;
        else {
            return Duration.between(inTime, outTime);
        }
    }

    private Duration calculateWorkingHours(ZonedDateTime inTime, ZonedDateTime outTime) {
        if (inTime == null || outTime == null)
            return null;
        else {
            Duration totalWorkingHours = Duration.between(inTime, outTime);
            return  totalWorkingHours.toHours() < 1 ? Duration.ZERO : totalWorkingHours.minusHours(1);
        }
    }

    private Duration calculateOverTime(Employee employee, ZonedDateTime inTime, ZonedDateTime outTime) {
        if (employee.getCategory() != null && employee.getCategory().equals(EmployeeCategory.WORKER)) {
            if (inTime == null || outTime == null)
                return null;
            else {
                ZonedDateTime threshold = ZonedDateTime.of(inTime.getYear(), inTime.getMonthValue(), inTime.getDayOfMonth(), 17, 45, 0, 0, ZoneId.of("Asia/Dhaka"));
                if (outTime.isAfter(threshold)) {
                    if (Duration.between(inTime, outTime).toHours() >= 9) {
                        return Duration.between(inTime, outTime).minusHours(9);
                    }
                }
            }
        }
        return Duration.ZERO;
    }

    private AttendanceType determineAttendanceType(boolean isWeekend, boolean isHoliday, boolean isLeaveTaken) {
        return isWeekend ? AttendanceType.WEEKEND : isHoliday ? AttendanceType.HOLIDAY : isLeaveTaken ? AttendanceType.LEAVE : AttendanceType.WEEKDAY;
    }

    private AttendanceType determineAttendanceType(Employee employee, LocalDate localDate) {
        boolean isWeekend = weekendDateMapService.isWeekend(localDate);
        boolean isHoliday = holidayDateMapService.isHoliday(localDate);
        boolean isLeaveTaken = leaveApplicationDateMapService.isLeaveTaken(employee, localDate);
        return determineAttendanceType(isWeekend, isHoliday, isLeaveTaken);
    }

    public AttendanceStatus determineAttendanceStatus(ZonedDateTime inTime, ZonedDateTime outTime, AttendanceType attendanceType) {
        return attendanceType.equals(AttendanceType.HOLIDAY) || attendanceType.equals(AttendanceType.WEEKEND) || attendanceType.equals(AttendanceType.LEAVE)
            ? (inTime == null && outTime == null) ? AttendanceStatus.NOT_APPLICABLE : AttendanceStatus.PRESENT : (inTime == null && outTime == null) ? AttendanceStatus.ABSENT : AttendanceStatus.PRESENT;
    }

    private AttendanceSummary build(Long id, LocalDate attendanceDate, ZonedDateTime inTime, ZonedDateTime outTime, Duration totalHours, Duration workingHours, Duration overTime, AttendanceType attendanceType,
                                    AttendanceStatus attendanceStatus, Employee employee, EmployeeSalary employeeSalary, String remarks) {
        AttendanceSummary attendanceSummary = new AttendanceSummary();
        attendanceSummary.setId(id);
        attendanceSummary.setAttendanceDate(attendanceDate);
        attendanceSummary.setInTime(inTime);
        attendanceSummary.setOutTime(outTime);
        attendanceSummary.setTotalHours(totalHours);
        attendanceSummary.setWorkingHours(workingHours);
        attendanceSummary.setOvertime(overTime);
        attendanceSummary.setAttendanceType(attendanceType);
        attendanceSummary.setAttendanceStatus(attendanceStatus);
        attendanceSummary.setEmployee(employee);
        attendanceSummary.setDepartment(employee.getDepartment());
        attendanceSummary.setDesignation(employee.getDesignation());
        attendanceSummary.setGrade(employee.getGrade());
        attendanceSummary.setLine(employee.getLine());
        attendanceSummary.setEmployeeSalary(employeeSalary);
        attendanceSummary.setRemarks(remarks);
        return attendanceSummary;
    }

    public AttendanceSummary build(Employee employee, LocalDate attendanceDate, ZonedDateTime inTime, ZonedDateTime outTime) {
        AttendanceSummary attendanceSummary = new AttendanceSummary();
        attendanceSummary.setAttendanceDate(attendanceDate);
        attendanceSummary.setInTime(inTime);
        attendanceSummary.setOutTime(outTime);
        attendanceSummary.setTotalHours(calculateTotalHours(inTime, outTime));
        attendanceSummary.setWorkingHours(calculateWorkingHours(inTime, outTime));
        attendanceSummary.setOvertime(calculateOverTime(employee, inTime, outTime));
        attendanceSummary.setAttendanceType(determineAttendanceType(employee, attendanceDate));
        attendanceSummary.setAttendanceStatus(determineAttendanceStatus(inTime, outTime, attendanceSummary.getAttendanceType()));
        attendanceSummary.setEmployee(employee);
        attendanceSummary.setDepartment(employee.getDepartment());
        attendanceSummary.setDesignation(employee.getDesignation());
        attendanceSummary.setGrade(employee.getGrade());
        attendanceSummary.setLine(employee.getLine());
        attendanceSummary.setEmployeeSalary(findEmployeeSalary(employee, attendanceDate));
        attendanceSummary.setRemarks("Manually Generated on: [" + Instant.now().toString() + "]");
        return attendanceSummary;
    }

    public AttendanceSummary build(Long id, Employee employee, LocalDate attendanceDate, ZonedDateTime inTime, ZonedDateTime outTime, String remarks) {
        AttendanceSummary attendanceSummary = new AttendanceSummary();
        attendanceSummary.setAttendanceDate(attendanceDate);
        attendanceSummary.setId(id);
        attendanceSummary.setInTime(inTime);
        attendanceSummary.setOutTime(outTime);
        attendanceSummary.setTotalHours(calculateTotalHours(inTime, outTime));
        attendanceSummary.setWorkingHours(calculateWorkingHours(inTime, outTime));
        attendanceSummary.setOvertime(calculateOverTime(employee, inTime, outTime));
        attendanceSummary.setAttendanceType(determineAttendanceType(employee, attendanceDate));
        attendanceSummary.setAttendanceStatus(determineAttendanceStatus(inTime, outTime, attendanceSummary.getAttendanceType()));
        attendanceSummary.setEmployee(employee);
        attendanceSummary.setDepartment(employee.getDepartment());
        attendanceSummary.setDesignation(employee.getDesignation());
        attendanceSummary.setGrade(employee.getGrade());
        attendanceSummary.setLine(employee.getLine());
        attendanceSummary.setEmployeeSalary(findEmployeeSalary(employee, attendanceDate));
        attendanceSummary.setRemarks(remarks + " Updated on: [" + Instant.now().toString() + "]");
        return attendanceSummary;
    }

    public List<AttendanceSummary> findAll(Employee employee, LocalDate fromDate, LocalDate toDate) {
        return attendanceSummaryRepository.findAllByEmployeeEqualsAndAttendanceDateIsGreaterThanEqualAndAttendanceDateLessThanEqualAndAttendanceStatusEquals(employee, fromDate, toDate, AttendanceStatus.PRESENT);
    }

    public List<AttendanceSummary> findAll(LocalDate fromDate, LocalDate toDate) {
        return attendanceSummaryRepository.findAllByAttendanceDateIsGreaterThanEqualAndAttendanceDateLessThanEqualAndAttendanceStatusEquals(fromDate, toDate, AttendanceStatus.PRESENT);
    }

    public List<AttendanceSummary> findAll(List<AttendanceSummary> attendanceSummaries) {
        return attendanceSummaryRepository.findAllByIdIn(attendanceSummaries
            .stream()
            .map(AttendanceSummary::getId)
            .collect(Collectors.toList()));
    }
}
