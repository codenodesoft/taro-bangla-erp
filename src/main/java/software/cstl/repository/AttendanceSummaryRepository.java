package software.cstl.repository;

import software.cstl.domain.Attendance;
import software.cstl.domain.AttendanceSummary;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.AttendanceStatus;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data  repository for the AttendanceSummary entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceSummaryRepository extends JpaRepository<AttendanceSummary, Long>, JpaSpecificationExecutor<AttendanceSummary> {

    List<AttendanceSummary> findAllByAttendanceDateEquals(LocalDate localDate);

    List<AttendanceSummary> findAllByEmployeeEqualsAndAttendanceDateIsGreaterThanEqualAndAttendanceDateLessThanEqualAndAttendanceStatusEquals(Employee employee, LocalDate fromDate, LocalDate toDate, AttendanceStatus status);

    List<AttendanceSummary> findAllByAttendanceDateIsGreaterThanEqualAndAttendanceDateLessThanEqualAndAttendanceStatusEquals( LocalDate fromDate, LocalDate toDate, AttendanceStatus status);

    List<AttendanceSummary> findAttendanceSummaryByInTimeIsGreaterThanEqualAndOutTimeIsLessThanEqual(ZonedDateTime from, ZonedDateTime to);

    List<AttendanceSummary> findAllByIdIn(List<Long> ids);
}
