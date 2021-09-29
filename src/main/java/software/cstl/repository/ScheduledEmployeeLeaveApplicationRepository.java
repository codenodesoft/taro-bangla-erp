package software.cstl.repository;

import software.cstl.domain.ScheduledEmployeeLeaveApplication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ScheduledEmployeeLeaveApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScheduledEmployeeLeaveApplicationRepository extends JpaRepository<ScheduledEmployeeLeaveApplication, Long> {
}
