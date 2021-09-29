package software.cstl.repository;

import software.cstl.domain.Attendance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data  repository for the Attendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

    boolean existsByEmployeeMachineIdEqualsAndAttendanceDateTimeEquals(String employeeMachineId, ZonedDateTime attendanceDateTime);

    List<Attendance> findAllByAttendanceDateTimeBetween(ZonedDateTime from, ZonedDateTime to);
}
