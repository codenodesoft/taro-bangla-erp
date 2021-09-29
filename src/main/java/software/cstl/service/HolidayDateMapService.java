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
public class HolidayDateMapService {

    private final Logger log = LoggerFactory.getLogger(HolidayDateMapService.class);

    private final HolidayService holidayService;

    private final CommonService commonService;

    public HolidayDateMapService(HolidayService holidayService, CommonService commonService) {
        this.holidayService = holidayService;
        this.commonService = commonService;
    }

    /**
     * Get the holidayDateMapDTOs.
     *
     * @param fromDate a start date.
     * @param toDate an end date.
     * @return the holidayDateMapDTO DTO
     */
    public List<HolidayDateMapDTO> findAllHolidayDateMapDTOs(LocalDate fromDate, LocalDate toDate) {
        log.debug("Request to find HolidayDateMapDTO: {} {}", fromDate, toDate);

        List<Holiday> holidays = holidayService.findAll(fromDate, toDate);
        List<HolidayDateMapDTO> holidayDateMapDTOs = new ArrayList<>();
        long serial = 0L;

        for(Holiday holiday: holidays) {
            LocalDate startDate = holiday.getFrom();
            LocalDate endDate = holiday.getTo().plusDays(1);
            while(startDate.isBefore(endDate)) {
                serial = serial + 1;
                HolidayDateMapDTO holidayDateMapDTO = new HolidayDateMapDTO(serial, startDate, holiday.getId());
                holidayDateMapDTOs.add(holidayDateMapDTO);
                startDate = startDate.plusDays(1);
            }
        }
        return holidayDateMapDTOs;
    }

    /**
     * Get list of holidayDateMapDTO.
     *
     * @param year the year.
     * @param month the month.
     * @return the HolidayDateMapDTO DTOs.
     */
    public List<HolidayDateMapDTO> findAllHolidayDateMapDTOs(int year, Month month) {
        log.debug("Request to get HolidayDateMapDTO : {} {}", year, month);
        LocalDate startDateOfTheMonth = commonService.getFirstDayOfTheMonth(year, month);
        LocalDate lastDateOfTheMonth = commonService.getLastDayOfTheMonth(year, month);
        return findAllHolidayDateMapDTOs(startDateOfTheMonth, lastDateOfTheMonth);
    }

    /**
     * Get list of holidayDateMapDTO.
     *
     * @param year the year.
     * @return the HolidayDateMapDTO DTOs.
     */
    public List<HolidayDateMapDTO> findAllHolidayDateMapDTOs(int year) {
        log.debug("Request to get HolidayDateMapDTO : {}", year);
        LocalDate startDateOfTheYear = commonService.getFirstDayOfTheYear(year);
        LocalDate lastDateOfTheYear = commonService.getLastDayOfTheYear(year);
        return findAllHolidayDateMapDTOs(startDateOfTheYear, lastDateOfTheYear);
    }

    /**
     * Get count of holidayDateMapDTO.
     *
     * @param year the year.
     * @param month the month.
     * @return the HolidayDateMapDTO DTOs.
     */
    public int getTotalNumberOfHolidays(int year, Month month) {
        log.debug("Request to get count of total holidays : {} {}", year, month);
        return findAllHolidayDateMapDTOs(year, month).size();
    }

    /**
     * Check if a given date is a holiday.
     *
     * @param localDate the date.
     * @return the true|false.
     */
    public boolean isHoliday(LocalDate localDate) {
        log.debug("Request to check if the given date is a holiday : {}", localDate);
        return findAllHolidayDateMapDTOs(localDate, localDate).size() == 1;
    }
}
