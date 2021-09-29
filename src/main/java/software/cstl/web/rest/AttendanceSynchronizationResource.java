package software.cstl.web.rest;

import software.cstl.domain.AttendanceSynchronization;
import software.cstl.service.AttendanceSynchronizationService;
import software.cstl.web.rest.errors.BadRequestAlertException;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.AttendanceSynchronization}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceSynchronizationResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceSynchronizationResource.class);

    private static final String ENTITY_NAME = "attendanceSynchronization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceSynchronizationService attendanceSynchronizationService;

    public AttendanceSynchronizationResource(AttendanceSynchronizationService attendanceSynchronizationService) {
        this.attendanceSynchronizationService = attendanceSynchronizationService;
    }

    /**
     * {@code POST  /attendance-synchronizations} : Create a new attendanceSynchronization.
     *
     * @param attendanceSynchronization the attendanceSynchronization to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendanceSynchronization, or with status {@code 400 (Bad Request)} if the attendanceSynchronization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendance-synchronizations")
    public ResponseEntity<AttendanceSynchronization> createAttendanceSynchronization(@Valid @RequestBody AttendanceSynchronization attendanceSynchronization) throws URISyntaxException {
        log.debug("REST request to save AttendanceSynchronization : {}", attendanceSynchronization);
        if (attendanceSynchronization.getId() != null) {
            throw new BadRequestAlertException("A new attendanceSynchronization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (attendanceSynchronization.getFromDate().isAfter(attendanceSynchronization.getToDate())) {
            throw new BadRequestAlertException("Please enter valid dates.", ENTITY_NAME, "idexists");
        }
        AttendanceSynchronization result = attendanceSynchronizationService.save(attendanceSynchronization);
        return ResponseEntity.created(new URI("/api/attendance-synchronizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendance-synchronizations} : Updates an existing attendanceSynchronization.
     *
     * @param attendanceSynchronization the attendanceSynchronization to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendanceSynchronization,
     * or with status {@code 400 (Bad Request)} if the attendanceSynchronization is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendanceSynchronization couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendance-synchronizations")
    public ResponseEntity<AttendanceSynchronization> updateAttendanceSynchronization(@Valid @RequestBody AttendanceSynchronization attendanceSynchronization) throws URISyntaxException {
        log.debug("REST request to update AttendanceSynchronization : {}", attendanceSynchronization);
        throw new BadRequestAlertException("NOT ALLOWED", ENTITY_NAME, "accessdenied");
    }

    /**
     * {@code GET  /attendance-synchronizations} : get all the attendanceSynchronizations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendanceSynchronizations in body.
     */
    @GetMapping("/attendance-synchronizations")
    public ResponseEntity<List<AttendanceSynchronization>> getAllAttendanceSynchronizations(Pageable pageable) {
        log.debug("REST request to get a page of AttendanceSynchronizations");
        Page<AttendanceSynchronization> page = attendanceSynchronizationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendance-synchronizations/:id} : get the "id" attendanceSynchronization.
     *
     * @param id the id of the attendanceSynchronization to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendanceSynchronization, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendance-synchronizations/{id}")
    public ResponseEntity<AttendanceSynchronization> getAttendanceSynchronization(@PathVariable Long id) {
        log.debug("REST request to get AttendanceSynchronization : {}", id);
        Optional<AttendanceSynchronization> attendanceSynchronization = attendanceSynchronizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendanceSynchronization);
    }

    /**
     * {@code DELETE  /attendance-synchronizations/:id} : delete the "id" attendanceSynchronization.
     *
     * @param id the id of the attendanceSynchronization to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendance-synchronizations/{id}")
    public ResponseEntity<Void> deleteAttendanceSynchronization(@PathVariable Long id) {
        log.debug("REST request to delete AttendanceSynchronization : {}", id);
        throw new BadRequestAlertException("NOT ALLOWED", ENTITY_NAME, "accessdenied");
    }
}
