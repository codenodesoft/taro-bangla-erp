package software.cstl.web.rest;

import software.cstl.domain.LeaveBalance;
import software.cstl.service.LeaveBalanceService;
import software.cstl.web.rest.errors.BadRequestAlertException;
import software.cstl.service.dto.LeaveBalanceCriteria;
import software.cstl.service.LeaveBalanceQueryService;

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
 * REST controller for managing {@link software.cstl.domain.LeaveBalance}.
 */
@RestController
@RequestMapping("/api")
public class LeaveBalanceResource {

    private final Logger log = LoggerFactory.getLogger(LeaveBalanceResource.class);

    private static final String ENTITY_NAME = "leaveBalance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveBalanceService leaveBalanceService;

    private final LeaveBalanceQueryService leaveBalanceQueryService;

    public LeaveBalanceResource(LeaveBalanceService leaveBalanceService, LeaveBalanceQueryService leaveBalanceQueryService) {
        this.leaveBalanceService = leaveBalanceService;
        this.leaveBalanceQueryService = leaveBalanceQueryService;
    }

    /**
     * {@code POST  /leave-balances} : Create a new leaveBalance.
     *
     * @param leaveBalance the leaveBalance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveBalance, or with status {@code 400 (Bad Request)} if the leaveBalance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leave-balances")
    public ResponseEntity<LeaveBalance> createLeaveBalance(@Valid @RequestBody LeaveBalance leaveBalance) throws URISyntaxException {
        log.debug("REST request to save LeaveBalance : {}", leaveBalance);
        throw new BadRequestAlertException("NOT ALLOWED", ENTITY_NAME, "accessdenied");
    }

    /**
     * {@code PUT  /leave-balances} : Updates an existing leaveBalance.
     *
     * @param leaveBalance the leaveBalance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveBalance,
     * or with status {@code 400 (Bad Request)} if the leaveBalance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leaveBalance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leave-balances")
    public ResponseEntity<LeaveBalance> updateLeaveBalance(@Valid @RequestBody LeaveBalance leaveBalance) throws URISyntaxException {
        log.debug("REST request to update LeaveBalance : {}", leaveBalance);
        if (leaveBalance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveBalance result = leaveBalanceService.save(leaveBalance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, leaveBalance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /leave-balances} : get all the leaveBalances.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveBalances in body.
     */
    @GetMapping("/leave-balances")
    public ResponseEntity<List<LeaveBalance>> getAllLeaveBalances(LeaveBalanceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get LeaveBalances by criteria: {}", criteria);
        Page<LeaveBalance> page = leaveBalanceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leave-balances/count} : count all the leaveBalances.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leave-balances/count")
    public ResponseEntity<Long> countLeaveBalances(LeaveBalanceCriteria criteria) {
        log.debug("REST request to count LeaveBalances by criteria: {}", criteria);
        return ResponseEntity.ok().body(leaveBalanceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leave-balances/:id} : get the "id" leaveBalance.
     *
     * @param id the id of the leaveBalance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveBalance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leave-balances/{id}")
    public ResponseEntity<LeaveBalance> getLeaveBalance(@PathVariable Long id) {
        log.debug("REST request to get LeaveBalance : {}", id);
        Optional<LeaveBalance> leaveBalance = leaveBalanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveBalance);
    }

    /**
     * {@code DELETE  /leave-balances/:id} : delete the "id" leaveBalance.
     *
     * @param id the id of the leaveBalance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leave-balances/{id}")
    public ResponseEntity<Void> deleteLeaveBalance(@PathVariable Long id) {
        log.debug("REST request to delete LeaveBalance : {}", id);
        throw new BadRequestAlertException("NOT ALLOWED", ENTITY_NAME, "accessdenied");
    }
}
