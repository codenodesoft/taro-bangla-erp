package software.cstl.service;

import software.cstl.domain.Attendance;
import software.cstl.domain.AttendanceDataUpload;
import software.cstl.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Attendance;
import software.cstl.domain.enumeration.ActiveStatus;
import software.cstl.domain.enumeration.AttendanceMarkedAs;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.domain.enumeration.LeaveAppliedStatus;
import software.cstl.repository.AttendanceRepository;
import software.cstl.service.dto.AttendanceDataUploadDTO;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Attendance}.
 */
@Service
@Transactional
public class AttendanceService {

    private final Logger log = LoggerFactory.getLogger(AttendanceService.class);

    private final AttendanceRepository attendanceRepository;

    private final CommonService commonService;

    public AttendanceService(AttendanceRepository attendanceRepository, CommonService commonService) {
        this.attendanceRepository = attendanceRepository;
        this.commonService = commonService;
    }

    /**
     * Save a attendance.
     *
     * @param attendance the entity to save.
     * @return the persisted entity.
     */
    public Attendance save(Attendance attendance) {
        log.debug("Request to save Attendance : {}", attendance);
        return attendanceRepository.save(attendance);
    }

    /**
     * Save bulk attendances.
     *
     * @param attendances the list of entities to save.
     * @return the list of persisted entities.
     */
    public List<Attendance> save(List<Attendance> attendances) {
        log.debug("Request to save Attendances : {}", attendances);
        return attendanceRepository.saveAll(attendances);
    }

    /**
     * Get all the attendances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Attendance> findAll(Pageable pageable) {
        log.debug("Request to get all Attendances");
        return attendanceRepository.findAll(pageable);
    }


    /**
     * Get one attendance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Attendance> findOne(Long id) {
        log.debug("Request to get Attendance : {}", id);
        return attendanceRepository.findById(id);
    }

    /**
     * Delete the attendance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attendance : {}", id);
        attendanceRepository.deleteById(id);
    }

    /**
     * Get list of attendances.
     *
     * @param from the from time.
     * @param to the to time.
     * @return the list of entities.
     */
    public List<Attendance> findAllByAttendanceDateTimeBetween(ZonedDateTime from, ZonedDateTime to) {
        return attendanceRepository.findAllByAttendanceDateTimeBetween(from, to);
    }

    /**
     * Save bulk attendance from TXT file.
     *
     * @param attendanceDataUpload the entity where attendance data needs to process and then save.
     */
    @Transactional
    public List<Attendance> save(AttendanceDataUpload attendanceDataUpload) {
        String fileContents = commonService.getByteArrayToString(attendanceDataUpload.getFileUpload());
        String[] lines = commonService.getStringArrayBySeparatingStringContentUsingSeparator(fileContents, "\r\n");
        List<Attendance> attendances = new ArrayList<>();

        for (String line : lines) {
            Attendance attendance = splitAndBuildAttendanceData(line, attendanceDataUpload);
            if(!exists(attendance)) {
                attendances.add(attendance);
            }
        }
        return save(attendances);
    }

    private Attendance splitAndBuildAttendanceData(String line, AttendanceDataUpload attendanceDataUpload) {
        String[] data = commonService.getStringArrayBySeparatingStringContentUsingSeparator(line, ",");
        String machineCode = data[0];
        String employeeMachineId = data[1];
        String attendanceDate = data[2];
        String attendanceTime = data[3];

        int year = Integer.parseInt(attendanceDate.substring(0, 4));
        int month = Integer.parseInt(attendanceDate.substring(4, 6));
        int day = Integer.parseInt(attendanceDate.substring(6, 8));

        int hour = Integer.parseInt(attendanceTime.substring(0, 2));
        int minute = Integer.parseInt(attendanceTime.substring(2, 4));
        int second = Integer.parseInt(attendanceTime.substring(4, 6));

        ZonedDateTime attendanceDateTime = ZonedDateTime.of(year, month, day, hour, minute, second, 0, ZoneId.of("Asia/Dhaka"));
        return build(employeeMachineId, machineCode, attendanceDateTime, line, attendanceDataUpload);
    }

    private Attendance build(String employeeMachineId, String machineCode, ZonedDateTime attendanceDateTime, String remarks, AttendanceDataUpload attendanceDataUpload) {
        Attendance attendance = new Attendance();
        attendance.setEmployeeMachineId(employeeMachineId);
        attendance.setMachineNo(machineCode);
        attendance.setAttendanceDateTime(attendanceDateTime);
        attendance.setRemarks(remarks);
        attendance.setAttendanceDataUpload(attendanceDataUpload);
        return attendance;
    }

    private boolean exists(Attendance attendance) {
        return attendanceRepository.existsByEmployeeMachineIdEqualsAndAttendanceDateTimeEquals(attendance.getEmployeeMachineId(), attendance.getAttendanceDateTime());
    }
}
