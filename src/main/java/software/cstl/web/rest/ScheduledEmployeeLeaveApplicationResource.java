package software.cstl.web.rest;

import software.cstl.domain.ScheduledEmployeeLeaveApplication;
import software.cstl.repository.ScheduledEmployeeLeaveApplicationRepository;
import software.cstl.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link software.cstl.domain.ScheduledEmployeeLeaveApplication}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ScheduledEmployeeLeaveApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ScheduledEmployeeLeaveApplicationResource.class);

    private static final String ENTITY_NAME = "scheduledEmployeeLeaveApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduledEmployeeLeaveApplicationRepository scheduledEmployeeLeaveApplicationRepository;

    public ScheduledEmployeeLeaveApplicationResource(ScheduledEmployeeLeaveApplicationRepository scheduledEmployeeLeaveApplicationRepository) {
        this.scheduledEmployeeLeaveApplicationRepository = scheduledEmployeeLeaveApplicationRepository;
    }

    /**
     * {@code POST  /scheduled-employee-leave-applications} : Create a new scheduledEmployeeLeaveApplication.
     *
     * @param scheduledEmployeeLeaveApplication the scheduledEmployeeLeaveApplication to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheduledEmployeeLeaveApplication, or with status {@code 400 (Bad Request)} if the scheduledEmployeeLeaveApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scheduled-employee-leave-applications")
    public ResponseEntity<ScheduledEmployeeLeaveApplication> createScheduledEmployeeLeaveApplication(@RequestBody ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication) throws URISyntaxException {
        log.debug("REST request to save ScheduledEmployeeLeaveApplication : {}", scheduledEmployeeLeaveApplication);
        if (scheduledEmployeeLeaveApplication.getId() != null) {
            throw new BadRequestAlertException("A new scheduledEmployeeLeaveApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduledEmployeeLeaveApplication result = scheduledEmployeeLeaveApplicationRepository.save(scheduledEmployeeLeaveApplication);
        return ResponseEntity.created(new URI("/api/scheduled-employee-leave-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scheduled-employee-leave-applications} : Updates an existing scheduledEmployeeLeaveApplication.
     *
     * @param scheduledEmployeeLeaveApplication the scheduledEmployeeLeaveApplication to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduledEmployeeLeaveApplication,
     * or with status {@code 400 (Bad Request)} if the scheduledEmployeeLeaveApplication is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheduledEmployeeLeaveApplication couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scheduled-employee-leave-applications")
    public ResponseEntity<ScheduledEmployeeLeaveApplication> updateScheduledEmployeeLeaveApplication(@RequestBody ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication) throws URISyntaxException {
        log.debug("REST request to update ScheduledEmployeeLeaveApplication : {}", scheduledEmployeeLeaveApplication);
        if (scheduledEmployeeLeaveApplication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduledEmployeeLeaveApplication result = scheduledEmployeeLeaveApplicationRepository.save(scheduledEmployeeLeaveApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scheduledEmployeeLeaveApplication.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scheduled-employee-leave-applications} : get all the scheduledEmployeeLeaveApplications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scheduledEmployeeLeaveApplications in body.
     */
    @GetMapping("/scheduled-employee-leave-applications")
    public List<ScheduledEmployeeLeaveApplication> getAllScheduledEmployeeLeaveApplications() {
        log.debug("REST request to get all ScheduledEmployeeLeaveApplications");
        return scheduledEmployeeLeaveApplicationRepository.findAll();
    }

    /**
     * {@code GET  /scheduled-employee-leave-applications/:id} : get the "id" scheduledEmployeeLeaveApplication.
     *
     * @param id the id of the scheduledEmployeeLeaveApplication to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheduledEmployeeLeaveApplication, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scheduled-employee-leave-applications/{id}")
    public ResponseEntity<ScheduledEmployeeLeaveApplication> getScheduledEmployeeLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to get ScheduledEmployeeLeaveApplication : {}", id);
        Optional<ScheduledEmployeeLeaveApplication> scheduledEmployeeLeaveApplication = scheduledEmployeeLeaveApplicationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scheduledEmployeeLeaveApplication);
    }

    /**
     * {@code DELETE  /scheduled-employee-leave-applications/:id} : delete the "id" scheduledEmployeeLeaveApplication.
     *
     * @param id the id of the scheduledEmployeeLeaveApplication to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scheduled-employee-leave-applications/{id}")
    public ResponseEntity<Void> deleteScheduledEmployeeLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to delete ScheduledEmployeeLeaveApplication : {}", id);
        scheduledEmployeeLeaveApplicationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
