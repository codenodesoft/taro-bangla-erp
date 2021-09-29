package software.cstl.service;

import software.cstl.domain.AttendanceSummary;
import software.cstl.domain.AttendanceSynchronization;
import software.cstl.domain.enumeration.SynchronizationStatus;
import software.cstl.repository.AttendanceSynchronizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AttendanceSynchronization}.
 */
@Service
@Transactional
public class AttendanceSynchronizationService {

    private final Logger log = LoggerFactory.getLogger(AttendanceSynchronizationService.class);

    private final AttendanceSynchronizationRepository attendanceSynchronizationRepository;

    private final AttendanceSummaryService attendanceSummaryService;

    public AttendanceSynchronizationService(AttendanceSynchronizationRepository attendanceSynchronizationRepository, AttendanceSummaryService attendanceSummaryService) {
        this.attendanceSynchronizationRepository = attendanceSynchronizationRepository;
        this.attendanceSummaryService = attendanceSummaryService;
    }

    /**
     * Save a attendanceSynchronization.
     *
     * @param attendanceSynchronization the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public AttendanceSynchronization save(AttendanceSynchronization attendanceSynchronization) {
        log.debug("Request to save AttendanceSynchronization : {}", attendanceSynchronization);
        LocalDate startDate = attendanceSynchronization.getFromDate();
        LocalDate endDate = attendanceSynchronization.getToDate();
        while(startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            attendanceSynchronization.setStartTime(Instant.now());
            attendanceSummaryService.syncAttendanceSummary(startDate);
            attendanceSynchronization.setEndTime(Instant.now());
            attendanceSynchronization.setStatus(SynchronizationStatus.SUCCESS);
            startDate = startDate.plusDays(1);
        }
        return attendanceSynchronizationRepository.save(attendanceSynchronization);
    }

    /**
     * Get all the attendanceSynchronizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceSynchronization> findAll(Pageable pageable) {
        log.debug("Request to get all AttendanceSynchronizations");
        return attendanceSynchronizationRepository.findAll(pageable);
    }


    /**
     * Get one attendanceSynchronization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendanceSynchronization> findOne(Long id) {
        log.debug("Request to get AttendanceSynchronization : {}", id);
        return attendanceSynchronizationRepository.findById(id);
    }

    /**
     * Delete the attendanceSynchronization by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttendanceSynchronization : {}", id);
        attendanceSynchronizationRepository.deleteById(id);
    }
}
