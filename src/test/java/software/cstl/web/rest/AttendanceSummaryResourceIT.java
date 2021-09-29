package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.AttendanceSummary;
import software.cstl.domain.Employee;
import software.cstl.domain.EmployeeSalary;
import software.cstl.domain.Department;
import software.cstl.domain.Designation;
import software.cstl.domain.Line;
import software.cstl.domain.Grade;
import software.cstl.repository.AttendanceSummaryRepository;
import software.cstl.service.AttendanceSummaryService;
import software.cstl.service.dto.AttendanceSummaryCriteria;
import software.cstl.service.AttendanceSummaryQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static software.cstl.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.AttendanceType;
import software.cstl.domain.enumeration.AttendanceStatus;
/**
 * Integration tests for the {@link AttendanceSummaryResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttendanceSummaryResourceIT {

    private static final ZonedDateTime DEFAULT_IN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_IN_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_IN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_OUT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OUT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_OUT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Duration DEFAULT_TOTAL_HOURS = Duration.ofHours(6);
    private static final Duration UPDATED_TOTAL_HOURS = Duration.ofHours(12);
    private static final Duration SMALLER_TOTAL_HOURS = Duration.ofHours(5);

    private static final Duration DEFAULT_WORKING_HOURS = Duration.ofHours(6);
    private static final Duration UPDATED_WORKING_HOURS = Duration.ofHours(12);
    private static final Duration SMALLER_WORKING_HOURS = Duration.ofHours(5);

    private static final Duration DEFAULT_OVERTIME = Duration.ofHours(6);
    private static final Duration UPDATED_OVERTIME = Duration.ofHours(12);
    private static final Duration SMALLER_OVERTIME = Duration.ofHours(5);

    private static final AttendanceType DEFAULT_ATTENDANCE_TYPE = AttendanceType.WEEKDAY;
    private static final AttendanceType UPDATED_ATTENDANCE_TYPE = AttendanceType.WEEKEND;

    private static final AttendanceStatus DEFAULT_ATTENDANCE_STATUS = AttendanceStatus.PRESENT;
    private static final AttendanceStatus UPDATED_ATTENDANCE_STATUS = AttendanceStatus.ABSENT;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ATTENDANCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATTENDANCE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ATTENDANCE_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AttendanceSummaryRepository attendanceSummaryRepository;

    @Autowired
    private AttendanceSummaryService attendanceSummaryService;

    @Autowired
    private AttendanceSummaryQueryService attendanceSummaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendanceSummaryMockMvc;

    private AttendanceSummary attendanceSummary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceSummary createEntity(EntityManager em) {
        AttendanceSummary attendanceSummary = new AttendanceSummary()
            .inTime(DEFAULT_IN_TIME)
            .outTime(DEFAULT_OUT_TIME)
            .totalHours(DEFAULT_TOTAL_HOURS)
            .workingHours(DEFAULT_WORKING_HOURS)
            .overtime(DEFAULT_OVERTIME)
            .attendanceType(DEFAULT_ATTENDANCE_TYPE)
            .attendanceStatus(DEFAULT_ATTENDANCE_STATUS)
            .remarks(DEFAULT_REMARKS)
            .attendanceDate(DEFAULT_ATTENDANCE_DATE);
        return attendanceSummary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceSummary createUpdatedEntity(EntityManager em) {
        AttendanceSummary attendanceSummary = new AttendanceSummary()
            .inTime(UPDATED_IN_TIME)
            .outTime(UPDATED_OUT_TIME)
            .totalHours(UPDATED_TOTAL_HOURS)
            .workingHours(UPDATED_WORKING_HOURS)
            .overtime(UPDATED_OVERTIME)
            .attendanceType(UPDATED_ATTENDANCE_TYPE)
            .attendanceStatus(UPDATED_ATTENDANCE_STATUS)
            .remarks(UPDATED_REMARKS)
            .attendanceDate(UPDATED_ATTENDANCE_DATE);
        return attendanceSummary;
    }

    @BeforeEach
    public void initTest() {
        attendanceSummary = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendanceSummary() throws Exception {
        int databaseSizeBeforeCreate = attendanceSummaryRepository.findAll().size();
        // Create the AttendanceSummary
        restAttendanceSummaryMockMvc.perform(post("/api/attendance-summaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceSummary)))
            .andExpect(status().isCreated());

        // Validate the AttendanceSummary in the database
        List<AttendanceSummary> attendanceSummaryList = attendanceSummaryRepository.findAll();
        assertThat(attendanceSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        AttendanceSummary testAttendanceSummary = attendanceSummaryList.get(attendanceSummaryList.size() - 1);
        assertThat(testAttendanceSummary.getInTime()).isEqualTo(DEFAULT_IN_TIME);
        assertThat(testAttendanceSummary.getOutTime()).isEqualTo(DEFAULT_OUT_TIME);
        assertThat(testAttendanceSummary.getTotalHours()).isEqualTo(DEFAULT_TOTAL_HOURS);
        assertThat(testAttendanceSummary.getWorkingHours()).isEqualTo(DEFAULT_WORKING_HOURS);
        assertThat(testAttendanceSummary.getOvertime()).isEqualTo(DEFAULT_OVERTIME);
        assertThat(testAttendanceSummary.getAttendanceType()).isEqualTo(DEFAULT_ATTENDANCE_TYPE);
        assertThat(testAttendanceSummary.getAttendanceStatus()).isEqualTo(DEFAULT_ATTENDANCE_STATUS);
        assertThat(testAttendanceSummary.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAttendanceSummary.getAttendanceDate()).isEqualTo(DEFAULT_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void createAttendanceSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceSummaryRepository.findAll().size();

        // Create the AttendanceSummary with an existing ID
        attendanceSummary.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceSummaryMockMvc.perform(post("/api/attendance-summaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceSummary)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceSummary in the database
        List<AttendanceSummary> attendanceSummaryList = attendanceSummaryRepository.findAll();
        assertThat(attendanceSummaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttendanceSummaries() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList
        restAttendanceSummaryMockMvc.perform(get("/api/attendance-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(sameInstant(DEFAULT_IN_TIME))))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(sameInstant(DEFAULT_OUT_TIME))))
            .andExpect(jsonPath("$.[*].totalHours").value(hasItem(DEFAULT_TOTAL_HOURS.toString())))
            .andExpect(jsonPath("$.[*].workingHours").value(hasItem(DEFAULT_WORKING_HOURS.toString())))
            .andExpect(jsonPath("$.[*].overtime").value(hasItem(DEFAULT_OVERTIME.toString())))
            .andExpect(jsonPath("$.[*].attendanceType").value(hasItem(DEFAULT_ATTENDANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attendanceStatus").value(hasItem(DEFAULT_ATTENDANCE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].attendanceDate").value(hasItem(DEFAULT_ATTENDANCE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAttendanceSummary() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get the attendanceSummary
        restAttendanceSummaryMockMvc.perform(get("/api/attendance-summaries/{id}", attendanceSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendanceSummary.getId().intValue()))
            .andExpect(jsonPath("$.inTime").value(sameInstant(DEFAULT_IN_TIME)))
            .andExpect(jsonPath("$.outTime").value(sameInstant(DEFAULT_OUT_TIME)))
            .andExpect(jsonPath("$.totalHours").value(DEFAULT_TOTAL_HOURS.toString()))
            .andExpect(jsonPath("$.workingHours").value(DEFAULT_WORKING_HOURS.toString()))
            .andExpect(jsonPath("$.overtime").value(DEFAULT_OVERTIME.toString()))
            .andExpect(jsonPath("$.attendanceType").value(DEFAULT_ATTENDANCE_TYPE.toString()))
            .andExpect(jsonPath("$.attendanceStatus").value(DEFAULT_ATTENDANCE_STATUS.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.attendanceDate").value(DEFAULT_ATTENDANCE_DATE.toString()));
    }


    @Test
    @Transactional
    public void getAttendanceSummariesByIdFiltering() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        Long id = attendanceSummary.getId();

        defaultAttendanceSummaryShouldBeFound("id.equals=" + id);
        defaultAttendanceSummaryShouldNotBeFound("id.notEquals=" + id);

        defaultAttendanceSummaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttendanceSummaryShouldNotBeFound("id.greaterThan=" + id);

        defaultAttendanceSummaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttendanceSummaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByInTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where inTime equals to DEFAULT_IN_TIME
        defaultAttendanceSummaryShouldBeFound("inTime.equals=" + DEFAULT_IN_TIME);

        // Get all the attendanceSummaryList where inTime equals to UPDATED_IN_TIME
        defaultAttendanceSummaryShouldNotBeFound("inTime.equals=" + UPDATED_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByInTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where inTime not equals to DEFAULT_IN_TIME
        defaultAttendanceSummaryShouldNotBeFound("inTime.notEquals=" + DEFAULT_IN_TIME);

        // Get all the attendanceSummaryList where inTime not equals to UPDATED_IN_TIME
        defaultAttendanceSummaryShouldBeFound("inTime.notEquals=" + UPDATED_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByInTimeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where inTime in DEFAULT_IN_TIME or UPDATED_IN_TIME
        defaultAttendanceSummaryShouldBeFound("inTime.in=" + DEFAULT_IN_TIME + "," + UPDATED_IN_TIME);

        // Get all the attendanceSummaryList where inTime equals to UPDATED_IN_TIME
        defaultAttendanceSummaryShouldNotBeFound("inTime.in=" + UPDATED_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByInTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where inTime is not null
        defaultAttendanceSummaryShouldBeFound("inTime.specified=true");

        // Get all the attendanceSummaryList where inTime is null
        defaultAttendanceSummaryShouldNotBeFound("inTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByInTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where inTime is greater than or equal to DEFAULT_IN_TIME
        defaultAttendanceSummaryShouldBeFound("inTime.greaterThanOrEqual=" + DEFAULT_IN_TIME);

        // Get all the attendanceSummaryList where inTime is greater than or equal to UPDATED_IN_TIME
        defaultAttendanceSummaryShouldNotBeFound("inTime.greaterThanOrEqual=" + UPDATED_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByInTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where inTime is less than or equal to DEFAULT_IN_TIME
        defaultAttendanceSummaryShouldBeFound("inTime.lessThanOrEqual=" + DEFAULT_IN_TIME);

        // Get all the attendanceSummaryList where inTime is less than or equal to SMALLER_IN_TIME
        defaultAttendanceSummaryShouldNotBeFound("inTime.lessThanOrEqual=" + SMALLER_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByInTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where inTime is less than DEFAULT_IN_TIME
        defaultAttendanceSummaryShouldNotBeFound("inTime.lessThan=" + DEFAULT_IN_TIME);

        // Get all the attendanceSummaryList where inTime is less than UPDATED_IN_TIME
        defaultAttendanceSummaryShouldBeFound("inTime.lessThan=" + UPDATED_IN_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByInTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where inTime is greater than DEFAULT_IN_TIME
        defaultAttendanceSummaryShouldNotBeFound("inTime.greaterThan=" + DEFAULT_IN_TIME);

        // Get all the attendanceSummaryList where inTime is greater than SMALLER_IN_TIME
        defaultAttendanceSummaryShouldBeFound("inTime.greaterThan=" + SMALLER_IN_TIME);
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByOutTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where outTime equals to DEFAULT_OUT_TIME
        defaultAttendanceSummaryShouldBeFound("outTime.equals=" + DEFAULT_OUT_TIME);

        // Get all the attendanceSummaryList where outTime equals to UPDATED_OUT_TIME
        defaultAttendanceSummaryShouldNotBeFound("outTime.equals=" + UPDATED_OUT_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOutTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where outTime not equals to DEFAULT_OUT_TIME
        defaultAttendanceSummaryShouldNotBeFound("outTime.notEquals=" + DEFAULT_OUT_TIME);

        // Get all the attendanceSummaryList where outTime not equals to UPDATED_OUT_TIME
        defaultAttendanceSummaryShouldBeFound("outTime.notEquals=" + UPDATED_OUT_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOutTimeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where outTime in DEFAULT_OUT_TIME or UPDATED_OUT_TIME
        defaultAttendanceSummaryShouldBeFound("outTime.in=" + DEFAULT_OUT_TIME + "," + UPDATED_OUT_TIME);

        // Get all the attendanceSummaryList where outTime equals to UPDATED_OUT_TIME
        defaultAttendanceSummaryShouldNotBeFound("outTime.in=" + UPDATED_OUT_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOutTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where outTime is not null
        defaultAttendanceSummaryShouldBeFound("outTime.specified=true");

        // Get all the attendanceSummaryList where outTime is null
        defaultAttendanceSummaryShouldNotBeFound("outTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOutTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where outTime is greater than or equal to DEFAULT_OUT_TIME
        defaultAttendanceSummaryShouldBeFound("outTime.greaterThanOrEqual=" + DEFAULT_OUT_TIME);

        // Get all the attendanceSummaryList where outTime is greater than or equal to UPDATED_OUT_TIME
        defaultAttendanceSummaryShouldNotBeFound("outTime.greaterThanOrEqual=" + UPDATED_OUT_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOutTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where outTime is less than or equal to DEFAULT_OUT_TIME
        defaultAttendanceSummaryShouldBeFound("outTime.lessThanOrEqual=" + DEFAULT_OUT_TIME);

        // Get all the attendanceSummaryList where outTime is less than or equal to SMALLER_OUT_TIME
        defaultAttendanceSummaryShouldNotBeFound("outTime.lessThanOrEqual=" + SMALLER_OUT_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOutTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where outTime is less than DEFAULT_OUT_TIME
        defaultAttendanceSummaryShouldNotBeFound("outTime.lessThan=" + DEFAULT_OUT_TIME);

        // Get all the attendanceSummaryList where outTime is less than UPDATED_OUT_TIME
        defaultAttendanceSummaryShouldBeFound("outTime.lessThan=" + UPDATED_OUT_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOutTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where outTime is greater than DEFAULT_OUT_TIME
        defaultAttendanceSummaryShouldNotBeFound("outTime.greaterThan=" + DEFAULT_OUT_TIME);

        // Get all the attendanceSummaryList where outTime is greater than SMALLER_OUT_TIME
        defaultAttendanceSummaryShouldBeFound("outTime.greaterThan=" + SMALLER_OUT_TIME);
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByTotalHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where totalHours equals to DEFAULT_TOTAL_HOURS
        defaultAttendanceSummaryShouldBeFound("totalHours.equals=" + DEFAULT_TOTAL_HOURS);

        // Get all the attendanceSummaryList where totalHours equals to UPDATED_TOTAL_HOURS
        defaultAttendanceSummaryShouldNotBeFound("totalHours.equals=" + UPDATED_TOTAL_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByTotalHoursIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where totalHours not equals to DEFAULT_TOTAL_HOURS
        defaultAttendanceSummaryShouldNotBeFound("totalHours.notEquals=" + DEFAULT_TOTAL_HOURS);

        // Get all the attendanceSummaryList where totalHours not equals to UPDATED_TOTAL_HOURS
        defaultAttendanceSummaryShouldBeFound("totalHours.notEquals=" + UPDATED_TOTAL_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByTotalHoursIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where totalHours in DEFAULT_TOTAL_HOURS or UPDATED_TOTAL_HOURS
        defaultAttendanceSummaryShouldBeFound("totalHours.in=" + DEFAULT_TOTAL_HOURS + "," + UPDATED_TOTAL_HOURS);

        // Get all the attendanceSummaryList where totalHours equals to UPDATED_TOTAL_HOURS
        defaultAttendanceSummaryShouldNotBeFound("totalHours.in=" + UPDATED_TOTAL_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByTotalHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where totalHours is not null
        defaultAttendanceSummaryShouldBeFound("totalHours.specified=true");

        // Get all the attendanceSummaryList where totalHours is null
        defaultAttendanceSummaryShouldNotBeFound("totalHours.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByTotalHoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where totalHours is greater than or equal to DEFAULT_TOTAL_HOURS
        defaultAttendanceSummaryShouldBeFound("totalHours.greaterThanOrEqual=" + DEFAULT_TOTAL_HOURS);

        // Get all the attendanceSummaryList where totalHours is greater than or equal to UPDATED_TOTAL_HOURS
        defaultAttendanceSummaryShouldNotBeFound("totalHours.greaterThanOrEqual=" + UPDATED_TOTAL_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByTotalHoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where totalHours is less than or equal to DEFAULT_TOTAL_HOURS
        defaultAttendanceSummaryShouldBeFound("totalHours.lessThanOrEqual=" + DEFAULT_TOTAL_HOURS);

        // Get all the attendanceSummaryList where totalHours is less than or equal to SMALLER_TOTAL_HOURS
        defaultAttendanceSummaryShouldNotBeFound("totalHours.lessThanOrEqual=" + SMALLER_TOTAL_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByTotalHoursIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where totalHours is less than DEFAULT_TOTAL_HOURS
        defaultAttendanceSummaryShouldNotBeFound("totalHours.lessThan=" + DEFAULT_TOTAL_HOURS);

        // Get all the attendanceSummaryList where totalHours is less than UPDATED_TOTAL_HOURS
        defaultAttendanceSummaryShouldBeFound("totalHours.lessThan=" + UPDATED_TOTAL_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByTotalHoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where totalHours is greater than DEFAULT_TOTAL_HOURS
        defaultAttendanceSummaryShouldNotBeFound("totalHours.greaterThan=" + DEFAULT_TOTAL_HOURS);

        // Get all the attendanceSummaryList where totalHours is greater than SMALLER_TOTAL_HOURS
        defaultAttendanceSummaryShouldBeFound("totalHours.greaterThan=" + SMALLER_TOTAL_HOURS);
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByWorkingHoursIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where workingHours equals to DEFAULT_WORKING_HOURS
        defaultAttendanceSummaryShouldBeFound("workingHours.equals=" + DEFAULT_WORKING_HOURS);

        // Get all the attendanceSummaryList where workingHours equals to UPDATED_WORKING_HOURS
        defaultAttendanceSummaryShouldNotBeFound("workingHours.equals=" + UPDATED_WORKING_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByWorkingHoursIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where workingHours not equals to DEFAULT_WORKING_HOURS
        defaultAttendanceSummaryShouldNotBeFound("workingHours.notEquals=" + DEFAULT_WORKING_HOURS);

        // Get all the attendanceSummaryList where workingHours not equals to UPDATED_WORKING_HOURS
        defaultAttendanceSummaryShouldBeFound("workingHours.notEquals=" + UPDATED_WORKING_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByWorkingHoursIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where workingHours in DEFAULT_WORKING_HOURS or UPDATED_WORKING_HOURS
        defaultAttendanceSummaryShouldBeFound("workingHours.in=" + DEFAULT_WORKING_HOURS + "," + UPDATED_WORKING_HOURS);

        // Get all the attendanceSummaryList where workingHours equals to UPDATED_WORKING_HOURS
        defaultAttendanceSummaryShouldNotBeFound("workingHours.in=" + UPDATED_WORKING_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByWorkingHoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where workingHours is not null
        defaultAttendanceSummaryShouldBeFound("workingHours.specified=true");

        // Get all the attendanceSummaryList where workingHours is null
        defaultAttendanceSummaryShouldNotBeFound("workingHours.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByWorkingHoursIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where workingHours is greater than or equal to DEFAULT_WORKING_HOURS
        defaultAttendanceSummaryShouldBeFound("workingHours.greaterThanOrEqual=" + DEFAULT_WORKING_HOURS);

        // Get all the attendanceSummaryList where workingHours is greater than or equal to UPDATED_WORKING_HOURS
        defaultAttendanceSummaryShouldNotBeFound("workingHours.greaterThanOrEqual=" + UPDATED_WORKING_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByWorkingHoursIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where workingHours is less than or equal to DEFAULT_WORKING_HOURS
        defaultAttendanceSummaryShouldBeFound("workingHours.lessThanOrEqual=" + DEFAULT_WORKING_HOURS);

        // Get all the attendanceSummaryList where workingHours is less than or equal to SMALLER_WORKING_HOURS
        defaultAttendanceSummaryShouldNotBeFound("workingHours.lessThanOrEqual=" + SMALLER_WORKING_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByWorkingHoursIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where workingHours is less than DEFAULT_WORKING_HOURS
        defaultAttendanceSummaryShouldNotBeFound("workingHours.lessThan=" + DEFAULT_WORKING_HOURS);

        // Get all the attendanceSummaryList where workingHours is less than UPDATED_WORKING_HOURS
        defaultAttendanceSummaryShouldBeFound("workingHours.lessThan=" + UPDATED_WORKING_HOURS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByWorkingHoursIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where workingHours is greater than DEFAULT_WORKING_HOURS
        defaultAttendanceSummaryShouldNotBeFound("workingHours.greaterThan=" + DEFAULT_WORKING_HOURS);

        // Get all the attendanceSummaryList where workingHours is greater than SMALLER_WORKING_HOURS
        defaultAttendanceSummaryShouldBeFound("workingHours.greaterThan=" + SMALLER_WORKING_HOURS);
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByOvertimeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where overtime equals to DEFAULT_OVERTIME
        defaultAttendanceSummaryShouldBeFound("overtime.equals=" + DEFAULT_OVERTIME);

        // Get all the attendanceSummaryList where overtime equals to UPDATED_OVERTIME
        defaultAttendanceSummaryShouldNotBeFound("overtime.equals=" + UPDATED_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOvertimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where overtime not equals to DEFAULT_OVERTIME
        defaultAttendanceSummaryShouldNotBeFound("overtime.notEquals=" + DEFAULT_OVERTIME);

        // Get all the attendanceSummaryList where overtime not equals to UPDATED_OVERTIME
        defaultAttendanceSummaryShouldBeFound("overtime.notEquals=" + UPDATED_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOvertimeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where overtime in DEFAULT_OVERTIME or UPDATED_OVERTIME
        defaultAttendanceSummaryShouldBeFound("overtime.in=" + DEFAULT_OVERTIME + "," + UPDATED_OVERTIME);

        // Get all the attendanceSummaryList where overtime equals to UPDATED_OVERTIME
        defaultAttendanceSummaryShouldNotBeFound("overtime.in=" + UPDATED_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOvertimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where overtime is not null
        defaultAttendanceSummaryShouldBeFound("overtime.specified=true");

        // Get all the attendanceSummaryList where overtime is null
        defaultAttendanceSummaryShouldNotBeFound("overtime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOvertimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where overtime is greater than or equal to DEFAULT_OVERTIME
        defaultAttendanceSummaryShouldBeFound("overtime.greaterThanOrEqual=" + DEFAULT_OVERTIME);

        // Get all the attendanceSummaryList where overtime is greater than or equal to UPDATED_OVERTIME
        defaultAttendanceSummaryShouldNotBeFound("overtime.greaterThanOrEqual=" + UPDATED_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOvertimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where overtime is less than or equal to DEFAULT_OVERTIME
        defaultAttendanceSummaryShouldBeFound("overtime.lessThanOrEqual=" + DEFAULT_OVERTIME);

        // Get all the attendanceSummaryList where overtime is less than or equal to SMALLER_OVERTIME
        defaultAttendanceSummaryShouldNotBeFound("overtime.lessThanOrEqual=" + SMALLER_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOvertimeIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where overtime is less than DEFAULT_OVERTIME
        defaultAttendanceSummaryShouldNotBeFound("overtime.lessThan=" + DEFAULT_OVERTIME);

        // Get all the attendanceSummaryList where overtime is less than UPDATED_OVERTIME
        defaultAttendanceSummaryShouldBeFound("overtime.lessThan=" + UPDATED_OVERTIME);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByOvertimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where overtime is greater than DEFAULT_OVERTIME
        defaultAttendanceSummaryShouldNotBeFound("overtime.greaterThan=" + DEFAULT_OVERTIME);

        // Get all the attendanceSummaryList where overtime is greater than SMALLER_OVERTIME
        defaultAttendanceSummaryShouldBeFound("overtime.greaterThan=" + SMALLER_OVERTIME);
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceType equals to DEFAULT_ATTENDANCE_TYPE
        defaultAttendanceSummaryShouldBeFound("attendanceType.equals=" + DEFAULT_ATTENDANCE_TYPE);

        // Get all the attendanceSummaryList where attendanceType equals to UPDATED_ATTENDANCE_TYPE
        defaultAttendanceSummaryShouldNotBeFound("attendanceType.equals=" + UPDATED_ATTENDANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceType not equals to DEFAULT_ATTENDANCE_TYPE
        defaultAttendanceSummaryShouldNotBeFound("attendanceType.notEquals=" + DEFAULT_ATTENDANCE_TYPE);

        // Get all the attendanceSummaryList where attendanceType not equals to UPDATED_ATTENDANCE_TYPE
        defaultAttendanceSummaryShouldBeFound("attendanceType.notEquals=" + UPDATED_ATTENDANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceType in DEFAULT_ATTENDANCE_TYPE or UPDATED_ATTENDANCE_TYPE
        defaultAttendanceSummaryShouldBeFound("attendanceType.in=" + DEFAULT_ATTENDANCE_TYPE + "," + UPDATED_ATTENDANCE_TYPE);

        // Get all the attendanceSummaryList where attendanceType equals to UPDATED_ATTENDANCE_TYPE
        defaultAttendanceSummaryShouldNotBeFound("attendanceType.in=" + UPDATED_ATTENDANCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceType is not null
        defaultAttendanceSummaryShouldBeFound("attendanceType.specified=true");

        // Get all the attendanceSummaryList where attendanceType is null
        defaultAttendanceSummaryShouldNotBeFound("attendanceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceStatus equals to DEFAULT_ATTENDANCE_STATUS
        defaultAttendanceSummaryShouldBeFound("attendanceStatus.equals=" + DEFAULT_ATTENDANCE_STATUS);

        // Get all the attendanceSummaryList where attendanceStatus equals to UPDATED_ATTENDANCE_STATUS
        defaultAttendanceSummaryShouldNotBeFound("attendanceStatus.equals=" + UPDATED_ATTENDANCE_STATUS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceStatus not equals to DEFAULT_ATTENDANCE_STATUS
        defaultAttendanceSummaryShouldNotBeFound("attendanceStatus.notEquals=" + DEFAULT_ATTENDANCE_STATUS);

        // Get all the attendanceSummaryList where attendanceStatus not equals to UPDATED_ATTENDANCE_STATUS
        defaultAttendanceSummaryShouldBeFound("attendanceStatus.notEquals=" + UPDATED_ATTENDANCE_STATUS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceStatusIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceStatus in DEFAULT_ATTENDANCE_STATUS or UPDATED_ATTENDANCE_STATUS
        defaultAttendanceSummaryShouldBeFound("attendanceStatus.in=" + DEFAULT_ATTENDANCE_STATUS + "," + UPDATED_ATTENDANCE_STATUS);

        // Get all the attendanceSummaryList where attendanceStatus equals to UPDATED_ATTENDANCE_STATUS
        defaultAttendanceSummaryShouldNotBeFound("attendanceStatus.in=" + UPDATED_ATTENDANCE_STATUS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceStatus is not null
        defaultAttendanceSummaryShouldBeFound("attendanceStatus.specified=true");

        // Get all the attendanceSummaryList where attendanceStatus is null
        defaultAttendanceSummaryShouldNotBeFound("attendanceStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where remarks equals to DEFAULT_REMARKS
        defaultAttendanceSummaryShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the attendanceSummaryList where remarks equals to UPDATED_REMARKS
        defaultAttendanceSummaryShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where remarks not equals to DEFAULT_REMARKS
        defaultAttendanceSummaryShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the attendanceSummaryList where remarks not equals to UPDATED_REMARKS
        defaultAttendanceSummaryShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultAttendanceSummaryShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the attendanceSummaryList where remarks equals to UPDATED_REMARKS
        defaultAttendanceSummaryShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where remarks is not null
        defaultAttendanceSummaryShouldBeFound("remarks.specified=true");

        // Get all the attendanceSummaryList where remarks is null
        defaultAttendanceSummaryShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttendanceSummariesByRemarksContainsSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where remarks contains DEFAULT_REMARKS
        defaultAttendanceSummaryShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the attendanceSummaryList where remarks contains UPDATED_REMARKS
        defaultAttendanceSummaryShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where remarks does not contain DEFAULT_REMARKS
        defaultAttendanceSummaryShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the attendanceSummaryList where remarks does not contain UPDATED_REMARKS
        defaultAttendanceSummaryShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceDate equals to DEFAULT_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldBeFound("attendanceDate.equals=" + DEFAULT_ATTENDANCE_DATE);

        // Get all the attendanceSummaryList where attendanceDate equals to UPDATED_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldNotBeFound("attendanceDate.equals=" + UPDATED_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceDate not equals to DEFAULT_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldNotBeFound("attendanceDate.notEquals=" + DEFAULT_ATTENDANCE_DATE);

        // Get all the attendanceSummaryList where attendanceDate not equals to UPDATED_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldBeFound("attendanceDate.notEquals=" + UPDATED_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceDateIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceDate in DEFAULT_ATTENDANCE_DATE or UPDATED_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldBeFound("attendanceDate.in=" + DEFAULT_ATTENDANCE_DATE + "," + UPDATED_ATTENDANCE_DATE);

        // Get all the attendanceSummaryList where attendanceDate equals to UPDATED_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldNotBeFound("attendanceDate.in=" + UPDATED_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceDate is not null
        defaultAttendanceSummaryShouldBeFound("attendanceDate.specified=true");

        // Get all the attendanceSummaryList where attendanceDate is null
        defaultAttendanceSummaryShouldNotBeFound("attendanceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceDate is greater than or equal to DEFAULT_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldBeFound("attendanceDate.greaterThanOrEqual=" + DEFAULT_ATTENDANCE_DATE);

        // Get all the attendanceSummaryList where attendanceDate is greater than or equal to UPDATED_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldNotBeFound("attendanceDate.greaterThanOrEqual=" + UPDATED_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceDate is less than or equal to DEFAULT_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldBeFound("attendanceDate.lessThanOrEqual=" + DEFAULT_ATTENDANCE_DATE);

        // Get all the attendanceSummaryList where attendanceDate is less than or equal to SMALLER_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldNotBeFound("attendanceDate.lessThanOrEqual=" + SMALLER_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceDate is less than DEFAULT_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldNotBeFound("attendanceDate.lessThan=" + DEFAULT_ATTENDANCE_DATE);

        // Get all the attendanceSummaryList where attendanceDate is less than UPDATED_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldBeFound("attendanceDate.lessThan=" + UPDATED_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void getAllAttendanceSummariesByAttendanceDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);

        // Get all the attendanceSummaryList where attendanceDate is greater than DEFAULT_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldNotBeFound("attendanceDate.greaterThan=" + DEFAULT_ATTENDANCE_DATE);

        // Get all the attendanceSummaryList where attendanceDate is greater than SMALLER_ATTENDANCE_DATE
        defaultAttendanceSummaryShouldBeFound("attendanceDate.greaterThan=" + SMALLER_ATTENDANCE_DATE);
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        attendanceSummary.setEmployee(employee);
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Long employeeId = employee.getId();

        // Get all the attendanceSummaryList where employee equals to employeeId
        defaultAttendanceSummaryShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the attendanceSummaryList where employee equals to employeeId + 1
        defaultAttendanceSummaryShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByEmployeeSalaryIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        EmployeeSalary employeeSalary = EmployeeSalaryResourceIT.createEntity(em);
        em.persist(employeeSalary);
        em.flush();
        attendanceSummary.setEmployeeSalary(employeeSalary);
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Long employeeSalaryId = employeeSalary.getId();

        // Get all the attendanceSummaryList where employeeSalary equals to employeeSalaryId
        defaultAttendanceSummaryShouldBeFound("employeeSalaryId.equals=" + employeeSalaryId);

        // Get all the attendanceSummaryList where employeeSalary equals to employeeSalaryId + 1
        defaultAttendanceSummaryShouldNotBeFound("employeeSalaryId.equals=" + (employeeSalaryId + 1));
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        attendanceSummary.setDepartment(department);
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Long departmentId = department.getId();

        // Get all the attendanceSummaryList where department equals to departmentId
        defaultAttendanceSummaryShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the attendanceSummaryList where department equals to departmentId + 1
        defaultAttendanceSummaryShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Designation designation = DesignationResourceIT.createEntity(em);
        em.persist(designation);
        em.flush();
        attendanceSummary.setDesignation(designation);
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Long designationId = designation.getId();

        // Get all the attendanceSummaryList where designation equals to designationId
        defaultAttendanceSummaryShouldBeFound("designationId.equals=" + designationId);

        // Get all the attendanceSummaryList where designation equals to designationId + 1
        defaultAttendanceSummaryShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByLineIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Line line = LineResourceIT.createEntity(em);
        em.persist(line);
        em.flush();
        attendanceSummary.setLine(line);
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Long lineId = line.getId();

        // Get all the attendanceSummaryList where line equals to lineId
        defaultAttendanceSummaryShouldBeFound("lineId.equals=" + lineId);

        // Get all the attendanceSummaryList where line equals to lineId + 1
        defaultAttendanceSummaryShouldNotBeFound("lineId.equals=" + (lineId + 1));
    }


    @Test
    @Transactional
    public void getAllAttendanceSummariesByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Grade grade = GradeResourceIT.createEntity(em);
        em.persist(grade);
        em.flush();
        attendanceSummary.setGrade(grade);
        attendanceSummaryRepository.saveAndFlush(attendanceSummary);
        Long gradeId = grade.getId();

        // Get all the attendanceSummaryList where grade equals to gradeId
        defaultAttendanceSummaryShouldBeFound("gradeId.equals=" + gradeId);

        // Get all the attendanceSummaryList where grade equals to gradeId + 1
        defaultAttendanceSummaryShouldNotBeFound("gradeId.equals=" + (gradeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttendanceSummaryShouldBeFound(String filter) throws Exception {
        restAttendanceSummaryMockMvc.perform(get("/api/attendance-summaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].inTime").value(hasItem(sameInstant(DEFAULT_IN_TIME))))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(sameInstant(DEFAULT_OUT_TIME))))
            .andExpect(jsonPath("$.[*].totalHours").value(hasItem(DEFAULT_TOTAL_HOURS.toString())))
            .andExpect(jsonPath("$.[*].workingHours").value(hasItem(DEFAULT_WORKING_HOURS.toString())))
            .andExpect(jsonPath("$.[*].overtime").value(hasItem(DEFAULT_OVERTIME.toString())))
            .andExpect(jsonPath("$.[*].attendanceType").value(hasItem(DEFAULT_ATTENDANCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attendanceStatus").value(hasItem(DEFAULT_ATTENDANCE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].attendanceDate").value(hasItem(DEFAULT_ATTENDANCE_DATE.toString())));

        // Check, that the count call also returns 1
        restAttendanceSummaryMockMvc.perform(get("/api/attendance-summaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttendanceSummaryShouldNotBeFound(String filter) throws Exception {
        restAttendanceSummaryMockMvc.perform(get("/api/attendance-summaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttendanceSummaryMockMvc.perform(get("/api/attendance-summaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAttendanceSummary() throws Exception {
        // Get the attendanceSummary
        restAttendanceSummaryMockMvc.perform(get("/api/attendance-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendanceSummary() throws Exception {
        // Initialize the database
        attendanceSummaryService.save(attendanceSummary);

        int databaseSizeBeforeUpdate = attendanceSummaryRepository.findAll().size();

        // Update the attendanceSummary
        AttendanceSummary updatedAttendanceSummary = attendanceSummaryRepository.findById(attendanceSummary.getId()).get();
        // Disconnect from session so that the updates on updatedAttendanceSummary are not directly saved in db
        em.detach(updatedAttendanceSummary);
        updatedAttendanceSummary
            .inTime(UPDATED_IN_TIME)
            .outTime(UPDATED_OUT_TIME)
            .totalHours(UPDATED_TOTAL_HOURS)
            .workingHours(UPDATED_WORKING_HOURS)
            .overtime(UPDATED_OVERTIME)
            .attendanceType(UPDATED_ATTENDANCE_TYPE)
            .attendanceStatus(UPDATED_ATTENDANCE_STATUS)
            .remarks(UPDATED_REMARKS)
            .attendanceDate(UPDATED_ATTENDANCE_DATE);

        restAttendanceSummaryMockMvc.perform(put("/api/attendance-summaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttendanceSummary)))
            .andExpect(status().isOk());

        // Validate the AttendanceSummary in the database
        List<AttendanceSummary> attendanceSummaryList = attendanceSummaryRepository.findAll();
        assertThat(attendanceSummaryList).hasSize(databaseSizeBeforeUpdate);
        AttendanceSummary testAttendanceSummary = attendanceSummaryList.get(attendanceSummaryList.size() - 1);
        assertThat(testAttendanceSummary.getInTime()).isEqualTo(UPDATED_IN_TIME);
        assertThat(testAttendanceSummary.getOutTime()).isEqualTo(UPDATED_OUT_TIME);
        assertThat(testAttendanceSummary.getTotalHours()).isEqualTo(UPDATED_TOTAL_HOURS);
        assertThat(testAttendanceSummary.getWorkingHours()).isEqualTo(UPDATED_WORKING_HOURS);
        assertThat(testAttendanceSummary.getOvertime()).isEqualTo(UPDATED_OVERTIME);
        assertThat(testAttendanceSummary.getAttendanceType()).isEqualTo(UPDATED_ATTENDANCE_TYPE);
        assertThat(testAttendanceSummary.getAttendanceStatus()).isEqualTo(UPDATED_ATTENDANCE_STATUS);
        assertThat(testAttendanceSummary.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAttendanceSummary.getAttendanceDate()).isEqualTo(UPDATED_ATTENDANCE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendanceSummary() throws Exception {
        int databaseSizeBeforeUpdate = attendanceSummaryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceSummaryMockMvc.perform(put("/api/attendance-summaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceSummary)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceSummary in the database
        List<AttendanceSummary> attendanceSummaryList = attendanceSummaryRepository.findAll();
        assertThat(attendanceSummaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendanceSummary() throws Exception {
        // Initialize the database
        attendanceSummaryService.save(attendanceSummary);

        int databaseSizeBeforeDelete = attendanceSummaryRepository.findAll().size();

        // Delete the attendanceSummary
        restAttendanceSummaryMockMvc.perform(delete("/api/attendance-summaries/{id}", attendanceSummary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendanceSummary> attendanceSummaryList = attendanceSummaryRepository.findAll();
        assertThat(attendanceSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
