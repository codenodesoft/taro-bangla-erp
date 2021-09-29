package software.cstl.repository;

import software.cstl.domain.Employee;
import software.cstl.domain.LeaveBalance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.LeaveType;
import software.cstl.domain.enumeration.LeaveBalanceStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the LeaveBalance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long>, JpaSpecificationExecutor<LeaveBalance> {

    List<LeaveBalance> findAllByAssessmentYear(int assessmentYear);

    List<LeaveBalance> findAllByAssessmentYearAndLeaveType(int assessmentYear, LeaveType leaveType);

    List<LeaveBalance> findAllByToLessThanAndStatusEquals(LocalDate localDate, LeaveBalanceStatus leaveBalanceStatus);

    List<LeaveBalance> findByEmployeeEqualsAndLeaveTypeEqualsAndStatusEquals(Employee employee, LeaveType leaveType, LeaveBalanceStatus status);
}
