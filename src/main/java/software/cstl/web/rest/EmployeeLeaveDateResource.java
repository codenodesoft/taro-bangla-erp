package software.cstl.web.rest;

import software.cstl.domain.EmployeeLeaveDate;
import software.cstl.repository.EmployeeLeaveDateRepository;
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
 * REST controller for managing {@link software.cstl.domain.EmployeeLeaveDate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmployeeLeaveDateResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLeaveDateResource.class);

    private static final String ENTITY_NAME = "employeeLeaveDate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeLeaveDateRepository employeeLeaveDateRepository;

    public EmployeeLeaveDateResource(EmployeeLeaveDateRepository employeeLeaveDateRepository) {
        this.employeeLeaveDateRepository = employeeLeaveDateRepository;
    }

    /**
     * {@code POST  /employee-leave-dates} : Create a new employeeLeaveDate.
     *
     * @param employeeLeaveDate the employeeLeaveDate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeLeaveDate, or with status {@code 400 (Bad Request)} if the employeeLeaveDate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-leave-dates")
    public ResponseEntity<EmployeeLeaveDate> createEmployeeLeaveDate(@RequestBody EmployeeLeaveDate employeeLeaveDate) throws URISyntaxException {
        log.debug("REST request to save EmployeeLeaveDate : {}", employeeLeaveDate);
        if (employeeLeaveDate.getId() != null) {
            throw new BadRequestAlertException("A new employeeLeaveDate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeLeaveDate result = employeeLeaveDateRepository.save(employeeLeaveDate);
        return ResponseEntity.created(new URI("/api/employee-leave-dates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-leave-dates} : Updates an existing employeeLeaveDate.
     *
     * @param employeeLeaveDate the employeeLeaveDate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeLeaveDate,
     * or with status {@code 400 (Bad Request)} if the employeeLeaveDate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeLeaveDate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-leave-dates")
    public ResponseEntity<EmployeeLeaveDate> updateEmployeeLeaveDate(@RequestBody EmployeeLeaveDate employeeLeaveDate) throws URISyntaxException {
        log.debug("REST request to update EmployeeLeaveDate : {}", employeeLeaveDate);
        if (employeeLeaveDate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeLeaveDate result = employeeLeaveDateRepository.save(employeeLeaveDate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeLeaveDate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /employee-leave-dates} : get all the employeeLeaveDates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeLeaveDates in body.
     */
    @GetMapping("/employee-leave-dates")
    public List<EmployeeLeaveDate> getAllEmployeeLeaveDates() {
        log.debug("REST request to get all EmployeeLeaveDates");
        return employeeLeaveDateRepository.findAll();
    }

    /**
     * {@code GET  /employee-leave-dates/:id} : get the "id" employeeLeaveDate.
     *
     * @param id the id of the employeeLeaveDate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeLeaveDate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-leave-dates/{id}")
    public ResponseEntity<EmployeeLeaveDate> getEmployeeLeaveDate(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLeaveDate : {}", id);
        Optional<EmployeeLeaveDate> employeeLeaveDate = employeeLeaveDateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employeeLeaveDate);
    }

    /**
     * {@code DELETE  /employee-leave-dates/:id} : delete the "id" employeeLeaveDate.
     *
     * @param id the id of the employeeLeaveDate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-leave-dates/{id}")
    public ResponseEntity<Void> deleteEmployeeLeaveDate(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLeaveDate : {}", id);
        employeeLeaveDateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
