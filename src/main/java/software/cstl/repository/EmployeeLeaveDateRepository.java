package software.cstl.repository;

import software.cstl.domain.EmployeeLeaveDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data  repository for the EmployeeLeaveDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeLeaveDateRepository extends JpaRepository<EmployeeLeaveDate, Long> {
    List<EmployeeLeaveDate> findAllByLeaveDateIsBetween(LocalDate fromDate, LocalDate toDate);
}
