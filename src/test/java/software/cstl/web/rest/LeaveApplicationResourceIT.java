package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.LeaveApplication;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveType;
import software.cstl.domain.Department;
import software.cstl.domain.Designation;
import software.cstl.repository.LeaveApplicationRepository;
import software.cstl.service.LeaveApplicationService;
import software.cstl.service.dto.LeaveApplicationCriteria;
import software.cstl.service.LeaveApplicationQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.LeaveApplicationStatus;
/**
 * Integration tests for the {@link LeaveApplicationResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LeaveApplicationResourceIT {

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_TOTAL_DAYS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_DAYS = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_DAYS = new BigDecimal(1 - 1);

    private static final String DEFAULT_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REASON = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ATTACHMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENT_CONTENT_TYPE = "image/png";

    private static final LeaveApplicationStatus DEFAULT_STATUS = LeaveApplicationStatus.PENDING;
    private static final LeaveApplicationStatus UPDATED_STATUS = LeaveApplicationStatus.ACCEPTED;

    private static final String DEFAULT_APPLIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_APPLIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_APPLIED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLIED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ACTION_TAKEN_BY = "AAAAAAAAAA";
    private static final String UPDATED_ACTION_TAKEN_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACTION_TAKEN_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACTION_TAKEN_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @Autowired
    private LeaveApplicationQueryService leaveApplicationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveApplicationMockMvc;

    private LeaveApplication leaveApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApplication createEntity(EntityManager em) {
        LeaveApplication leaveApplication = new LeaveApplication()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .totalDays(DEFAULT_TOTAL_DAYS)
            .reason(DEFAULT_REASON)
            .attachment(DEFAULT_ATTACHMENT)
            .attachmentContentType(DEFAULT_ATTACHMENT_CONTENT_TYPE)
            .status(DEFAULT_STATUS)
            .appliedBy(DEFAULT_APPLIED_BY)
            .appliedOn(DEFAULT_APPLIED_ON)
            .actionTakenBy(DEFAULT_ACTION_TAKEN_BY)
            .actionTakenOn(DEFAULT_ACTION_TAKEN_ON);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        leaveApplication.setEmployee(employee);
        // Add required entity
        LeaveType leaveType;
        if (TestUtil.findAll(em, LeaveType.class).isEmpty()) {
            leaveType = LeaveTypeResourceIT.createEntity(em);
            em.persist(leaveType);
            em.flush();
        } else {
            leaveType = TestUtil.findAll(em, LeaveType.class).get(0);
        }
        leaveApplication.setLeaveType(leaveType);
        return leaveApplication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveApplication createUpdatedEntity(EntityManager em) {
        LeaveApplication leaveApplication = new LeaveApplication()
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .totalDays(UPDATED_TOTAL_DAYS)
            .reason(UPDATED_REASON)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .appliedBy(UPDATED_APPLIED_BY)
            .appliedOn(UPDATED_APPLIED_ON)
            .actionTakenBy(UPDATED_ACTION_TAKEN_BY)
            .actionTakenOn(UPDATED_ACTION_TAKEN_ON);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        leaveApplication.setEmployee(employee);
        // Add required entity
        LeaveType leaveType;
        if (TestUtil.findAll(em, LeaveType.class).isEmpty()) {
            leaveType = LeaveTypeResourceIT.createUpdatedEntity(em);
            em.persist(leaveType);
            em.flush();
        } else {
            leaveType = TestUtil.findAll(em, LeaveType.class).get(0);
        }
        leaveApplication.setLeaveType(leaveType);
        return leaveApplication;
    }

    @BeforeEach
    public void initTest() {
        leaveApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveApplication() throws Exception {
        int databaseSizeBeforeCreate = leaveApplicationRepository.findAll().size();
        // Create the LeaveApplication
        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isCreated());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testLeaveApplication.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testLeaveApplication.getTotalDays()).isEqualTo(DEFAULT_TOTAL_DAYS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(DEFAULT_REASON);
        assertThat(testLeaveApplication.getAttachment()).isEqualTo(DEFAULT_ATTACHMENT);
        assertThat(testLeaveApplication.getAttachmentContentType()).isEqualTo(DEFAULT_ATTACHMENT_CONTENT_TYPE);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveApplication.getAppliedBy()).isEqualTo(DEFAULT_APPLIED_BY);
        assertThat(testLeaveApplication.getAppliedOn()).isEqualTo(DEFAULT_APPLIED_ON);
        assertThat(testLeaveApplication.getActionTakenBy()).isEqualTo(DEFAULT_ACTION_TAKEN_BY);
        assertThat(testLeaveApplication.getActionTakenOn()).isEqualTo(DEFAULT_ACTION_TAKEN_ON);
    }

    @Test
    @Transactional
    public void createLeaveApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveApplicationRepository.findAll().size();

        // Create the LeaveApplication with an existing ID
        leaveApplication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setFrom(null);

        // Create the LeaveApplication, which fails.


        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setTo(null);

        // Create the LeaveApplication, which fails.


        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setTotalDays(null);

        // Create the LeaveApplication, which fails.


        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveApplicationRepository.findAll().size();
        // set the field null
        leaveApplication.setStatus(null);

        // Create the LeaveApplication, which fails.


        restLeaveApplicationMockMvc.perform(post("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeaveApplications() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].appliedBy").value(hasItem(DEFAULT_APPLIED_BY)))
            .andExpect(jsonPath("$.[*].appliedOn").value(hasItem(DEFAULT_APPLIED_ON.toString())))
            .andExpect(jsonPath("$.[*].actionTakenBy").value(hasItem(DEFAULT_ACTION_TAKEN_BY)))
            .andExpect(jsonPath("$.[*].actionTakenOn").value(hasItem(DEFAULT_ACTION_TAKEN_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get the leaveApplication
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/{id}", leaveApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveApplication.getId().intValue()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.totalDays").value(DEFAULT_TOTAL_DAYS.intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.attachmentContentType").value(DEFAULT_ATTACHMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachment").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENT)))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.appliedBy").value(DEFAULT_APPLIED_BY))
            .andExpect(jsonPath("$.appliedOn").value(DEFAULT_APPLIED_ON.toString()))
            .andExpect(jsonPath("$.actionTakenBy").value(DEFAULT_ACTION_TAKEN_BY))
            .andExpect(jsonPath("$.actionTakenOn").value(DEFAULT_ACTION_TAKEN_ON.toString()));
    }


    @Test
    @Transactional
    public void getLeaveApplicationsByIdFiltering() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        Long id = leaveApplication.getId();

        defaultLeaveApplicationShouldBeFound("id.equals=" + id);
        defaultLeaveApplicationShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveApplicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveApplicationShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveApplicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveApplicationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from equals to DEFAULT_FROM
        defaultLeaveApplicationShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from equals to UPDATED_FROM
        defaultLeaveApplicationShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from not equals to DEFAULT_FROM
        defaultLeaveApplicationShouldNotBeFound("from.notEquals=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from not equals to UPDATED_FROM
        defaultLeaveApplicationShouldBeFound("from.notEquals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from in DEFAULT_FROM or UPDATED_FROM
        defaultLeaveApplicationShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the leaveApplicationList where from equals to UPDATED_FROM
        defaultLeaveApplicationShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is not null
        defaultLeaveApplicationShouldBeFound("from.specified=true");

        // Get all the leaveApplicationList where from is null
        defaultLeaveApplicationShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is greater than or equal to DEFAULT_FROM
        defaultLeaveApplicationShouldBeFound("from.greaterThanOrEqual=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from is greater than or equal to UPDATED_FROM
        defaultLeaveApplicationShouldNotBeFound("from.greaterThanOrEqual=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is less than or equal to DEFAULT_FROM
        defaultLeaveApplicationShouldBeFound("from.lessThanOrEqual=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from is less than or equal to SMALLER_FROM
        defaultLeaveApplicationShouldNotBeFound("from.lessThanOrEqual=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is less than DEFAULT_FROM
        defaultLeaveApplicationShouldNotBeFound("from.lessThan=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from is less than UPDATED_FROM
        defaultLeaveApplicationShouldBeFound("from.lessThan=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where from is greater than DEFAULT_FROM
        defaultLeaveApplicationShouldNotBeFound("from.greaterThan=" + DEFAULT_FROM);

        // Get all the leaveApplicationList where from is greater than SMALLER_FROM
        defaultLeaveApplicationShouldBeFound("from.greaterThan=" + SMALLER_FROM);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to equals to DEFAULT_TO
        defaultLeaveApplicationShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to equals to UPDATED_TO
        defaultLeaveApplicationShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to not equals to DEFAULT_TO
        defaultLeaveApplicationShouldNotBeFound("to.notEquals=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to not equals to UPDATED_TO
        defaultLeaveApplicationShouldBeFound("to.notEquals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to in DEFAULT_TO or UPDATED_TO
        defaultLeaveApplicationShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the leaveApplicationList where to equals to UPDATED_TO
        defaultLeaveApplicationShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is not null
        defaultLeaveApplicationShouldBeFound("to.specified=true");

        // Get all the leaveApplicationList where to is null
        defaultLeaveApplicationShouldNotBeFound("to.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is greater than or equal to DEFAULT_TO
        defaultLeaveApplicationShouldBeFound("to.greaterThanOrEqual=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to is greater than or equal to UPDATED_TO
        defaultLeaveApplicationShouldNotBeFound("to.greaterThanOrEqual=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is less than or equal to DEFAULT_TO
        defaultLeaveApplicationShouldBeFound("to.lessThanOrEqual=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to is less than or equal to SMALLER_TO
        defaultLeaveApplicationShouldNotBeFound("to.lessThanOrEqual=" + SMALLER_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is less than DEFAULT_TO
        defaultLeaveApplicationShouldNotBeFound("to.lessThan=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to is less than UPDATED_TO
        defaultLeaveApplicationShouldBeFound("to.lessThan=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where to is greater than DEFAULT_TO
        defaultLeaveApplicationShouldNotBeFound("to.greaterThan=" + DEFAULT_TO);

        // Get all the leaveApplicationList where to is greater than SMALLER_TO
        defaultLeaveApplicationShouldBeFound("to.greaterThan=" + SMALLER_TO);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays equals to DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.equals=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.equals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays not equals to DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.notEquals=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays not equals to UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.notEquals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays in DEFAULT_TOTAL_DAYS or UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.in=" + DEFAULT_TOTAL_DAYS + "," + UPDATED_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.in=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is not null
        defaultLeaveApplicationShouldBeFound("totalDays.specified=true");

        // Get all the leaveApplicationList where totalDays is null
        defaultLeaveApplicationShouldNotBeFound("totalDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is greater than or equal to DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.greaterThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays is greater than or equal to UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.greaterThanOrEqual=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is less than or equal to DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.lessThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays is less than or equal to SMALLER_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.lessThanOrEqual=" + SMALLER_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is less than DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.lessThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays is less than UPDATED_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.lessThan=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByTotalDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where totalDays is greater than DEFAULT_TOTAL_DAYS
        defaultLeaveApplicationShouldNotBeFound("totalDays.greaterThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveApplicationList where totalDays is greater than SMALLER_TOTAL_DAYS
        defaultLeaveApplicationShouldBeFound("totalDays.greaterThan=" + SMALLER_TOTAL_DAYS);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status equals to DEFAULT_STATUS
        defaultLeaveApplicationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the leaveApplicationList where status equals to UPDATED_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status not equals to DEFAULT_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the leaveApplicationList where status not equals to UPDATED_STATUS
        defaultLeaveApplicationShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLeaveApplicationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the leaveApplicationList where status equals to UPDATED_STATUS
        defaultLeaveApplicationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where status is not null
        defaultLeaveApplicationShouldBeFound("status.specified=true");

        // Get all the leaveApplicationList where status is null
        defaultLeaveApplicationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedBy equals to DEFAULT_APPLIED_BY
        defaultLeaveApplicationShouldBeFound("appliedBy.equals=" + DEFAULT_APPLIED_BY);

        // Get all the leaveApplicationList where appliedBy equals to UPDATED_APPLIED_BY
        defaultLeaveApplicationShouldNotBeFound("appliedBy.equals=" + UPDATED_APPLIED_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedBy not equals to DEFAULT_APPLIED_BY
        defaultLeaveApplicationShouldNotBeFound("appliedBy.notEquals=" + DEFAULT_APPLIED_BY);

        // Get all the leaveApplicationList where appliedBy not equals to UPDATED_APPLIED_BY
        defaultLeaveApplicationShouldBeFound("appliedBy.notEquals=" + UPDATED_APPLIED_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedBy in DEFAULT_APPLIED_BY or UPDATED_APPLIED_BY
        defaultLeaveApplicationShouldBeFound("appliedBy.in=" + DEFAULT_APPLIED_BY + "," + UPDATED_APPLIED_BY);

        // Get all the leaveApplicationList where appliedBy equals to UPDATED_APPLIED_BY
        defaultLeaveApplicationShouldNotBeFound("appliedBy.in=" + UPDATED_APPLIED_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedBy is not null
        defaultLeaveApplicationShouldBeFound("appliedBy.specified=true");

        // Get all the leaveApplicationList where appliedBy is null
        defaultLeaveApplicationShouldNotBeFound("appliedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedBy contains DEFAULT_APPLIED_BY
        defaultLeaveApplicationShouldBeFound("appliedBy.contains=" + DEFAULT_APPLIED_BY);

        // Get all the leaveApplicationList where appliedBy contains UPDATED_APPLIED_BY
        defaultLeaveApplicationShouldNotBeFound("appliedBy.contains=" + UPDATED_APPLIED_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedByNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedBy does not contain DEFAULT_APPLIED_BY
        defaultLeaveApplicationShouldNotBeFound("appliedBy.doesNotContain=" + DEFAULT_APPLIED_BY);

        // Get all the leaveApplicationList where appliedBy does not contain UPDATED_APPLIED_BY
        defaultLeaveApplicationShouldBeFound("appliedBy.doesNotContain=" + UPDATED_APPLIED_BY);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedOn equals to DEFAULT_APPLIED_ON
        defaultLeaveApplicationShouldBeFound("appliedOn.equals=" + DEFAULT_APPLIED_ON);

        // Get all the leaveApplicationList where appliedOn equals to UPDATED_APPLIED_ON
        defaultLeaveApplicationShouldNotBeFound("appliedOn.equals=" + UPDATED_APPLIED_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedOn not equals to DEFAULT_APPLIED_ON
        defaultLeaveApplicationShouldNotBeFound("appliedOn.notEquals=" + DEFAULT_APPLIED_ON);

        // Get all the leaveApplicationList where appliedOn not equals to UPDATED_APPLIED_ON
        defaultLeaveApplicationShouldBeFound("appliedOn.notEquals=" + UPDATED_APPLIED_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedOnIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedOn in DEFAULT_APPLIED_ON or UPDATED_APPLIED_ON
        defaultLeaveApplicationShouldBeFound("appliedOn.in=" + DEFAULT_APPLIED_ON + "," + UPDATED_APPLIED_ON);

        // Get all the leaveApplicationList where appliedOn equals to UPDATED_APPLIED_ON
        defaultLeaveApplicationShouldNotBeFound("appliedOn.in=" + UPDATED_APPLIED_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByAppliedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where appliedOn is not null
        defaultLeaveApplicationShouldBeFound("appliedOn.specified=true");

        // Get all the leaveApplicationList where appliedOn is null
        defaultLeaveApplicationShouldNotBeFound("appliedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenBy equals to DEFAULT_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldBeFound("actionTakenBy.equals=" + DEFAULT_ACTION_TAKEN_BY);

        // Get all the leaveApplicationList where actionTakenBy equals to UPDATED_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldNotBeFound("actionTakenBy.equals=" + UPDATED_ACTION_TAKEN_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenBy not equals to DEFAULT_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldNotBeFound("actionTakenBy.notEquals=" + DEFAULT_ACTION_TAKEN_BY);

        // Get all the leaveApplicationList where actionTakenBy not equals to UPDATED_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldBeFound("actionTakenBy.notEquals=" + UPDATED_ACTION_TAKEN_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenBy in DEFAULT_ACTION_TAKEN_BY or UPDATED_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldBeFound("actionTakenBy.in=" + DEFAULT_ACTION_TAKEN_BY + "," + UPDATED_ACTION_TAKEN_BY);

        // Get all the leaveApplicationList where actionTakenBy equals to UPDATED_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldNotBeFound("actionTakenBy.in=" + UPDATED_ACTION_TAKEN_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenBy is not null
        defaultLeaveApplicationShouldBeFound("actionTakenBy.specified=true");

        // Get all the leaveApplicationList where actionTakenBy is null
        defaultLeaveApplicationShouldNotBeFound("actionTakenBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenBy contains DEFAULT_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldBeFound("actionTakenBy.contains=" + DEFAULT_ACTION_TAKEN_BY);

        // Get all the leaveApplicationList where actionTakenBy contains UPDATED_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldNotBeFound("actionTakenBy.contains=" + UPDATED_ACTION_TAKEN_BY);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenByNotContainsSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenBy does not contain DEFAULT_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldNotBeFound("actionTakenBy.doesNotContain=" + DEFAULT_ACTION_TAKEN_BY);

        // Get all the leaveApplicationList where actionTakenBy does not contain UPDATED_ACTION_TAKEN_BY
        defaultLeaveApplicationShouldBeFound("actionTakenBy.doesNotContain=" + UPDATED_ACTION_TAKEN_BY);
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenOnIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenOn equals to DEFAULT_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldBeFound("actionTakenOn.equals=" + DEFAULT_ACTION_TAKEN_ON);

        // Get all the leaveApplicationList where actionTakenOn equals to UPDATED_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldNotBeFound("actionTakenOn.equals=" + UPDATED_ACTION_TAKEN_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenOn not equals to DEFAULT_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldNotBeFound("actionTakenOn.notEquals=" + DEFAULT_ACTION_TAKEN_ON);

        // Get all the leaveApplicationList where actionTakenOn not equals to UPDATED_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldBeFound("actionTakenOn.notEquals=" + UPDATED_ACTION_TAKEN_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenOnIsInShouldWork() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenOn in DEFAULT_ACTION_TAKEN_ON or UPDATED_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldBeFound("actionTakenOn.in=" + DEFAULT_ACTION_TAKEN_ON + "," + UPDATED_ACTION_TAKEN_ON);

        // Get all the leaveApplicationList where actionTakenOn equals to UPDATED_ACTION_TAKEN_ON
        defaultLeaveApplicationShouldNotBeFound("actionTakenOn.in=" + UPDATED_ACTION_TAKEN_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByActionTakenOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);

        // Get all the leaveApplicationList where actionTakenOn is not null
        defaultLeaveApplicationShouldBeFound("actionTakenOn.specified=true");

        // Get all the leaveApplicationList where actionTakenOn is null
        defaultLeaveApplicationShouldNotBeFound("actionTakenOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveApplicationsByEmployeeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Employee employee = leaveApplication.getEmployee();
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Long employeeId = employee.getId();

        // Get all the leaveApplicationList where employee equals to employeeId
        defaultLeaveApplicationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the leaveApplicationList where employee equals to employeeId + 1
        defaultLeaveApplicationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByLeaveTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        LeaveType leaveType = leaveApplication.getLeaveType();
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Long leaveTypeId = leaveType.getId();

        // Get all the leaveApplicationList where leaveType equals to leaveTypeId
        defaultLeaveApplicationShouldBeFound("leaveTypeId.equals=" + leaveTypeId);

        // Get all the leaveApplicationList where leaveType equals to leaveTypeId + 1
        defaultLeaveApplicationShouldNotBeFound("leaveTypeId.equals=" + (leaveTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        leaveApplication.setDepartment(department);
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Long departmentId = department.getId();

        // Get all the leaveApplicationList where department equals to departmentId
        defaultLeaveApplicationShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the leaveApplicationList where department equals to departmentId + 1
        defaultLeaveApplicationShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllLeaveApplicationsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Designation designation = DesignationResourceIT.createEntity(em);
        em.persist(designation);
        em.flush();
        leaveApplication.setDesignation(designation);
        leaveApplicationRepository.saveAndFlush(leaveApplication);
        Long designationId = designation.getId();

        // Get all the leaveApplicationList where designation equals to designationId
        defaultLeaveApplicationShouldBeFound("designationId.equals=" + designationId);

        // Get all the leaveApplicationList where designation equals to designationId + 1
        defaultLeaveApplicationShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveApplicationShouldBeFound(String filter) throws Exception {
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].attachmentContentType").value(hasItem(DEFAULT_ATTACHMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachment").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENT))))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].appliedBy").value(hasItem(DEFAULT_APPLIED_BY)))
            .andExpect(jsonPath("$.[*].appliedOn").value(hasItem(DEFAULT_APPLIED_ON.toString())))
            .andExpect(jsonPath("$.[*].actionTakenBy").value(hasItem(DEFAULT_ACTION_TAKEN_BY)))
            .andExpect(jsonPath("$.[*].actionTakenOn").value(hasItem(DEFAULT_ACTION_TAKEN_ON.toString())));

        // Check, that the count call also returns 1
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveApplicationShouldNotBeFound(String filter) throws Exception {
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLeaveApplication() throws Exception {
        // Get the leaveApplication
        restLeaveApplicationMockMvc.perform(get("/api/leave-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationService.save(leaveApplication);

        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();

        // Update the leaveApplication
        LeaveApplication updatedLeaveApplication = leaveApplicationRepository.findById(leaveApplication.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveApplication are not directly saved in db
        em.detach(updatedLeaveApplication);
        updatedLeaveApplication
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .totalDays(UPDATED_TOTAL_DAYS)
            .reason(UPDATED_REASON)
            .attachment(UPDATED_ATTACHMENT)
            .attachmentContentType(UPDATED_ATTACHMENT_CONTENT_TYPE)
            .status(UPDATED_STATUS)
            .appliedBy(UPDATED_APPLIED_BY)
            .appliedOn(UPDATED_APPLIED_ON)
            .actionTakenBy(UPDATED_ACTION_TAKEN_BY)
            .actionTakenOn(UPDATED_ACTION_TAKEN_ON);

        restLeaveApplicationMockMvc.perform(put("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeaveApplication)))
            .andExpect(status().isOk());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
        LeaveApplication testLeaveApplication = leaveApplicationList.get(leaveApplicationList.size() - 1);
        assertThat(testLeaveApplication.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testLeaveApplication.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testLeaveApplication.getTotalDays()).isEqualTo(UPDATED_TOTAL_DAYS);
        assertThat(testLeaveApplication.getReason()).isEqualTo(UPDATED_REASON);
        assertThat(testLeaveApplication.getAttachment()).isEqualTo(UPDATED_ATTACHMENT);
        assertThat(testLeaveApplication.getAttachmentContentType()).isEqualTo(UPDATED_ATTACHMENT_CONTENT_TYPE);
        assertThat(testLeaveApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveApplication.getAppliedBy()).isEqualTo(UPDATED_APPLIED_BY);
        assertThat(testLeaveApplication.getAppliedOn()).isEqualTo(UPDATED_APPLIED_ON);
        assertThat(testLeaveApplication.getActionTakenBy()).isEqualTo(UPDATED_ACTION_TAKEN_BY);
        assertThat(testLeaveApplication.getActionTakenOn()).isEqualTo(UPDATED_ACTION_TAKEN_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = leaveApplicationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveApplicationMockMvc.perform(put("/api/leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveApplication)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveApplication in the database
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeaveApplication() throws Exception {
        // Initialize the database
        leaveApplicationService.save(leaveApplication);

        int databaseSizeBeforeDelete = leaveApplicationRepository.findAll().size();

        // Delete the leaveApplication
        restLeaveApplicationMockMvc.perform(delete("/api/leave-applications/{id}", leaveApplication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveApplication> leaveApplicationList = leaveApplicationRepository.findAll();
        assertThat(leaveApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
