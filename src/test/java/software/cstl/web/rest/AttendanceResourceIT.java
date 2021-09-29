package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.Attendance;
import software.cstl.domain.AttendanceDataUpload;
import software.cstl.repository.AttendanceRepository;
import software.cstl.service.AttendanceService;
import software.cstl.service.dto.AttendanceCriteria;
import software.cstl.service.AttendanceQueryService;

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

/**
 * Integration tests for the {@link AttendanceResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttendanceResourceIT {

    private static final String DEFAULT_MACHINE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_MACHINE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_MACHINE_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ATTENDANCE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ATTENDANCE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ATTENDANCE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceQueryService attendanceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendanceMockMvc;

    private Attendance attendance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendance createEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .machineNo(DEFAULT_MACHINE_NO)
            .employeeMachineId(DEFAULT_EMPLOYEE_MACHINE_ID)
            .attendanceDateTime(DEFAULT_ATTENDANCE_DATE_TIME)
            .remarks(DEFAULT_REMARKS);
        return attendance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendance createUpdatedEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .machineNo(UPDATED_MACHINE_NO)
            .employeeMachineId(UPDATED_EMPLOYEE_MACHINE_ID)
            .attendanceDateTime(UPDATED_ATTENDANCE_DATE_TIME)
            .remarks(UPDATED_REMARKS);
        return attendance;
    }

    @BeforeEach
    public void initTest() {
        attendance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendance() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();
        // Create the Attendance
        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isCreated());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate + 1);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getMachineNo()).isEqualTo(DEFAULT_MACHINE_NO);
        assertThat(testAttendance.getEmployeeMachineId()).isEqualTo(DEFAULT_EMPLOYEE_MACHINE_ID);
        assertThat(testAttendance.getAttendanceDateTime()).isEqualTo(DEFAULT_ATTENDANCE_DATE_TIME);
        assertThat(testAttendance.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createAttendanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();

        // Create the Attendance with an existing ID
        attendance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMachineNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRepository.findAll().size();
        // set the field null
        attendance.setMachineNo(null);

        // Create the Attendance, which fails.


        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isBadRequest());

        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmployeeMachineIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRepository.findAll().size();
        // set the field null
        attendance.setEmployeeMachineId(null);

        // Create the Attendance, which fails.


        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isBadRequest());

        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttendanceDateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceRepository.findAll().size();
        // set the field null
        attendance.setAttendanceDateTime(null);

        // Create the Attendance, which fails.


        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isBadRequest());

        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttendances() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].machineNo").value(hasItem(DEFAULT_MACHINE_NO)))
            .andExpect(jsonPath("$.[*].employeeMachineId").value(hasItem(DEFAULT_EMPLOYEE_MACHINE_ID)))
            .andExpect(jsonPath("$.[*].attendanceDateTime").value(hasItem(sameInstant(DEFAULT_ATTENDANCE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", attendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendance.getId().intValue()))
            .andExpect(jsonPath("$.machineNo").value(DEFAULT_MACHINE_NO))
            .andExpect(jsonPath("$.employeeMachineId").value(DEFAULT_EMPLOYEE_MACHINE_ID))
            .andExpect(jsonPath("$.attendanceDateTime").value(sameInstant(DEFAULT_ATTENDANCE_DATE_TIME)))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }


    @Test
    @Transactional
    public void getAttendancesByIdFiltering() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        Long id = attendance.getId();

        defaultAttendanceShouldBeFound("id.equals=" + id);
        defaultAttendanceShouldNotBeFound("id.notEquals=" + id);

        defaultAttendanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAttendanceShouldNotBeFound("id.greaterThan=" + id);

        defaultAttendanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAttendanceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAttendancesByMachineNoIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo equals to DEFAULT_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.equals=" + DEFAULT_MACHINE_NO);

        // Get all the attendanceList where machineNo equals to UPDATED_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.equals=" + UPDATED_MACHINE_NO);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMachineNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo not equals to DEFAULT_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.notEquals=" + DEFAULT_MACHINE_NO);

        // Get all the attendanceList where machineNo not equals to UPDATED_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.notEquals=" + UPDATED_MACHINE_NO);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMachineNoIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo in DEFAULT_MACHINE_NO or UPDATED_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.in=" + DEFAULT_MACHINE_NO + "," + UPDATED_MACHINE_NO);

        // Get all the attendanceList where machineNo equals to UPDATED_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.in=" + UPDATED_MACHINE_NO);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMachineNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo is not null
        defaultAttendanceShouldBeFound("machineNo.specified=true");

        // Get all the attendanceList where machineNo is null
        defaultAttendanceShouldNotBeFound("machineNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttendancesByMachineNoContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo contains DEFAULT_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.contains=" + DEFAULT_MACHINE_NO);

        // Get all the attendanceList where machineNo contains UPDATED_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.contains=" + UPDATED_MACHINE_NO);
    }

    @Test
    @Transactional
    public void getAllAttendancesByMachineNoNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where machineNo does not contain DEFAULT_MACHINE_NO
        defaultAttendanceShouldNotBeFound("machineNo.doesNotContain=" + DEFAULT_MACHINE_NO);

        // Get all the attendanceList where machineNo does not contain UPDATED_MACHINE_NO
        defaultAttendanceShouldBeFound("machineNo.doesNotContain=" + UPDATED_MACHINE_NO);
    }


    @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId equals to DEFAULT_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.equals=" + DEFAULT_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId equals to UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.equals=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId not equals to DEFAULT_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.notEquals=" + DEFAULT_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId not equals to UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.notEquals=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId in DEFAULT_EMPLOYEE_MACHINE_ID or UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.in=" + DEFAULT_EMPLOYEE_MACHINE_ID + "," + UPDATED_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId equals to UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.in=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId is not null
        defaultAttendanceShouldBeFound("employeeMachineId.specified=true");

        // Get all the attendanceList where employeeMachineId is null
        defaultAttendanceShouldNotBeFound("employeeMachineId.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId contains DEFAULT_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.contains=" + DEFAULT_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId contains UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.contains=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }

    @Test
    @Transactional
    public void getAllAttendancesByEmployeeMachineIdNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where employeeMachineId does not contain DEFAULT_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldNotBeFound("employeeMachineId.doesNotContain=" + DEFAULT_EMPLOYEE_MACHINE_ID);

        // Get all the attendanceList where employeeMachineId does not contain UPDATED_EMPLOYEE_MACHINE_ID
        defaultAttendanceShouldBeFound("employeeMachineId.doesNotContain=" + UPDATED_EMPLOYEE_MACHINE_ID);
    }


    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDateTime equals to DEFAULT_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldBeFound("attendanceDateTime.equals=" + DEFAULT_ATTENDANCE_DATE_TIME);

        // Get all the attendanceList where attendanceDateTime equals to UPDATED_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldNotBeFound("attendanceDateTime.equals=" + UPDATED_ATTENDANCE_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDateTime not equals to DEFAULT_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldNotBeFound("attendanceDateTime.notEquals=" + DEFAULT_ATTENDANCE_DATE_TIME);

        // Get all the attendanceList where attendanceDateTime not equals to UPDATED_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldBeFound("attendanceDateTime.notEquals=" + UPDATED_ATTENDANCE_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDateTime in DEFAULT_ATTENDANCE_DATE_TIME or UPDATED_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldBeFound("attendanceDateTime.in=" + DEFAULT_ATTENDANCE_DATE_TIME + "," + UPDATED_ATTENDANCE_DATE_TIME);

        // Get all the attendanceList where attendanceDateTime equals to UPDATED_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldNotBeFound("attendanceDateTime.in=" + UPDATED_ATTENDANCE_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDateTime is not null
        defaultAttendanceShouldBeFound("attendanceDateTime.specified=true");

        // Get all the attendanceList where attendanceDateTime is null
        defaultAttendanceShouldNotBeFound("attendanceDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDateTime is greater than or equal to DEFAULT_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldBeFound("attendanceDateTime.greaterThanOrEqual=" + DEFAULT_ATTENDANCE_DATE_TIME);

        // Get all the attendanceList where attendanceDateTime is greater than or equal to UPDATED_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldNotBeFound("attendanceDateTime.greaterThanOrEqual=" + UPDATED_ATTENDANCE_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDateTime is less than or equal to DEFAULT_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldBeFound("attendanceDateTime.lessThanOrEqual=" + DEFAULT_ATTENDANCE_DATE_TIME);

        // Get all the attendanceList where attendanceDateTime is less than or equal to SMALLER_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldNotBeFound("attendanceDateTime.lessThanOrEqual=" + SMALLER_ATTENDANCE_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDateTime is less than DEFAULT_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldNotBeFound("attendanceDateTime.lessThan=" + DEFAULT_ATTENDANCE_DATE_TIME);

        // Get all the attendanceList where attendanceDateTime is less than UPDATED_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldBeFound("attendanceDateTime.lessThan=" + UPDATED_ATTENDANCE_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where attendanceDateTime is greater than DEFAULT_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldNotBeFound("attendanceDateTime.greaterThan=" + DEFAULT_ATTENDANCE_DATE_TIME);

        // Get all the attendanceList where attendanceDateTime is greater than SMALLER_ATTENDANCE_DATE_TIME
        defaultAttendanceShouldBeFound("attendanceDateTime.greaterThan=" + SMALLER_ATTENDANCE_DATE_TIME);
    }


    @Test
    @Transactional
    public void getAllAttendancesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where remarks equals to DEFAULT_REMARKS
        defaultAttendanceShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the attendanceList where remarks equals to UPDATED_REMARKS
        defaultAttendanceShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where remarks not equals to DEFAULT_REMARKS
        defaultAttendanceShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the attendanceList where remarks not equals to UPDATED_REMARKS
        defaultAttendanceShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultAttendanceShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the attendanceList where remarks equals to UPDATED_REMARKS
        defaultAttendanceShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where remarks is not null
        defaultAttendanceShouldBeFound("remarks.specified=true");

        // Get all the attendanceList where remarks is null
        defaultAttendanceShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllAttendancesByRemarksContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where remarks contains DEFAULT_REMARKS
        defaultAttendanceShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the attendanceList where remarks contains UPDATED_REMARKS
        defaultAttendanceShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllAttendancesByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList where remarks does not contain DEFAULT_REMARKS
        defaultAttendanceShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the attendanceList where remarks does not contain UPDATED_REMARKS
        defaultAttendanceShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllAttendancesByAttendanceDataUploadIsEqualToSomething() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        AttendanceDataUpload attendanceDataUpload = AttendanceDataUploadResourceIT.createEntity(em);
        em.persist(attendanceDataUpload);
        em.flush();
        attendance.setAttendanceDataUpload(attendanceDataUpload);
        attendanceRepository.saveAndFlush(attendance);
        Long attendanceDataUploadId = attendanceDataUpload.getId();

        // Get all the attendanceList where attendanceDataUpload equals to attendanceDataUploadId
        defaultAttendanceShouldBeFound("attendanceDataUploadId.equals=" + attendanceDataUploadId);

        // Get all the attendanceList where attendanceDataUpload equals to attendanceDataUploadId + 1
        defaultAttendanceShouldNotBeFound("attendanceDataUploadId.equals=" + (attendanceDataUploadId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAttendanceShouldBeFound(String filter) throws Exception {
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].machineNo").value(hasItem(DEFAULT_MACHINE_NO)))
            .andExpect(jsonPath("$.[*].employeeMachineId").value(hasItem(DEFAULT_EMPLOYEE_MACHINE_ID)))
            .andExpect(jsonPath("$.[*].attendanceDateTime").value(hasItem(sameInstant(DEFAULT_ATTENDANCE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restAttendanceMockMvc.perform(get("/api/attendances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAttendanceShouldNotBeFound(String filter) throws Exception {
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAttendanceMockMvc.perform(get("/api/attendances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAttendance() throws Exception {
        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendance() throws Exception {
        // Initialize the database
        attendanceService.save(attendance);

        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance
        Attendance updatedAttendance = attendanceRepository.findById(attendance.getId()).get();
        // Disconnect from session so that the updates on updatedAttendance are not directly saved in db
        em.detach(updatedAttendance);
        updatedAttendance
            .machineNo(UPDATED_MACHINE_NO)
            .employeeMachineId(UPDATED_EMPLOYEE_MACHINE_ID)
            .attendanceDateTime(UPDATED_ATTENDANCE_DATE_TIME)
            .remarks(UPDATED_REMARKS);

        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttendance)))
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getMachineNo()).isEqualTo(UPDATED_MACHINE_NO);
        assertThat(testAttendance.getEmployeeMachineId()).isEqualTo(UPDATED_EMPLOYEE_MACHINE_ID);
        assertThat(testAttendance.getAttendanceDateTime()).isEqualTo(UPDATED_ATTENDANCE_DATE_TIME);
        assertThat(testAttendance.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendance)))
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendance() throws Exception {
        // Initialize the database
        attendanceService.save(attendance);

        int databaseSizeBeforeDelete = attendanceRepository.findAll().size();

        // Delete the attendance
        restAttendanceMockMvc.perform(delete("/api/attendances/{id}", attendance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
