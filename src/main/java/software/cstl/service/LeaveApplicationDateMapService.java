package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.enumeration.LeaveApplicationStatus;
import software.cstl.service.dto.LeaveApplicationDateMapDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing {@link LeaveApplication}.
 */
@Service
@Transactional
public class LeaveApplicationDateMapService {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationDateMapService.class);

    private final LeaveApplicationService leaveApplicationService;

    public LeaveApplicationDateMapService(LeaveApplicationService leaveApplicationService) {
        this.leaveApplicationService = leaveApplicationService;
    }

    /**
     * Get all the leaveApplicationDetailDateMapDTO by leaveApplications.
     *
     * @param leaveApplications the leaveApplications.
     * @return the entity.
     */
    public List<LeaveApplicationDateMapDTO> findAllLeaveApplicationDateMapDto(List<LeaveApplication> leaveApplications) {
        List<LeaveApplicationDateMapDTO> leaveApplicationDateMapDTOS = new ArrayList<>();
        int counter = 0;
        for(LeaveApplication leaveApplication: leaveApplications) {
            counter = counter + 1;
            LocalDate startDate = leaveApplication.getFrom();
            LocalDate endDate = leaveApplication.getTo().plusDays(1);
            while(startDate.isBefore(endDate)) {
                LeaveApplicationDateMapDTO leaveApplicationDateMapDto = new LeaveApplicationDateMapDTO((long) counter, startDate, leaveApplication.getStatus(), leaveApplication.getEmployee().getId(), leaveApplication.getEmployee().getName());
                startDate = startDate.plusDays(1);
                leaveApplicationDateMapDTOS.add(leaveApplicationDateMapDto);
            }
        }
        return leaveApplicationDateMapDTOS;
    }

    /**
     * Get all the leaveApplicationDetailDateMapDTO by leaveApplications.
     *
     * @param fromDate the startDate.
     * @param toDate the endDate.
     * @param status the status.
     * @return the entity.
     */
    public List<LeaveApplicationDateMapDTO> findAllLeaveApplicationDateMapDto(LocalDate fromDate, LocalDate toDate, LeaveApplicationStatus status) {
        List<LeaveApplication> leaveApplications = leaveApplicationService.findAll(fromDate, toDate, status);
        return findAllLeaveApplicationDateMapDto(leaveApplications);
    }

    /**
     * Get all the leaveApplicationDetailDateMapDTO by leaveApplications.
     *
     * @param employee the employee.
     * @param fromDate the startDate.
     * @param toDate the endDate.
     * @return the entity.
     */
    public List<LeaveApplicationDateMapDTO> findAllLeaveApplicationDateMapDto(Employee employee, LocalDate fromDate, LocalDate toDate) {
        List<LeaveApplication> leaveApplications = leaveApplicationService.findAll(employee, LeaveApplicationStatus.ACCEPTED);
        List<LeaveApplicationDateMapDTO> leaveApplicationDateMapDTOs = findAllLeaveApplicationDateMapDto(leaveApplications);
        List<LeaveApplicationDateMapDTO> list = new ArrayList<>();
        for(LeaveApplicationDateMapDTO leaveApplicationDateMapDTO: leaveApplicationDateMapDTOs) {
            if ((fromDate.isBefore(leaveApplicationDateMapDTO.getLeaveAppliedDate()) && toDate.isAfter(leaveApplicationDateMapDTO.getLeaveAppliedDate())) || fromDate.equals(leaveApplicationDateMapDTO.getLeaveAppliedDate()) || toDate.equals(leaveApplicationDateMapDTO.getLeaveAppliedDate())) {
                list.add(leaveApplicationDateMapDTO);
            }
        }
        return list;
    }

    /**
     * Check if employee has taken leave on this given date.
     *
     * @param employee the employee
     * @param localDate the date.
     * @return the true|false.
     */
    public boolean isLeaveTaken(Employee employee, LocalDate localDate) {
        log.debug("Request to check if employee has taken leave on this given date : {}", localDate);
        return findAllLeaveApplicationDateMapDto(employee, localDate, localDate).size() == 1;
    }
}
