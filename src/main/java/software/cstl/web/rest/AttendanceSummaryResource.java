package software.cstl.web.rest;

import software.cstl.domain.AttendanceSummary;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.enumeration.AttendanceType;
import software.cstl.repository.EmployeeSalaryRepository;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.EmployeeSalaryService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.AttendanceSummaryCriteria;
import software.cstl.service.AttendanceSummaryQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing {@link software.cstl.domain.AttendanceSummary}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceSummaryResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryResource.class);

    private static final String ENTITY_NAME = "attendanceSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceSummaryService attendanceSummaryService;

    private final AttendanceSummaryQueryService attendanceSummaryQueryService;

    public AttendanceSummaryResource(AttendanceSummaryService attendanceSummaryService, AttendanceSummaryQueryService attendanceSummaryQueryService) {
        this.attendanceSummaryService = attendanceSummaryService;
        this.attendanceSummaryQueryService = attendanceSummaryQueryService;
    }

    /**
     * {@code POST  /attendance-summaries} : Create a new attendanceSummary.
     *
     * @param attendanceSummary the attendanceSummary to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendanceSummary, or with status {@code 400 (Bad Request)} if the attendanceSummary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendance-summaries")
    public ResponseEntity<AttendanceSummary> createAttendanceSummary(@RequestBody AttendanceSummary attendanceSummary) throws URISyntaxException {
        log.debug("REST request to save AttendanceSummary : {}", attendanceSummary);
        if (attendanceSummary.getId() != null) {
            throw new BadRequestAlertException("A new attendanceSummary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (attendanceSummary.getEmployee() == null || attendanceSummary.getAttendanceDate() == null || attendanceSummary.getInTime() == null || attendanceSummary.getOutTime() == null) {
            throw new BadRequestAlertException("Please fill up the form correctly", ENTITY_NAME, "idexists");
        }
        if (attendanceSummary.getInTime().isAfter(attendanceSummary.getOutTime())) {
            throw new BadRequestAlertException("Please check in and out time", ENTITY_NAME, "idexists");
        }
        if (!attendanceSummary.getAttendanceDate().equals(attendanceSummary.getInTime().toLocalDate())) {
            throw new BadRequestAlertException("Please check in and out time with attendance date", ENTITY_NAME, "idexists");
        }
        if (Duration.between(attendanceSummary.getInTime(), attendanceSummary.getOutTime()).toHours() > 24) {
            throw new BadRequestAlertException("Duration between in and out time is more than 24 hours", ENTITY_NAME, "idexists");
        }
        AttendanceSummary summary = attendanceSummaryService.build(attendanceSummary.getEmployee(), attendanceSummary.getAttendanceDate(), attendanceSummary.getInTime(), attendanceSummary.getOutTime());
        AttendanceSummary result = attendanceSummaryService.save(summary);
        return ResponseEntity.created(new URI("/api/attendance-summaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendance-summaries} : Updates an existing attendanceSummary.
     *
     * @param attendanceSummary the attendanceSummary to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceSummary,
     * or with status {@code 400 (Bad Request)} if the attendanceSummary is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendanceSummary couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendance-summaries")
    public ResponseEntity<AttendanceSummary> updateAttendanceSummary(@RequestBody AttendanceSummary attendanceSummary) throws URISyntaxException {
        log.debug("REST request to update AttendanceSummary : {}", attendanceSummary);
        if (attendanceSummary.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (attendanceSummary.getEmployee() == null || attendanceSummary.getAttendanceDate() == null || attendanceSummary.getInTime() == null || attendanceSummary.getOutTime() == null) {
            throw new BadRequestAlertException("Please fill up the form correctly", ENTITY_NAME, "idexists");
        }
        if (attendanceSummary.getInTime().isAfter(attendanceSummary.getOutTime())) {
            throw new BadRequestAlertException("Please check in and out time", ENTITY_NAME, "idexists");
        }
        if (!attendanceSummary.getAttendanceDate().equals(attendanceSummary.getInTime().toLocalDate())) {
            throw new BadRequestAlertException("Please check in and out time with attendance date", ENTITY_NAME, "idexists");
        }
        if (Duration.between(attendanceSummary.getInTime(), attendanceSummary.getOutTime()).toHours() > 24) {
            throw new BadRequestAlertException("Duration between in and out time is more than 24 hours", ENTITY_NAME, "idexists");
        }
        AttendanceSummary summary = attendanceSummaryService.build(attendanceSummary.getId(), attendanceSummary.getEmployee(), attendanceSummary.getAttendanceDate(), attendanceSummary.getInTime(), attendanceSummary.getOutTime(), attendanceSummary.getRemarks());
        AttendanceSummary result = attendanceSummaryService.save(summary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attendanceSummary.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendance-summaries/batch} : Updates existing attendanceSummaries.
     *
     * @param attendanceSummaries the attendanceSummaries to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceSummaries,
     * or with status {@code 400 (Bad Request)} if the attendanceSummaries are not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendanceSummaries couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendance-summaries/batch")
    public ResponseEntity<List<AttendanceSummary>> batchUpdateAttendanceSummary(@RequestBody List<AttendanceSummary> attendanceSummaries) throws URISyntaxException {
        log.debug("REST request to batch update AttendanceSummaries : {}", attendanceSummaries);
        for (AttendanceSummary attendanceSummary: attendanceSummaries) {
            if (attendanceSummary.getId() == null) {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            }
            attendanceSummary.setAttendanceStatus(attendanceSummaryService.determineAttendanceStatus(attendanceSummary.getInTime(), attendanceSummary.getOutTime(), attendanceSummary.getAttendanceType()));
        }
        List<AttendanceSummary> results = attendanceSummaryService.save(attendanceSummaries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attendanceSummaries
                .stream()
                .map(summary -> String.valueOf(summary.getId()))
                .collect(Collectors.joining(", "))))
            .body(results);
    }

    /**
     * {@code GET  /attendance-summaries} : get all the attendanceSummaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendanceSummaries in body.
     */
    @GetMapping("/attendance-summaries")
    public ResponseEntity<List<AttendanceSummary>> getAllAttendanceSummaries(AttendanceSummaryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AttendanceSummaries by criteria: {}", criteria);
        Page<AttendanceSummary> page = attendanceSummaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendance-summaries/count} : count all the attendanceSummaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/attendance-summaries/count")
    public ResponseEntity<Long> countAttendanceSummaries(AttendanceSummaryCriteria criteria) {
        log.debug("REST request to count AttendanceSummaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(attendanceSummaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /attendance-summaries/:id} : get the "id" attendanceSummary.
     *
     * @param id the id of the attendanceSummary to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendanceSummary, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendance-summaries/{id}")
    public ResponseEntity<AttendanceSummary> getAttendanceSummary(@PathVariable Long id) {
        log.debug("REST request to get AttendanceSummary : {}", id);
        Optional<AttendanceSummary> attendanceSummary = attendanceSummaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceSummary);
    }

    /**
     * {@code DELETE  /attendance-summaries/:id} : delete the "id" attendanceSummary.
     *
     * @param id the id of the attendanceSummary to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendance-summaries/{id}")
    public ResponseEntity<Void> deleteAttendanceSummary(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceSummary : {}", id);
        throw new BadRequestAlertException("NOT ALLOWED", ENTITY_NAME, "accessdenied");
    }
}
