package software.cstl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Holiday;
import software.cstl.repository.HolidayRepository;
import software.cstl.service.dto.HolidayDateMapDTO;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Holiday}.
 */
@Service
@Transactional
public class HolidayService {

    private final Logger log = LoggerFactory.getLogger(HolidayService.class);

    private final HolidayRepository holidayRepository;

    private final CommonService commonService;

    public HolidayService(HolidayRepository holidayRepository, CommonService commonService) {
        this.holidayRepository = holidayRepository;
        this.commonService = commonService;
    }

    /**
     * Save a holiday.
     *
     * @param holiday the entity to save.
     * @return the persisted entity.
     */
    public Holiday save(Holiday holiday) {
        log.debug("Request to save Holiday : {}", holiday);
        return holidayRepository.save(holiday);
    }

    /**
     * Get all the holidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Holiday> findAll(Pageable pageable) {
        log.debug("Request to get all Holidays");
        return holidayRepository.findAll(pageable);
    }


    /**
     * Get one holiday by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Holiday> findOne(Long id) {
        log.debug("Request to get Holiday : {}", id);
        return holidayRepository.findById(id);
    }

    /**
     * Delete the holiday by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Holiday : {}", id);
        holidayRepository.deleteById(id);
    }

    /**
     * Get all the holidays
     *
     * @return the entities
     */
    public List<Holiday> findAll() {
        log.debug("Request to find all Holidays");
        return holidayRepository.findAll();
    }

    /**
     * Get all the holidays.
     *
     * @param fromDate a start date.
     * @param toDate an end date.
     * @return the entities
     */
    public List<Holiday> findAll(LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to find Holidays: {} {}", fromDate, toDate);
        return findAll()
            .stream()
            .filter(holiday
                -> (fromDate.isAfter(holiday.getFrom()) || fromDate.isEqual(holiday.getFrom())) && (toDate.isBefore(holiday.getTo()) || toDate.isEqual(holiday.getFrom())))
            .collect(Collectors.toList());
    }
}
