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

import software.cstl.domain.AttendanceSummary;
import software.cstl.domain.*; // for static metamodels
import software.cstl.repository.AttendanceSummaryRepository;
import software.cstl.service.dto.AttendanceSummaryCriteria;

/**
 * Service for executing complex queries for {@link AttendanceSummary} entities in the database.
 * The main input is a {@link AttendanceSummaryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttendanceSummary} or a {@link Page} of {@link AttendanceSummary} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttendanceSummaryQueryService extends QueryService<AttendanceSummary> {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryQueryService.class);

    private final AttendanceSummaryRepository attendanceSummaryRepository;

    public AttendanceSummaryQueryService(AttendanceSummaryRepository attendanceSummaryRepository) {
        this.attendanceSummaryRepository = attendanceSummaryRepository;
    }

    /**
     * Return a {@link List} of {@link AttendanceSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttendanceSummary> findByCriteria(AttendanceSummaryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AttendanceSummary> specification = createSpecification(criteria);
        return attendanceSummaryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link AttendanceSummary} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttendanceSummary> findByCriteria(AttendanceSummaryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AttendanceSummary> specification = createSpecification(criteria);
        return attendanceSummaryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttendanceSummaryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AttendanceSummary> specification = createSpecification(criteria);
        return attendanceSummaryRepository.count(specification);
    }

    /**
     * Function to convert {@link AttendanceSummaryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AttendanceSummary> createSpecification(AttendanceSummaryCriteria criteria) {
        Specification<AttendanceSummary> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AttendanceSummary_.id));
            }
            if (criteria.getInTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInTime(), AttendanceSummary_.inTime));
            }
            if (criteria.getOutTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutTime(), AttendanceSummary_.outTime));
            }
            if (criteria.getTotalHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalHours(), AttendanceSummary_.totalHours));
            }
            if (criteria.getWorkingHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWorkingHours(), AttendanceSummary_.workingHours));
            }
            if (criteria.getOvertime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOvertime(), AttendanceSummary_.overtime));
            }
            if (criteria.getAttendanceType() != null) {
                specification = specification.and(buildSpecification(criteria.getAttendanceType(), AttendanceSummary_.attendanceType));
            }
            if (criteria.getAttendanceStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getAttendanceStatus(), AttendanceSummary_.attendanceStatus));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), AttendanceSummary_.remarks));
            }
            if (criteria.getAttendanceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAttendanceDate(), AttendanceSummary_.attendanceDate));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeId(),
                    root -> root.join(AttendanceSummary_.employee, JoinType.LEFT).get(Employee_.id)));
            }
            if (criteria.getEmpId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmpId(),
                    root -> root.join(AttendanceSummary_.employee, JoinType.LEFT).get(Employee_.empId)));
            }
            if (criteria.getEmployeeSalaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getEmployeeSalaryId(),
                    root -> root.join(AttendanceSummary_.employeeSalary, JoinType.LEFT).get(EmployeeSalary_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(AttendanceSummary_.department, JoinType.LEFT).get(Department_.id)));
            }
            if (criteria.getDesignationId() != null) {
                specification = specification.and(buildSpecification(criteria.getDesignationId(),
                    root -> root.join(AttendanceSummary_.designation, JoinType.LEFT).get(Designation_.id)));
            }
            if (criteria.getLineId() != null) {
                specification = specification.and(buildSpecification(criteria.getLineId(),
                    root -> root.join(AttendanceSummary_.line, JoinType.LEFT).get(Line_.id)));
            }
            if (criteria.getGradeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGradeId(),
                    root -> root.join(AttendanceSummary_.grade, JoinType.LEFT).get(Grade_.id)));
            }
        }
        return specification;
    }
}
