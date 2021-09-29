package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.AttendanceSynchronization;
import software.cstl.repository.AttendanceSynchronizationRepository;
import software.cstl.service.AttendanceSynchronizationService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import software.cstl.domain.enumeration.SynchronizationStatus;
/**
 * Integration tests for the {@link AttendanceSynchronizationResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttendanceSynchronizationResourceIT {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SynchronizationStatus DEFAULT_STATUS = SynchronizationStatus.SUCCESS;
    private static final SynchronizationStatus UPDATED_STATUS = SynchronizationStatus.PROCESSING;

    @Autowired
    private AttendanceSynchronizationRepository attendanceSynchronizationRepository;

    @Autowired
    private AttendanceSynchronizationService attendanceSynchronizationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendanceSynchronizationMockMvc;

    private AttendanceSynchronization attendanceSynchronization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceSynchronization createEntity(EntityManager em) {
        AttendanceSynchronization attendanceSynchronization = new AttendanceSynchronization()
            .fromDate(DEFAULT_FROM_DATE)
            .toDate(DEFAULT_TO_DATE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .status(DEFAULT_STATUS);
        return attendanceSynchronization;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendanceSynchronization createUpdatedEntity(EntityManager em) {
        AttendanceSynchronization attendanceSynchronization = new AttendanceSynchronization()
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .status(UPDATED_STATUS);
        return attendanceSynchronization;
    }

    @BeforeEach
    public void initTest() {
        attendanceSynchronization = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendanceSynchronization() throws Exception {
        int databaseSizeBeforeCreate = attendanceSynchronizationRepository.findAll().size();
        // Create the AttendanceSynchronization
        restAttendanceSynchronizationMockMvc.perform(post("/api/attendance-synchronizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceSynchronization)))
            .andExpect(status().isCreated());

        // Validate the AttendanceSynchronization in the database
        List<AttendanceSynchronization> attendanceSynchronizationList = attendanceSynchronizationRepository.findAll();
        assertThat(attendanceSynchronizationList).hasSize(databaseSizeBeforeCreate + 1);
        AttendanceSynchronization testAttendanceSynchronization = attendanceSynchronizationList.get(attendanceSynchronizationList.size() - 1);
        assertThat(testAttendanceSynchronization.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testAttendanceSynchronization.getToDate()).isEqualTo(DEFAULT_TO_DATE);
        assertThat(testAttendanceSynchronization.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testAttendanceSynchronization.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testAttendanceSynchronization.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAttendanceSynchronizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceSynchronizationRepository.findAll().size();

        // Create the AttendanceSynchronization with an existing ID
        attendanceSynchronization.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceSynchronizationMockMvc.perform(post("/api/attendance-synchronizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceSynchronization)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceSynchronization in the database
        List<AttendanceSynchronization> attendanceSynchronizationList = attendanceSynchronizationRepository.findAll();
        assertThat(attendanceSynchronizationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFromDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceSynchronizationRepository.findAll().size();
        // set the field null
        attendanceSynchronization.setFromDate(null);

        // Create the AttendanceSynchronization, which fails.


        restAttendanceSynchronizationMockMvc.perform(post("/api/attendance-synchronizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceSynchronization)))
            .andExpect(status().isBadRequest());

        List<AttendanceSynchronization> attendanceSynchronizationList = attendanceSynchronizationRepository.findAll();
        assertThat(attendanceSynchronizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkToDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = attendanceSynchronizationRepository.findAll().size();
        // set the field null
        attendanceSynchronization.setToDate(null);

        // Create the AttendanceSynchronization, which fails.


        restAttendanceSynchronizationMockMvc.perform(post("/api/attendance-synchronizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceSynchronization)))
            .andExpect(status().isBadRequest());

        List<AttendanceSynchronization> attendanceSynchronizationList = attendanceSynchronizationRepository.findAll();
        assertThat(attendanceSynchronizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttendanceSynchronizations() throws Exception {
        // Initialize the database
        attendanceSynchronizationRepository.saveAndFlush(attendanceSynchronization);

        // Get all the attendanceSynchronizationList
        restAttendanceSynchronizationMockMvc.perform(get("/api/attendance-synchronizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendanceSynchronization.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].toDate").value(hasItem(DEFAULT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getAttendanceSynchronization() throws Exception {
        // Initialize the database
        attendanceSynchronizationRepository.saveAndFlush(attendanceSynchronization);

        // Get the attendanceSynchronization
        restAttendanceSynchronizationMockMvc.perform(get("/api/attendance-synchronizations/{id}", attendanceSynchronization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendanceSynchronization.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.toDate").value(DEFAULT_TO_DATE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAttendanceSynchronization() throws Exception {
        // Get the attendanceSynchronization
        restAttendanceSynchronizationMockMvc.perform(get("/api/attendance-synchronizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendanceSynchronization() throws Exception {
        // Initialize the database
        attendanceSynchronizationService.save(attendanceSynchronization);

        int databaseSizeBeforeUpdate = attendanceSynchronizationRepository.findAll().size();

        // Update the attendanceSynchronization
        AttendanceSynchronization updatedAttendanceSynchronization = attendanceSynchronizationRepository.findById(attendanceSynchronization.getId()).get();
        // Disconnect from session so that the updates on updatedAttendanceSynchronization are not directly saved in db
        em.detach(updatedAttendanceSynchronization);
        updatedAttendanceSynchronization
            .fromDate(UPDATED_FROM_DATE)
            .toDate(UPDATED_TO_DATE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .status(UPDATED_STATUS);

        restAttendanceSynchronizationMockMvc.perform(put("/api/attendance-synchronizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttendanceSynchronization)))
            .andExpect(status().isOk());

        // Validate the AttendanceSynchronization in the database
        List<AttendanceSynchronization> attendanceSynchronizationList = attendanceSynchronizationRepository.findAll();
        assertThat(attendanceSynchronizationList).hasSize(databaseSizeBeforeUpdate);
        AttendanceSynchronization testAttendanceSynchronization = attendanceSynchronizationList.get(attendanceSynchronizationList.size() - 1);
        assertThat(testAttendanceSynchronization.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testAttendanceSynchronization.getToDate()).isEqualTo(UPDATED_TO_DATE);
        assertThat(testAttendanceSynchronization.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAttendanceSynchronization.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testAttendanceSynchronization.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendanceSynchronization() throws Exception {
        int databaseSizeBeforeUpdate = attendanceSynchronizationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendanceSynchronizationMockMvc.perform(put("/api/attendance-synchronizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendanceSynchronization)))
            .andExpect(status().isBadRequest());

        // Validate the AttendanceSynchronization in the database
        List<AttendanceSynchronization> attendanceSynchronizationList = attendanceSynchronizationRepository.findAll();
        assertThat(attendanceSynchronizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendanceSynchronization() throws Exception {
        // Initialize the database
        attendanceSynchronizationService.save(attendanceSynchronization);

        int databaseSizeBeforeDelete = attendanceSynchronizationRepository.findAll().size();

        // Delete the attendanceSynchronization
        restAttendanceSynchronizationMockMvc.perform(delete("/api/attendance-synchronizations/{id}", attendanceSynchronization.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendanceSynchronization> attendanceSynchronizationList = attendanceSynchronizationRepository.findAll();
        assertThat(attendanceSynchronizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
