package software.cstl.service;

import org.springframework.scheduling.annotation.Scheduled;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.LeaveBalance;
import software.cstl.domain.LeaveType;
import software.cstl.domain.enumeration.*;
import software.cstl.repository.EmployeeRepository;
import software.cstl.repository.LeaveBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.service.dto.HolidayDateMapDTO;
import software.cstl.service.dto.LeaveApplicationDateMapDTO;
import software.cstl.service.dto.WeekendDateMapDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Service Implementation for managing {@link LeaveBalance}.
 */
@Service
@Transactional
public class LeaveBalanceService {

    private final Logger log = LoggerFactory.getLogger(LeaveBalanceService.class);

    private final LeaveBalanceRepository leaveBalanceRepository;

    private final EmployeeRepository employeeRepository;

    private final LeaveTypeService leaveTypeService;

    private final WeekendDateMapService weekendDateMapService;

    private final HolidayDateMapService holidayDateMapService;

    private final LeaveApplicationDateMapService leaveApplicationDateMapService;

    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository, EmployeeRepository employeeRepository, LeaveTypeService leaveTypeService, WeekendDateMapService weekendDateMapService, HolidayDateMapService holidayDateMapService, LeaveApplicationDateMapService leaveApplicationDateMapService) {
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.employeeRepository = employeeRepository;
        this.leaveTypeService = leaveTypeService;
        this.weekendDateMapService = weekendDateMapService;
        this.holidayDateMapService = holidayDateMapService;
        this.leaveApplicationDateMapService = leaveApplicationDateMapService;
    }

    /**
     * Save a leaveBalance.
     *
     * @param leaveBalance the entity to save.
     * @return the persisted entity.
     */
    public LeaveBalance save(LeaveBalance leaveBalance) {
        log.debug("Request to save LeaveBalance : {}", leaveBalance);
        return leaveBalanceRepository.save(leaveBalance);
    }

    /**
     * Save bulk leaveBalances.
     *
     * @param leaveBalances the entity to save.
     * @return the persisted entities.
     */
    public List<LeaveBalance> save(List<LeaveBalance> leaveBalances) {
        log.debug("Request to save LeaveBalance : {}", leaveBalances);
        return leaveBalanceRepository.saveAll(leaveBalances);
    }

    /**
     * Get all the leaveBalances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveBalance> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveBalances");
        return leaveBalanceRepository.findAll(pageable);
    }


    /**
     * Get one leaveBalance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveBalance> findOne(Long id) {
        log.debug("Request to get LeaveBalance : {}", id);
        return leaveBalanceRepository.findById(id);
    }

    /**
     * Delete the leaveBalance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveBalance : {}", id);
        leaveBalanceRepository.deleteById(id);
    }

    /**
     * sync leaveBalances.
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void sync() {
        log.debug("Request to sync/create LeaveBalances");
        createLeaveBalances();
        inactiveLeaveBalances();
    }

    private void createLeaveBalances() {
        LocalDate today = LocalDate.now();
        int thisYear = today.getYear();

        List<Employee> employees = employeeRepository.findAll();
        List<LeaveType> leaveTypes = leaveTypeService.findAll(LeaveTypeStatus.ACTIVE);
        Optional<LeaveType> earnedLeaveType = leaveTypeService.findAll(LeaveTypeName.EARNED_LEAVE);
        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findAllByAssessmentYear(thisYear);
        List<LeaveBalance> earnedLeaveBalances = earnedLeaveType.isPresent() ?
            leaveBalanceRepository.findAllByAssessmentYearAndLeaveType(thisYear - 1, earnedLeaveType.get()) : new ArrayList<>();
        List<WeekendDateMapDTO> weekendDateMapDTOs = weekendDateMapService.findAllWeekendDateMapDTOs(LocalDate.of(thisYear - 1, Month.JANUARY, 1), LocalDate.of(thisYear, Month.DECEMBER, 31));
        List<HolidayDateMapDTO> holidayDateMapDTOs = holidayDateMapService.findAllHolidayDateMapDTOs(LocalDate.of(thisYear - 1, Month.JANUARY, 1), LocalDate.of(thisYear, Month.DECEMBER, 31));
        List<LeaveApplicationDateMapDTO> leaveApplicationDateMapDTOs = leaveApplicationDateMapService.findAllLeaveApplicationDateMapDto(LocalDate.of(thisYear - 1, Month.JANUARY, 1), LocalDate.of(thisYear, Month.DECEMBER, 31), LeaveApplicationStatus.ACCEPTED);

        Map<Employee, List<LeaveBalance>> employeeLeaveBalancesMap = createEmployeeLeaveBalanceMap(employees, leaveBalances);

        List<LeaveBalance> list = new ArrayList<>();

        for (Map.Entry<Employee, List<LeaveBalance>> map : employeeLeaveBalancesMap.entrySet()) {
            boolean isActive = map.getKey().getStatus() != null && map.getKey().getStatus().equals(EmployeeStatus.ACTIVE);

            if(isActive) {
                LocalDate joiningDate = map.getKey().getJoiningDate();
                int joiningYear = joiningDate.getYear();
                boolean isThisYearAndJoiningYearEqual = joiningYear == thisYear;
                boolean isFemale = map.getKey().getPersonalInfo() != null && map.getKey().getPersonalInfo().getGender() != null && map.getKey().getPersonalInfo().getGender().equals(GenderType.FEMALE);

                for (LeaveType leaveType : leaveTypes) {
                    boolean isExists = exists(map.getValue(), thisYear, leaveType);
                    if (!isExists) {
                        switch (leaveType.getName()) {
                            case EARNED_LEAVE:
                                if (today.isAfter(joiningDate.plusYears(1).minusDays(1))) {
                                    LocalDate startDate = LocalDate.of(thisYear, joiningDate.getMonth(), joiningDate.getDayOfMonth());
                                    LocalDate endDate = LocalDate.of(thisYear + 1, joiningDate.getMonth(), joiningDate.getDayOfMonth()).minusDays(1);

                                    if (startDate.isEqual(today) || today.isAfter(startDate)) {
                                        BigDecimal numberOfDays = BigDecimal.valueOf(DAYS.between(LocalDate.of(thisYear - 1, joiningDate.getMonth(), joiningDate.getDayOfMonth()),
                                            LocalDate.of(thisYear, joiningDate.getMonth(), joiningDate.getDayOfMonth()).minusDays(1)));

                                        BigDecimal numberOfWeekends = totalNumberOfWeeks(weekendDateMapDTOs,
                                            LocalDate.of(thisYear - 1, joiningDate.getMonth(), joiningDate.getDayOfMonth()),
                                            LocalDate.of(thisYear, joiningDate.getMonth(), joiningDate.getDayOfMonth()).minusDays(1));

                                        BigDecimal numberOfHolidays = totalNumberOfHolidays(holidayDateMapDTOs,
                                            LocalDate.of(thisYear - 1, joiningDate.getMonth(), joiningDate.getDayOfMonth()),
                                            LocalDate.of(thisYear, joiningDate.getMonth(), joiningDate.getDayOfMonth()).minusDays(1));

                                        BigDecimal numberOfLeaves = totalNumberOfLeaves(leaveApplicationDateMapDTOs,
                                            LocalDate.of(thisYear - 1, joiningDate.getMonth(), joiningDate.getDayOfMonth()),
                                            LocalDate.of(thisYear, joiningDate.getMonth(), joiningDate.getDayOfMonth()).minusDays(1),
                                            map.getKey());

                                        BigDecimal remainingEarnedLeave = remainingEarnedLeave(earnedLeaveBalances, map.getKey());

                                        BigDecimal totalDays = remainingEarnedLeave.add((numberOfDays.subtract(numberOfWeekends).subtract(numberOfHolidays).
                                            subtract(numberOfLeaves)).divide(BigDecimal.valueOf(18), 2, RoundingMode.HALF_EVEN));

                                        totalDays = totalDays.compareTo(BigDecimal.valueOf(40)) > 0 ? BigDecimal.valueOf(40) : totalDays;

                                        LeaveBalance balance = build(map.getKey(), leaveType, thisYear, startDate, endDate,
                                            totalDays, totalDays);
                                        list.add(balance);
                                    }
                                }
                                break;
                            case MATERNITY_LEAVE:
                                if (isFemale && today.isAfter(joiningDate.plusMonths(6))) {
                                    LeaveBalance balance = build(map.getKey(), leaveType, thisYear,
                                        isThisYearAndJoiningYearEqual ? joiningDate : LocalDate.of(thisYear, Month.JANUARY, 1),
                                        LocalDate.of(thisYear, Month.DECEMBER, 31),
                                        leaveType.getTotalDays(), leaveType.getTotalDays());
                                    list.add(balance);
                                }
                                break;
                            default:
                                LeaveBalance balance = build(map.getKey(), leaveType, thisYear,
                                    isThisYearAndJoiningYearEqual ? joiningDate : LocalDate.of(thisYear, Month.JANUARY, 1),
                                    LocalDate.of(thisYear, Month.DECEMBER, 31),
                                    leaveType.getTotalDays(), leaveType.getTotalDays());
                                list.add(balance);
                        }
                    }
                }
            }
        }
        save(list);
    }

    private Map<Employee, List<LeaveBalance>> createEmployeeLeaveBalanceMap(List<Employee> employees, List<LeaveBalance> leaveBalances) {
        Map<Employee, List<LeaveBalance>> employeeLeaveBalancesMap = new HashMap<>();
        for (Employee employee : employees) {
            List<LeaveBalance> balances = new ArrayList<>();
            for (LeaveBalance leaveBalance : leaveBalances) {
                if (employee.getId().equals(leaveBalance.getEmployee().getId())) {
                    balances.add(leaveBalance);
                }
            }
            employeeLeaveBalancesMap.put(employee, balances);
        }
        return employeeLeaveBalancesMap;
    }

    private boolean exists(List<LeaveBalance> leaveBalances, int assessingYear, LeaveType leaveType) {
        for (LeaveBalance leaveBalance : leaveBalances) {
            if (leaveBalance.getAssessmentYear().equals(assessingYear) && leaveBalance.getLeaveType().equals(leaveType)) {
                return true;
            }
        }
        return false;
    }

    private BigDecimal totalNumberOfWeeks(List<WeekendDateMapDTO> weekendDateMapDTOs, LocalDate from, LocalDate to) {
        return BigDecimal.valueOf(weekendDateMapDTOs.stream().filter(weekend -> weekend.getWeekendDate().isAfter(from) && weekend.getWeekendDate().isBefore(to)).count());
    }

    private BigDecimal totalNumberOfHolidays(List<HolidayDateMapDTO> holidayDateMapDTOs, LocalDate from, LocalDate to) {
        return BigDecimal.valueOf(holidayDateMapDTOs.stream().filter(holiday -> holiday.getHolidayDate().isAfter(from) && holiday.getHolidayDate().isBefore(to)).count());
    }

    private BigDecimal totalNumberOfLeaves(List<LeaveApplicationDateMapDTO> leaveApplicationDateMapDTOs, LocalDate from, LocalDate to, Employee employee) {
        return BigDecimal.valueOf(leaveApplicationDateMapDTOs.stream().filter(leave -> (leave.getLeaveAppliedDate().isAfter(from) &&
            leave.getLeaveAppliedDate().isBefore(to)) &&
            leave.getEmployeeId().equals(employee.getId())).count());
    }

    private BigDecimal remainingEarnedLeave(List<LeaveBalance> leaveBalances, Employee employee) {
        for(LeaveBalance leaveBalance: leaveBalances) {
            if(leaveBalance.getEmployee().getId().equals(employee.getId())) {
                return leaveBalance.getRemainingDays();
            }
        }
        return BigDecimal.ZERO;
    }

    private LeaveBalance build(Employee employee, LeaveType leaveType, int assessmentYear, LocalDate startDate, LocalDate endDate,
                               BigDecimal totalDays, BigDecimal remainingDays) {
        LeaveBalance leaveBalance = new LeaveBalance();
        leaveBalance.setEmployee(employee);
        leaveBalance.setDepartment(employee.getDepartment());
        leaveBalance.setDesignation(employee.getDesignation());
        leaveBalance.setLeaveType(leaveType);
        leaveBalance.setAssessmentYear(assessmentYear);
        leaveBalance.setFrom(startDate);
        leaveBalance.setTo(endDate);
        leaveBalance.setTotalDays(totalDays);
        leaveBalance.remainingDays(remainingDays);
        leaveBalance.setStatus(LeaveBalanceStatus.ACTIVE);
        leaveBalance.setLastSynchronizedOn(Instant.now());
        return leaveBalance;
    }

    /**
     * inactive leaveBalances.
     */
    public void inactiveLeaveBalances() {
        log.debug("Request to inactive LeaveBalances");

        LocalDate today = LocalDate.now();
        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findAllByToLessThanAndStatusEquals(today, LeaveBalanceStatus.ACTIVE);
        List<LeaveBalance> list = new ArrayList<>();

        for(LeaveBalance leaveBalance: leaveBalances) {
            leaveBalance.setLastSynchronizedOn(Instant.now());
            leaveBalance.setStatus(LeaveBalanceStatus.INACTIVE);
            list.add(leaveBalance);
        }
        save(list);

        List<Employee> inactiveEmployees = employeeRepository.findAllByStatusNot(EmployeeStatus.ACTIVE);
        leaveBalances = leaveBalanceRepository.findAllByToLessThanAndStatusEquals(today, LeaveBalanceStatus.ACTIVE);
        list = new ArrayList<>();

        for (Employee employee: inactiveEmployees) {
            for (LeaveBalance leaveBalance: leaveBalances) {
                if (employee.getId().equals(leaveBalance.getEmployee().getId())) {
                    leaveBalance.setLastSynchronizedOn(Instant.now());
                    leaveBalance.setStatus(LeaveBalanceStatus.INACTIVE);
                    list.add(leaveBalance);
                }
            }
        }
        save(list);
    }

    @Transactional
    public boolean isValid(LeaveApplication leaveApplication) {
        List<LeaveBalance> leaveBalance = leaveBalanceRepository.findByEmployeeEqualsAndLeaveTypeEqualsAndStatusEquals(leaveApplication.getEmployee(), leaveApplication.getLeaveType(), LeaveBalanceStatus.ACTIVE);
        if (leaveBalance.size() > 0) {
            for(LeaveBalance balance: leaveBalance) {
                if ((balance.getFrom().isBefore(leaveApplication.getFrom()) && balance.getTo().isAfter(leaveApplication.getFrom()))
                    && (balance.getFrom().isBefore(leaveApplication.getTo()) && balance.getTo().isAfter(leaveApplication.getTo()))) {
                    BigDecimal remainingDays = balance.getRemainingDays().subtract(leaveApplication.getTotalDays());
                    if (remainingDays.compareTo(BigDecimal.ZERO) >= 0) {
                        if(leaveApplication.getStatus().equals(LeaveApplicationStatus.ACCEPTED)) {
                            balance.setRemainingDays(remainingDays);
                            save(balance);
                        }
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }
}
