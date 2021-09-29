package software.cstl.service;

import software.cstl.domain.LeaveType;
import software.cstl.domain.enumeration.LeaveTypeName;
import software.cstl.domain.enumeration.LeaveTypeStatus;
import software.cstl.repository.LeaveTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LeaveType}.
 */
@Service
@Transactional
public class LeaveTypeService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeService.class);

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    /**
     * Save a leaveType.
     *
     * @param leaveType the entity to save.
     * @return the persisted entity.
     */
    public LeaveType save(LeaveType leaveType) {
        log.debug("Request to save LeaveType : {}", leaveType);
        return leaveTypeRepository.save(leaveType);
    }

    /**
     * Get all the leaveTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LeaveType> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveTypes");
        return leaveTypeRepository.findAll(pageable);
    }


    /**
     * Get one leaveType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LeaveType> findOne(Long id) {
        log.debug("Request to get LeaveType : {}", id);
        return leaveTypeRepository.findById(id);
    }

    /**
     * Delete the leaveType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveType : {}", id);
        leaveTypeRepository.deleteById(id);
    }

    /**
     * Get all the leaveTypes.
     *
     * @return the list of entities.
     */
    public List<LeaveType> findAll() {
        return leaveTypeRepository.findAll();
    }

    /**
     * Get all the leaveTypes.
     *
     * @param leaveTypeStatus the status of the leaveType.
     * @return the list of entities.
     */
    public List<LeaveType> findAll(LeaveTypeStatus leaveTypeStatus) {
        return findAll().stream()
                        .filter(leaveType -> leaveType.getStatus().equals(leaveTypeStatus))
                        .collect(Collectors.toList());
    }

    /**
     * Get all the leaveTypes.
     *
     * @param type the type of leave.
     * @return the list of entities.
     */
    public Optional<LeaveType> findAll(LeaveTypeName type) {
        return findAll().stream()
            .filter(leaveType -> leaveType.getName().equals(type)).findFirst();
    }
}
