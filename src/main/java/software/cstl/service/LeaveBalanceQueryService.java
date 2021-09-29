package software.cstl.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import software.cstl.domain.LeaveBalance;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.LeaveBalanceRepository;
import software.cstl.service.dto.LeaveBalanceCriteria;

/**
 * Service for executing complex queries for {@link LeaveBalance} entities in the database.
 * The main input is a {@link LeaveBalanceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LeaveBalance} or a {@link Page} of {@link LeaveBalance} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LeaveBalanceQueryService extends QueryService<LeaveBalance> {

    private final Logger log = LoggerFactory.getLogger(LeaveBalanceQueryService.class);

    private final LeaveBalanceRepository leaveBalanceRepository;

    public LeaveBalanceQueryService(LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    /**
     * Return a {@link List} of {@link LeaveBalance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LeaveBalance> findByCriteria(LeaveBalanceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LeaveBalance> specification = createSpecification(criteria);
        return leaveBalanceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link LeaveBalance} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveBalance> findByCriteria(LeaveBalanceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LeaveBalance> specification = createSpecification(criteria);
        return leaveBalanceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LeaveBalanceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LeaveBalance> specification = createSpecification(criteria);
        return leaveBalanceRepository.count(specification);
    }

    /**
     * Function to convert {@link LeaveBalanceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LeaveBalance> createSpecification(LeaveBalanceCriteria criteria) {
        Specification<LeaveBalance> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LeaveBalance_.id));
            }
            if (criteria.getTotalDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalDays(), LeaveBalance_.totalDays));
            }
            if (criteria.getRemainingDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRemainingDays(), LeaveBalance_.remainingDays));
            }
            if (criteria.getFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFrom(), LeaveBalance_.from));
            }
            if (criteria.getTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTo(), LeaveBalance_.to));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), LeaveBalance_.remarks));
            }
            if (criteria.getLastSynchronizedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastSynchronizedOn(), LeaveBalance_.lastSynchronizedOn));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LeaveBalance_.status));
            }
            if (criteria.getAssessmentYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssessmentYear(), LeaveBalance_.assessmentYear));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), LeaveBalance_.amount));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(LeaveBalance_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getEmpId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpId(),
                    root -> root.join(LeaveBalance_.employee, JoinType.LEFT).get(Employee_.empId)));
            }
            if (criteria.getLeaveTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getLeaveTypeId(),
                    root -> root.join(LeaveBalance_.leaveType, JoinType.LEFT).get(LeaveType_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(LeaveBalance_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDesignationId(),
                    root -> root.join(LeaveBalance_.designation, JoinType.LEFT).get(Designation_.id)));
            }
        }
        return specification;
    }
}
