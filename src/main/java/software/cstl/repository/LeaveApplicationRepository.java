package software.cstl.repository;

import software.cstl.domain.Employee;
import software.cstl.domain.LeaveApplication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.LeaveApplicationStatus;

import java.util.List;

/**
 * Spring Data  repository for the LeaveApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long>, JpaSpecificationExecutor<LeaveApplication> {

    @Query(value = "select * from leave_application where status='ACCEPTED' and  id not in (select leave_application_id from sch_emp_leave_app)", nativeQuery = true)
    List<LeaveApplication> findAllLeaveApplicationsExcludingEmployeeLeaveDays();

    List<LeaveApplication> findAllByEmployeeEqualsAndStatusEquals(Employee employee, LeaveApplicationStatus status);

}
