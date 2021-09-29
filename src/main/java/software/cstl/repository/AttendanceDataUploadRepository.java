package software.cstl.repository;

import software.cstl.domain.AttendanceDataUpload;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttendanceDataUpload entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceDataUploadRepository extends JpaRepository<AttendanceDataUpload, Long> {
}
