package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.LeaveBalance;
import software.cstl.domain.Employee;
import software.cstl.domain.LeaveType;
import software.cstl.domain.Department;
import software.cstl.domain.Designation;
import software.cstl.repository.LeaveBalanceRepository;
import software.cstl.service.LeaveBalanceService;
import software.cstl.service.dto.LeaveBalanceCriteria;
import software.cstl.service.LeaveBalanceQueryService;

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

import software.cstl.domain.enumeration.LeaveBalanceStatus;
/**
 * Integration tests for the {@link LeaveBalanceResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LeaveBalanceResourceIT {

    private static final BigDecimal DEFAULT_TOTAL_DAYS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_DAYS = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_DAYS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_REMAINING_DAYS = new BigDecimal(1);
    private static final BigDecimal UPDATED_REMAINING_DAYS = new BigDecimal(2);
    private static final BigDecimal SMALLER_REMAINING_DAYS = new BigDecimal(1 - 1);

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_SYNCHRONIZED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_SYNCHRONIZED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LeaveBalanceStatus DEFAULT_STATUS = LeaveBalanceStatus.ACTIVE;
    private static final LeaveBalanceStatus UPDATED_STATUS = LeaveBalanceStatus.INACTIVE;

    private static final Integer DEFAULT_ASSESSMENT_YEAR = 1;
    private static final Integer UPDATED_ASSESSMENT_YEAR = 2;
    private static final Integer SMALLER_ASSESSMENT_YEAR = 1 - 1;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @Autowired
    private LeaveBalanceQueryService leaveBalanceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeaveBalanceMockMvc;

    private LeaveBalance leaveBalance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveBalance createEntity(EntityManager em) {
        LeaveBalance leaveBalance = new LeaveBalance()
            .totalDays(DEFAULT_TOTAL_DAYS)
            .remainingDays(DEFAULT_REMAINING_DAYS)
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .remarks(DEFAULT_REMARKS)
            .lastSynchronizedOn(DEFAULT_LAST_SYNCHRONIZED_ON)
            .status(DEFAULT_STATUS)
            .assessmentYear(DEFAULT_ASSESSMENT_YEAR)
            .amount(DEFAULT_AMOUNT);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        leaveBalance.setEmployee(employee);
        // Add required entity
        LeaveType leaveType;
        if (TestUtil.findAll(em, LeaveType.class).isEmpty()) {
            leaveType = LeaveTypeResourceIT.createEntity(em);
            em.persist(leaveType);
            em.flush();
        } else {
            leaveType = TestUtil.findAll(em, LeaveType.class).get(0);
        }
        leaveBalance.setLeaveType(leaveType);
        return leaveBalance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeaveBalance createUpdatedEntity(EntityManager em) {
        LeaveBalance leaveBalance = new LeaveBalance()
            .totalDays(UPDATED_TOTAL_DAYS)
            .remainingDays(UPDATED_REMAINING_DAYS)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .remarks(UPDATED_REMARKS)
            .lastSynchronizedOn(UPDATED_LAST_SYNCHRONIZED_ON)
            .status(UPDATED_STATUS)
            .assessmentYear(UPDATED_ASSESSMENT_YEAR)
            .amount(UPDATED_AMOUNT);
        // Add required entity
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employee = EmployeeResourceIT.createUpdatedEntity(em);
            em.persist(employee);
            em.flush();
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        leaveBalance.setEmployee(employee);
        // Add required entity
        LeaveType leaveType;
        if (TestUtil.findAll(em, LeaveType.class).isEmpty()) {
            leaveType = LeaveTypeResourceIT.createUpdatedEntity(em);
            em.persist(leaveType);
            em.flush();
        } else {
            leaveType = TestUtil.findAll(em, LeaveType.class).get(0);
        }
        leaveBalance.setLeaveType(leaveType);
        return leaveBalance;
    }

    @BeforeEach
    public void initTest() {
        leaveBalance = createEntity(em);
    }

    @Test
    @Transactional
    public void createLeaveBalance() throws Exception {
        int databaseSizeBeforeCreate = leaveBalanceRepository.findAll().size();
        // Create the LeaveBalance
        restLeaveBalanceMockMvc.perform(post("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveBalance)))
            .andExpect(status().isCreated());

        // Validate the LeaveBalance in the database
        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeCreate + 1);
        LeaveBalance testLeaveBalance = leaveBalanceList.get(leaveBalanceList.size() - 1);
        assertThat(testLeaveBalance.getTotalDays()).isEqualTo(DEFAULT_TOTAL_DAYS);
        assertThat(testLeaveBalance.getRemainingDays()).isEqualTo(DEFAULT_REMAINING_DAYS);
        assertThat(testLeaveBalance.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testLeaveBalance.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testLeaveBalance.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testLeaveBalance.getLastSynchronizedOn()).isEqualTo(DEFAULT_LAST_SYNCHRONIZED_ON);
        assertThat(testLeaveBalance.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLeaveBalance.getAssessmentYear()).isEqualTo(DEFAULT_ASSESSMENT_YEAR);
        assertThat(testLeaveBalance.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createLeaveBalanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = leaveBalanceRepository.findAll().size();

        // Create the LeaveBalance with an existing ID
        leaveBalance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeaveBalanceMockMvc.perform(post("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveBalance)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveBalance in the database
        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTotalDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveBalanceRepository.findAll().size();
        // set the field null
        leaveBalance.setTotalDays(null);

        // Create the LeaveBalance, which fails.


        restLeaveBalanceMockMvc.perform(post("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveBalance)))
            .andExpect(status().isBadRequest());

        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRemainingDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveBalanceRepository.findAll().size();
        // set the field null
        leaveBalance.setRemainingDays(null);

        // Create the LeaveBalance, which fails.


        restLeaveBalanceMockMvc.perform(post("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveBalance)))
            .andExpect(status().isBadRequest());

        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveBalanceRepository.findAll().size();
        // set the field null
        leaveBalance.setFrom(null);

        // Create the LeaveBalance, which fails.


        restLeaveBalanceMockMvc.perform(post("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveBalance)))
            .andExpect(status().isBadRequest());

        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveBalanceRepository.findAll().size();
        // set the field null
        leaveBalance.setTo(null);

        // Create the LeaveBalance, which fails.


        restLeaveBalanceMockMvc.perform(post("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveBalance)))
            .andExpect(status().isBadRequest());

        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveBalanceRepository.findAll().size();
        // set the field null
        leaveBalance.setStatus(null);

        // Create the LeaveBalance, which fails.


        restLeaveBalanceMockMvc.perform(post("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveBalance)))
            .andExpect(status().isBadRequest());

        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAssessmentYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = leaveBalanceRepository.findAll().size();
        // set the field null
        leaveBalance.setAssessmentYear(null);

        // Create the LeaveBalance, which fails.


        restLeaveBalanceMockMvc.perform(post("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveBalance)))
            .andExpect(status().isBadRequest());

        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLeaveBalances() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList
        restLeaveBalanceMockMvc.perform(get("/api/leave-balances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].remainingDays").value(hasItem(DEFAULT_REMAINING_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].lastSynchronizedOn").value(hasItem(DEFAULT_LAST_SYNCHRONIZED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].assessmentYear").value(hasItem(DEFAULT_ASSESSMENT_YEAR)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getLeaveBalance() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get the leaveBalance
        restLeaveBalanceMockMvc.perform(get("/api/leave-balances/{id}", leaveBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leaveBalance.getId().intValue()))
            .andExpect(jsonPath("$.totalDays").value(DEFAULT_TOTAL_DAYS.intValue()))
            .andExpect(jsonPath("$.remainingDays").value(DEFAULT_REMAINING_DAYS.intValue()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.lastSynchronizedOn").value(DEFAULT_LAST_SYNCHRONIZED_ON.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.assessmentYear").value(DEFAULT_ASSESSMENT_YEAR))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }


    @Test
    @Transactional
    public void getLeaveBalancesByIdFiltering() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        Long id = leaveBalance.getId();

        defaultLeaveBalanceShouldBeFound("id.equals=" + id);
        defaultLeaveBalanceShouldNotBeFound("id.notEquals=" + id);

        defaultLeaveBalanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLeaveBalanceShouldNotBeFound("id.greaterThan=" + id);

        defaultLeaveBalanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLeaveBalanceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByTotalDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where totalDays equals to DEFAULT_TOTAL_DAYS
        defaultLeaveBalanceShouldBeFound("totalDays.equals=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveBalanceList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultLeaveBalanceShouldNotBeFound("totalDays.equals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByTotalDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where totalDays not equals to DEFAULT_TOTAL_DAYS
        defaultLeaveBalanceShouldNotBeFound("totalDays.notEquals=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveBalanceList where totalDays not equals to UPDATED_TOTAL_DAYS
        defaultLeaveBalanceShouldBeFound("totalDays.notEquals=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByTotalDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where totalDays in DEFAULT_TOTAL_DAYS or UPDATED_TOTAL_DAYS
        defaultLeaveBalanceShouldBeFound("totalDays.in=" + DEFAULT_TOTAL_DAYS + "," + UPDATED_TOTAL_DAYS);

        // Get all the leaveBalanceList where totalDays equals to UPDATED_TOTAL_DAYS
        defaultLeaveBalanceShouldNotBeFound("totalDays.in=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByTotalDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where totalDays is not null
        defaultLeaveBalanceShouldBeFound("totalDays.specified=true");

        // Get all the leaveBalanceList where totalDays is null
        defaultLeaveBalanceShouldNotBeFound("totalDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByTotalDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where totalDays is greater than or equal to DEFAULT_TOTAL_DAYS
        defaultLeaveBalanceShouldBeFound("totalDays.greaterThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveBalanceList where totalDays is greater than or equal to UPDATED_TOTAL_DAYS
        defaultLeaveBalanceShouldNotBeFound("totalDays.greaterThanOrEqual=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByTotalDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where totalDays is less than or equal to DEFAULT_TOTAL_DAYS
        defaultLeaveBalanceShouldBeFound("totalDays.lessThanOrEqual=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveBalanceList where totalDays is less than or equal to SMALLER_TOTAL_DAYS
        defaultLeaveBalanceShouldNotBeFound("totalDays.lessThanOrEqual=" + SMALLER_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByTotalDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where totalDays is less than DEFAULT_TOTAL_DAYS
        defaultLeaveBalanceShouldNotBeFound("totalDays.lessThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveBalanceList where totalDays is less than UPDATED_TOTAL_DAYS
        defaultLeaveBalanceShouldBeFound("totalDays.lessThan=" + UPDATED_TOTAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByTotalDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where totalDays is greater than DEFAULT_TOTAL_DAYS
        defaultLeaveBalanceShouldNotBeFound("totalDays.greaterThan=" + DEFAULT_TOTAL_DAYS);

        // Get all the leaveBalanceList where totalDays is greater than SMALLER_TOTAL_DAYS
        defaultLeaveBalanceShouldBeFound("totalDays.greaterThan=" + SMALLER_TOTAL_DAYS);
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByRemainingDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remainingDays equals to DEFAULT_REMAINING_DAYS
        defaultLeaveBalanceShouldBeFound("remainingDays.equals=" + DEFAULT_REMAINING_DAYS);

        // Get all the leaveBalanceList where remainingDays equals to UPDATED_REMAINING_DAYS
        defaultLeaveBalanceShouldNotBeFound("remainingDays.equals=" + UPDATED_REMAINING_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemainingDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remainingDays not equals to DEFAULT_REMAINING_DAYS
        defaultLeaveBalanceShouldNotBeFound("remainingDays.notEquals=" + DEFAULT_REMAINING_DAYS);

        // Get all the leaveBalanceList where remainingDays not equals to UPDATED_REMAINING_DAYS
        defaultLeaveBalanceShouldBeFound("remainingDays.notEquals=" + UPDATED_REMAINING_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemainingDaysIsInShouldWork() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remainingDays in DEFAULT_REMAINING_DAYS or UPDATED_REMAINING_DAYS
        defaultLeaveBalanceShouldBeFound("remainingDays.in=" + DEFAULT_REMAINING_DAYS + "," + UPDATED_REMAINING_DAYS);

        // Get all the leaveBalanceList where remainingDays equals to UPDATED_REMAINING_DAYS
        defaultLeaveBalanceShouldNotBeFound("remainingDays.in=" + UPDATED_REMAINING_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemainingDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remainingDays is not null
        defaultLeaveBalanceShouldBeFound("remainingDays.specified=true");

        // Get all the leaveBalanceList where remainingDays is null
        defaultLeaveBalanceShouldNotBeFound("remainingDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemainingDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remainingDays is greater than or equal to DEFAULT_REMAINING_DAYS
        defaultLeaveBalanceShouldBeFound("remainingDays.greaterThanOrEqual=" + DEFAULT_REMAINING_DAYS);

        // Get all the leaveBalanceList where remainingDays is greater than or equal to UPDATED_REMAINING_DAYS
        defaultLeaveBalanceShouldNotBeFound("remainingDays.greaterThanOrEqual=" + UPDATED_REMAINING_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemainingDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remainingDays is less than or equal to DEFAULT_REMAINING_DAYS
        defaultLeaveBalanceShouldBeFound("remainingDays.lessThanOrEqual=" + DEFAULT_REMAINING_DAYS);

        // Get all the leaveBalanceList where remainingDays is less than or equal to SMALLER_REMAINING_DAYS
        defaultLeaveBalanceShouldNotBeFound("remainingDays.lessThanOrEqual=" + SMALLER_REMAINING_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemainingDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remainingDays is less than DEFAULT_REMAINING_DAYS
        defaultLeaveBalanceShouldNotBeFound("remainingDays.lessThan=" + DEFAULT_REMAINING_DAYS);

        // Get all the leaveBalanceList where remainingDays is less than UPDATED_REMAINING_DAYS
        defaultLeaveBalanceShouldBeFound("remainingDays.lessThan=" + UPDATED_REMAINING_DAYS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemainingDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remainingDays is greater than DEFAULT_REMAINING_DAYS
        defaultLeaveBalanceShouldNotBeFound("remainingDays.greaterThan=" + DEFAULT_REMAINING_DAYS);

        // Get all the leaveBalanceList where remainingDays is greater than SMALLER_REMAINING_DAYS
        defaultLeaveBalanceShouldBeFound("remainingDays.greaterThan=" + SMALLER_REMAINING_DAYS);
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByFromIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where from equals to DEFAULT_FROM
        defaultLeaveBalanceShouldBeFound("from.equals=" + DEFAULT_FROM);

        // Get all the leaveBalanceList where from equals to UPDATED_FROM
        defaultLeaveBalanceShouldNotBeFound("from.equals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where from not equals to DEFAULT_FROM
        defaultLeaveBalanceShouldNotBeFound("from.notEquals=" + DEFAULT_FROM);

        // Get all the leaveBalanceList where from not equals to UPDATED_FROM
        defaultLeaveBalanceShouldBeFound("from.notEquals=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByFromIsInShouldWork() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where from in DEFAULT_FROM or UPDATED_FROM
        defaultLeaveBalanceShouldBeFound("from.in=" + DEFAULT_FROM + "," + UPDATED_FROM);

        // Get all the leaveBalanceList where from equals to UPDATED_FROM
        defaultLeaveBalanceShouldNotBeFound("from.in=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where from is not null
        defaultLeaveBalanceShouldBeFound("from.specified=true");

        // Get all the leaveBalanceList where from is null
        defaultLeaveBalanceShouldNotBeFound("from.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where from is greater than or equal to DEFAULT_FROM
        defaultLeaveBalanceShouldBeFound("from.greaterThanOrEqual=" + DEFAULT_FROM);

        // Get all the leaveBalanceList where from is greater than or equal to UPDATED_FROM
        defaultLeaveBalanceShouldNotBeFound("from.greaterThanOrEqual=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where from is less than or equal to DEFAULT_FROM
        defaultLeaveBalanceShouldBeFound("from.lessThanOrEqual=" + DEFAULT_FROM);

        // Get all the leaveBalanceList where from is less than or equal to SMALLER_FROM
        defaultLeaveBalanceShouldNotBeFound("from.lessThanOrEqual=" + SMALLER_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByFromIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where from is less than DEFAULT_FROM
        defaultLeaveBalanceShouldNotBeFound("from.lessThan=" + DEFAULT_FROM);

        // Get all the leaveBalanceList where from is less than UPDATED_FROM
        defaultLeaveBalanceShouldBeFound("from.lessThan=" + UPDATED_FROM);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where from is greater than DEFAULT_FROM
        defaultLeaveBalanceShouldNotBeFound("from.greaterThan=" + DEFAULT_FROM);

        // Get all the leaveBalanceList where from is greater than SMALLER_FROM
        defaultLeaveBalanceShouldBeFound("from.greaterThan=" + SMALLER_FROM);
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByToIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where to equals to DEFAULT_TO
        defaultLeaveBalanceShouldBeFound("to.equals=" + DEFAULT_TO);

        // Get all the leaveBalanceList where to equals to UPDATED_TO
        defaultLeaveBalanceShouldNotBeFound("to.equals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where to not equals to DEFAULT_TO
        defaultLeaveBalanceShouldNotBeFound("to.notEquals=" + DEFAULT_TO);

        // Get all the leaveBalanceList where to not equals to UPDATED_TO
        defaultLeaveBalanceShouldBeFound("to.notEquals=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByToIsInShouldWork() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where to in DEFAULT_TO or UPDATED_TO
        defaultLeaveBalanceShouldBeFound("to.in=" + DEFAULT_TO + "," + UPDATED_TO);

        // Get all the leaveBalanceList where to equals to UPDATED_TO
        defaultLeaveBalanceShouldNotBeFound("to.in=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByToIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where to is not null
        defaultLeaveBalanceShouldBeFound("to.specified=true");

        // Get all the leaveBalanceList where to is null
        defaultLeaveBalanceShouldNotBeFound("to.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where to is greater than or equal to DEFAULT_TO
        defaultLeaveBalanceShouldBeFound("to.greaterThanOrEqual=" + DEFAULT_TO);

        // Get all the leaveBalanceList where to is greater than or equal to UPDATED_TO
        defaultLeaveBalanceShouldNotBeFound("to.greaterThanOrEqual=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where to is less than or equal to DEFAULT_TO
        defaultLeaveBalanceShouldBeFound("to.lessThanOrEqual=" + DEFAULT_TO);

        // Get all the leaveBalanceList where to is less than or equal to SMALLER_TO
        defaultLeaveBalanceShouldNotBeFound("to.lessThanOrEqual=" + SMALLER_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByToIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where to is less than DEFAULT_TO
        defaultLeaveBalanceShouldNotBeFound("to.lessThan=" + DEFAULT_TO);

        // Get all the leaveBalanceList where to is less than UPDATED_TO
        defaultLeaveBalanceShouldBeFound("to.lessThan=" + UPDATED_TO);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where to is greater than DEFAULT_TO
        defaultLeaveBalanceShouldNotBeFound("to.greaterThan=" + DEFAULT_TO);

        // Get all the leaveBalanceList where to is greater than SMALLER_TO
        defaultLeaveBalanceShouldBeFound("to.greaterThan=" + SMALLER_TO);
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remarks equals to DEFAULT_REMARKS
        defaultLeaveBalanceShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the leaveBalanceList where remarks equals to UPDATED_REMARKS
        defaultLeaveBalanceShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remarks not equals to DEFAULT_REMARKS
        defaultLeaveBalanceShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the leaveBalanceList where remarks not equals to UPDATED_REMARKS
        defaultLeaveBalanceShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultLeaveBalanceShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the leaveBalanceList where remarks equals to UPDATED_REMARKS
        defaultLeaveBalanceShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remarks is not null
        defaultLeaveBalanceShouldBeFound("remarks.specified=true");

        // Get all the leaveBalanceList where remarks is null
        defaultLeaveBalanceShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllLeaveBalancesByRemarksContainsSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remarks contains DEFAULT_REMARKS
        defaultLeaveBalanceShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the leaveBalanceList where remarks contains UPDATED_REMARKS
        defaultLeaveBalanceShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where remarks does not contain DEFAULT_REMARKS
        defaultLeaveBalanceShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the leaveBalanceList where remarks does not contain UPDATED_REMARKS
        defaultLeaveBalanceShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByLastSynchronizedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where lastSynchronizedOn equals to DEFAULT_LAST_SYNCHRONIZED_ON
        defaultLeaveBalanceShouldBeFound("lastSynchronizedOn.equals=" + DEFAULT_LAST_SYNCHRONIZED_ON);

        // Get all the leaveBalanceList where lastSynchronizedOn equals to UPDATED_LAST_SYNCHRONIZED_ON
        defaultLeaveBalanceShouldNotBeFound("lastSynchronizedOn.equals=" + UPDATED_LAST_SYNCHRONIZED_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByLastSynchronizedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where lastSynchronizedOn not equals to DEFAULT_LAST_SYNCHRONIZED_ON
        defaultLeaveBalanceShouldNotBeFound("lastSynchronizedOn.notEquals=" + DEFAULT_LAST_SYNCHRONIZED_ON);

        // Get all the leaveBalanceList where lastSynchronizedOn not equals to UPDATED_LAST_SYNCHRONIZED_ON
        defaultLeaveBalanceShouldBeFound("lastSynchronizedOn.notEquals=" + UPDATED_LAST_SYNCHRONIZED_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByLastSynchronizedOnIsInShouldWork() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where lastSynchronizedOn in DEFAULT_LAST_SYNCHRONIZED_ON or UPDATED_LAST_SYNCHRONIZED_ON
        defaultLeaveBalanceShouldBeFound("lastSynchronizedOn.in=" + DEFAULT_LAST_SYNCHRONIZED_ON + "," + UPDATED_LAST_SYNCHRONIZED_ON);

        // Get all the leaveBalanceList where lastSynchronizedOn equals to UPDATED_LAST_SYNCHRONIZED_ON
        defaultLeaveBalanceShouldNotBeFound("lastSynchronizedOn.in=" + UPDATED_LAST_SYNCHRONIZED_ON);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByLastSynchronizedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where lastSynchronizedOn is not null
        defaultLeaveBalanceShouldBeFound("lastSynchronizedOn.specified=true");

        // Get all the leaveBalanceList where lastSynchronizedOn is null
        defaultLeaveBalanceShouldNotBeFound("lastSynchronizedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where status equals to DEFAULT_STATUS
        defaultLeaveBalanceShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the leaveBalanceList where status equals to UPDATED_STATUS
        defaultLeaveBalanceShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where status not equals to DEFAULT_STATUS
        defaultLeaveBalanceShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the leaveBalanceList where status not equals to UPDATED_STATUS
        defaultLeaveBalanceShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLeaveBalanceShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the leaveBalanceList where status equals to UPDATED_STATUS
        defaultLeaveBalanceShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where status is not null
        defaultLeaveBalanceShouldBeFound("status.specified=true");

        // Get all the leaveBalanceList where status is null
        defaultLeaveBalanceShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAssessmentYearIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where assessmentYear equals to DEFAULT_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldBeFound("assessmentYear.equals=" + DEFAULT_ASSESSMENT_YEAR);

        // Get all the leaveBalanceList where assessmentYear equals to UPDATED_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldNotBeFound("assessmentYear.equals=" + UPDATED_ASSESSMENT_YEAR);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAssessmentYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where assessmentYear not equals to DEFAULT_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldNotBeFound("assessmentYear.notEquals=" + DEFAULT_ASSESSMENT_YEAR);

        // Get all the leaveBalanceList where assessmentYear not equals to UPDATED_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldBeFound("assessmentYear.notEquals=" + UPDATED_ASSESSMENT_YEAR);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAssessmentYearIsInShouldWork() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where assessmentYear in DEFAULT_ASSESSMENT_YEAR or UPDATED_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldBeFound("assessmentYear.in=" + DEFAULT_ASSESSMENT_YEAR + "," + UPDATED_ASSESSMENT_YEAR);

        // Get all the leaveBalanceList where assessmentYear equals to UPDATED_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldNotBeFound("assessmentYear.in=" + UPDATED_ASSESSMENT_YEAR);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAssessmentYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where assessmentYear is not null
        defaultLeaveBalanceShouldBeFound("assessmentYear.specified=true");

        // Get all the leaveBalanceList where assessmentYear is null
        defaultLeaveBalanceShouldNotBeFound("assessmentYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAssessmentYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where assessmentYear is greater than or equal to DEFAULT_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldBeFound("assessmentYear.greaterThanOrEqual=" + DEFAULT_ASSESSMENT_YEAR);

        // Get all the leaveBalanceList where assessmentYear is greater than or equal to UPDATED_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldNotBeFound("assessmentYear.greaterThanOrEqual=" + UPDATED_ASSESSMENT_YEAR);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAssessmentYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where assessmentYear is less than or equal to DEFAULT_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldBeFound("assessmentYear.lessThanOrEqual=" + DEFAULT_ASSESSMENT_YEAR);

        // Get all the leaveBalanceList where assessmentYear is less than or equal to SMALLER_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldNotBeFound("assessmentYear.lessThanOrEqual=" + SMALLER_ASSESSMENT_YEAR);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAssessmentYearIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where assessmentYear is less than DEFAULT_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldNotBeFound("assessmentYear.lessThan=" + DEFAULT_ASSESSMENT_YEAR);

        // Get all the leaveBalanceList where assessmentYear is less than UPDATED_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldBeFound("assessmentYear.lessThan=" + UPDATED_ASSESSMENT_YEAR);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAssessmentYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where assessmentYear is greater than DEFAULT_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldNotBeFound("assessmentYear.greaterThan=" + DEFAULT_ASSESSMENT_YEAR);

        // Get all the leaveBalanceList where assessmentYear is greater than SMALLER_ASSESSMENT_YEAR
        defaultLeaveBalanceShouldBeFound("assessmentYear.greaterThan=" + SMALLER_ASSESSMENT_YEAR);
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where amount equals to DEFAULT_AMOUNT
        defaultLeaveBalanceShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the leaveBalanceList where amount equals to UPDATED_AMOUNT
        defaultLeaveBalanceShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where amount not equals to DEFAULT_AMOUNT
        defaultLeaveBalanceShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the leaveBalanceList where amount not equals to UPDATED_AMOUNT
        defaultLeaveBalanceShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultLeaveBalanceShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the leaveBalanceList where amount equals to UPDATED_AMOUNT
        defaultLeaveBalanceShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where amount is not null
        defaultLeaveBalanceShouldBeFound("amount.specified=true");

        // Get all the leaveBalanceList where amount is null
        defaultLeaveBalanceShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultLeaveBalanceShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the leaveBalanceList where amount is greater than or equal to UPDATED_AMOUNT
        defaultLeaveBalanceShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where amount is less than or equal to DEFAULT_AMOUNT
        defaultLeaveBalanceShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the leaveBalanceList where amount is less than or equal to SMALLER_AMOUNT
        defaultLeaveBalanceShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where amount is less than DEFAULT_AMOUNT
        defaultLeaveBalanceShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the leaveBalanceList where amount is less than UPDATED_AMOUNT
        defaultLeaveBalanceShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLeaveBalancesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);

        // Get all the leaveBalanceList where amount is greater than DEFAULT_AMOUNT
        defaultLeaveBalanceShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the leaveBalanceList where amount is greater than SMALLER_AMOUNT
        defaultLeaveBalanceShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByEmployeeIsEqualToSomething() throws Exception {
        // Get already existing entity
        Employee employee = leaveBalance.getEmployee();
        leaveBalanceRepository.saveAndFlush(leaveBalance);
        Long employeeId = employee.getId();

        // Get all the leaveBalanceList where employee equals to employeeId
        defaultLeaveBalanceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the leaveBalanceList where employee equals to employeeId + 1
        defaultLeaveBalanceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByLeaveTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        LeaveType leaveType = leaveBalance.getLeaveType();
        leaveBalanceRepository.saveAndFlush(leaveBalance);
        Long leaveTypeId = leaveType.getId();

        // Get all the leaveBalanceList where leaveType equals to leaveTypeId
        defaultLeaveBalanceShouldBeFound("leaveTypeId.equals=" + leaveTypeId);

        // Get all the leaveBalanceList where leaveType equals to leaveTypeId + 1
        defaultLeaveBalanceShouldNotBeFound("leaveTypeId.equals=" + (leaveTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        leaveBalance.setDepartment(department);
        leaveBalanceRepository.saveAndFlush(leaveBalance);
        Long departmentId = department.getId();

        // Get all the leaveBalanceList where department equals to departmentId
        defaultLeaveBalanceShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the leaveBalanceList where department equals to departmentId + 1
        defaultLeaveBalanceShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }


    @Test
    @Transactional
    public void getAllLeaveBalancesByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        leaveBalanceRepository.saveAndFlush(leaveBalance);
        Designation designation = DesignationResourceIT.createEntity(em);
        em.persist(designation);
        em.flush();
        leaveBalance.setDesignation(designation);
        leaveBalanceRepository.saveAndFlush(leaveBalance);
        Long designationId = designation.getId();

        // Get all the leaveBalanceList where designation equals to designationId
        defaultLeaveBalanceShouldBeFound("designationId.equals=" + designationId);

        // Get all the leaveBalanceList where designation equals to designationId + 1
        defaultLeaveBalanceShouldNotBeFound("designationId.equals=" + (designationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLeaveBalanceShouldBeFound(String filter) throws Exception {
        restLeaveBalanceMockMvc.perform(get("/api/leave-balances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leaveBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalDays").value(hasItem(DEFAULT_TOTAL_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].remainingDays").value(hasItem(DEFAULT_REMAINING_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].lastSynchronizedOn").value(hasItem(DEFAULT_LAST_SYNCHRONIZED_ON.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].assessmentYear").value(hasItem(DEFAULT_ASSESSMENT_YEAR)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));

        // Check, that the count call also returns 1
        restLeaveBalanceMockMvc.perform(get("/api/leave-balances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLeaveBalanceShouldNotBeFound(String filter) throws Exception {
        restLeaveBalanceMockMvc.perform(get("/api/leave-balances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLeaveBalanceMockMvc.perform(get("/api/leave-balances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLeaveBalance() throws Exception {
        // Get the leaveBalance
        restLeaveBalanceMockMvc.perform(get("/api/leave-balances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLeaveBalance() throws Exception {
        // Initialize the database
        leaveBalanceService.save(leaveBalance);

        int databaseSizeBeforeUpdate = leaveBalanceRepository.findAll().size();

        // Update the leaveBalance
        LeaveBalance updatedLeaveBalance = leaveBalanceRepository.findById(leaveBalance.getId()).get();
        // Disconnect from session so that the updates on updatedLeaveBalance are not directly saved in db
        em.detach(updatedLeaveBalance);
        updatedLeaveBalance
            .totalDays(UPDATED_TOTAL_DAYS)
            .remainingDays(UPDATED_REMAINING_DAYS)
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .remarks(UPDATED_REMARKS)
            .lastSynchronizedOn(UPDATED_LAST_SYNCHRONIZED_ON)
            .status(UPDATED_STATUS)
            .assessmentYear(UPDATED_ASSESSMENT_YEAR)
            .amount(UPDATED_AMOUNT);

        restLeaveBalanceMockMvc.perform(put("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLeaveBalance)))
            .andExpect(status().isOk());

        // Validate the LeaveBalance in the database
        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeUpdate);
        LeaveBalance testLeaveBalance = leaveBalanceList.get(leaveBalanceList.size() - 1);
        assertThat(testLeaveBalance.getTotalDays()).isEqualTo(UPDATED_TOTAL_DAYS);
        assertThat(testLeaveBalance.getRemainingDays()).isEqualTo(UPDATED_REMAINING_DAYS);
        assertThat(testLeaveBalance.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testLeaveBalance.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testLeaveBalance.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testLeaveBalance.getLastSynchronizedOn()).isEqualTo(UPDATED_LAST_SYNCHRONIZED_ON);
        assertThat(testLeaveBalance.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLeaveBalance.getAssessmentYear()).isEqualTo(UPDATED_ASSESSMENT_YEAR);
        assertThat(testLeaveBalance.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingLeaveBalance() throws Exception {
        int databaseSizeBeforeUpdate = leaveBalanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeaveBalanceMockMvc.perform(put("/api/leave-balances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(leaveBalance)))
            .andExpect(status().isBadRequest());

        // Validate the LeaveBalance in the database
        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLeaveBalance() throws Exception {
        // Initialize the database
        leaveBalanceService.save(leaveBalance);

        int databaseSizeBeforeDelete = leaveBalanceRepository.findAll().size();

        // Delete the leaveBalance
        restLeaveBalanceMockMvc.perform(delete("/api/leave-balances/{id}", leaveBalance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeaveBalance> leaveBalanceList = leaveBalanceRepository.findAll();
        assertThat(leaveBalanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
