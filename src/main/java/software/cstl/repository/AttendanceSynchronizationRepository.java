package software.cstl.repository;

import software.cstl.domain.AttendanceSynchronization;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttendanceSynchronization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceSynchronizationRepository extends JpaRepository<AttendanceSynchronization, Long> {
}
