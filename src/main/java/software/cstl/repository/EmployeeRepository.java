package software.cstl.repository;

import software.cstl.domain.Employee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.enumeration.EmployeeStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data  repository for the Employee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    public Employee findByEmpId(String empId);

    List<Employee> findAllByStatusEqualsAndJoiningDateLessThanEqual(EmployeeStatus employeeStatus, LocalDate localDate);

    List<Employee> findAllByStatusNot(EmployeeStatus status);
}
