package software.cstl.scheduled;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.cstl.domain.EmployeeLeaveDate;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.ScheduledEmployeeLeaveApplication;
import software.cstl.repository.EmployeeLeaveDateRepository;
import software.cstl.repository.LeaveApplicationRepository;
import software.cstl.repository.ScheduledEmployeeLeaveApplicationRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class LeaveApplicationScheduler {
    private final LeaveApplicationRepository leaveApplicationRepository;
    private final ScheduledEmployeeLeaveApplicationRepository scheduledEmployeeLeaveApplicationRepository;
    private final EmployeeLeaveDateRepository employeeLeaveDateRepository;

    @Scheduled(fixedDelay = 900000, initialDelay = 0)
    public void updateEmployeeLeaveDate() throws Exception{
        log.info("Running employee leave date (scheduled)");
        List<LeaveApplication> unrecordedLeaveApplications = leaveApplicationRepository.findAllLeaveApplicationsExcludingEmployeeLeaveDays();
        Set<EmployeeLeaveDate> newEmployeeLeaveDates = new HashSet<>();
        for(LeaveApplication leaveApplication: unrecordedLeaveApplications){
            ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication
                = new ScheduledEmployeeLeaveApplication();
            scheduledEmployeeLeaveApplication.leaveApplicationId(leaveApplication.getId());
            scheduledEmployeeLeaveApplication.setExecutedOn(Instant.now());

            LocalDate fromDate = leaveApplication.getFrom();
            while( fromDate.isBefore(leaveApplication.getTo()) || fromDate.isEqual(leaveApplication.getTo())){

                EmployeeLeaveDate employeeLeaveDate = new EmployeeLeaveDate();
                employeeLeaveDate.setEmployee(leaveApplication.getEmployee());
                employeeLeaveDate.setLeaveDate(fromDate);
                newEmployeeLeaveDates.add(employeeLeaveDate);
                //fromDate = fromDate.plusDays(1);
            }

            scheduledEmployeeLeaveApplication.setEmployeeLeaveDates(newEmployeeLeaveDates);
            scheduledEmployeeLeaveApplicationRepository.save(scheduledEmployeeLeaveApplication);
        }
    }
}
