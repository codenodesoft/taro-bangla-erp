package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.ScheduledEmployeeLeaveApplication;
import software.cstl.repository.ScheduledEmployeeLeaveApplicationRepository;

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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScheduledEmployeeLeaveApplicationResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ScheduledEmployeeLeaveApplicationResourceIT {

    private static final Long DEFAULT_LEAVE_APPLICATION_ID = 1L;
    private static final Long UPDATED_LEAVE_APPLICATION_ID = 2L;

    private static final Instant DEFAULT_EXECUTED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXECUTED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ScheduledEmployeeLeaveApplicationRepository scheduledEmployeeLeaveApplicationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restScheduledEmployeeLeaveApplicationMockMvc;

    private ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduledEmployeeLeaveApplication createEntity(EntityManager em) {
        ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication = new ScheduledEmployeeLeaveApplication()
            .leaveApplicationId(DEFAULT_LEAVE_APPLICATION_ID)
            .executedOn(DEFAULT_EXECUTED_ON);
        return scheduledEmployeeLeaveApplication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScheduledEmployeeLeaveApplication createUpdatedEntity(EntityManager em) {
        ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication = new ScheduledEmployeeLeaveApplication()
            .leaveApplicationId(UPDATED_LEAVE_APPLICATION_ID)
            .executedOn(UPDATED_EXECUTED_ON);
        return scheduledEmployeeLeaveApplication;
    }

    @BeforeEach
    public void initTest() {
        scheduledEmployeeLeaveApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createScheduledEmployeeLeaveApplication() throws Exception {
        int databaseSizeBeforeCreate = scheduledEmployeeLeaveApplicationRepository.findAll().size();
        // Create the ScheduledEmployeeLeaveApplication
        restScheduledEmployeeLeaveApplicationMockMvc.perform(post("/api/scheduled-employee-leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduledEmployeeLeaveApplication)))
            .andExpect(status().isCreated());

        // Validate the ScheduledEmployeeLeaveApplication in the database
        List<ScheduledEmployeeLeaveApplication> scheduledEmployeeLeaveApplicationList = scheduledEmployeeLeaveApplicationRepository.findAll();
        assertThat(scheduledEmployeeLeaveApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        ScheduledEmployeeLeaveApplication testScheduledEmployeeLeaveApplication = scheduledEmployeeLeaveApplicationList.get(scheduledEmployeeLeaveApplicationList.size() - 1);
        assertThat(testScheduledEmployeeLeaveApplication.getLeaveApplicationId()).isEqualTo(DEFAULT_LEAVE_APPLICATION_ID);
        assertThat(testScheduledEmployeeLeaveApplication.getExecutedOn()).isEqualTo(DEFAULT_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void createScheduledEmployeeLeaveApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scheduledEmployeeLeaveApplicationRepository.findAll().size();

        // Create the ScheduledEmployeeLeaveApplication with an existing ID
        scheduledEmployeeLeaveApplication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScheduledEmployeeLeaveApplicationMockMvc.perform(post("/api/scheduled-employee-leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduledEmployeeLeaveApplication)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduledEmployeeLeaveApplication in the database
        List<ScheduledEmployeeLeaveApplication> scheduledEmployeeLeaveApplicationList = scheduledEmployeeLeaveApplicationRepository.findAll();
        assertThat(scheduledEmployeeLeaveApplicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllScheduledEmployeeLeaveApplications() throws Exception {
        // Initialize the database
        scheduledEmployeeLeaveApplicationRepository.saveAndFlush(scheduledEmployeeLeaveApplication);

        // Get all the scheduledEmployeeLeaveApplicationList
        restScheduledEmployeeLeaveApplicationMockMvc.perform(get("/api/scheduled-employee-leave-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scheduledEmployeeLeaveApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveApplicationId").value(hasItem(DEFAULT_LEAVE_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].executedOn").value(hasItem(DEFAULT_EXECUTED_ON.toString())));
    }
    
    @Test
    @Transactional
    public void getScheduledEmployeeLeaveApplication() throws Exception {
        // Initialize the database
        scheduledEmployeeLeaveApplicationRepository.saveAndFlush(scheduledEmployeeLeaveApplication);

        // Get the scheduledEmployeeLeaveApplication
        restScheduledEmployeeLeaveApplicationMockMvc.perform(get("/api/scheduled-employee-leave-applications/{id}", scheduledEmployeeLeaveApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scheduledEmployeeLeaveApplication.getId().intValue()))
            .andExpect(jsonPath("$.leaveApplicationId").value(DEFAULT_LEAVE_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.executedOn").value(DEFAULT_EXECUTED_ON.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingScheduledEmployeeLeaveApplication() throws Exception {
        // Get the scheduledEmployeeLeaveApplication
        restScheduledEmployeeLeaveApplicationMockMvc.perform(get("/api/scheduled-employee-leave-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScheduledEmployeeLeaveApplication() throws Exception {
        // Initialize the database
        scheduledEmployeeLeaveApplicationRepository.saveAndFlush(scheduledEmployeeLeaveApplication);

        int databaseSizeBeforeUpdate = scheduledEmployeeLeaveApplicationRepository.findAll().size();

        // Update the scheduledEmployeeLeaveApplication
        ScheduledEmployeeLeaveApplication updatedScheduledEmployeeLeaveApplication = scheduledEmployeeLeaveApplicationRepository.findById(scheduledEmployeeLeaveApplication.getId()).get();
        // Disconnect from session so that the updates on updatedScheduledEmployeeLeaveApplication are not directly saved in db
        em.detach(updatedScheduledEmployeeLeaveApplication);
        updatedScheduledEmployeeLeaveApplication
            .leaveApplicationId(UPDATED_LEAVE_APPLICATION_ID)
            .executedOn(UPDATED_EXECUTED_ON);

        restScheduledEmployeeLeaveApplicationMockMvc.perform(put("/api/scheduled-employee-leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedScheduledEmployeeLeaveApplication)))
            .andExpect(status().isOk());

        // Validate the ScheduledEmployeeLeaveApplication in the database
        List<ScheduledEmployeeLeaveApplication> scheduledEmployeeLeaveApplicationList = scheduledEmployeeLeaveApplicationRepository.findAll();
        assertThat(scheduledEmployeeLeaveApplicationList).hasSize(databaseSizeBeforeUpdate);
        ScheduledEmployeeLeaveApplication testScheduledEmployeeLeaveApplication = scheduledEmployeeLeaveApplicationList.get(scheduledEmployeeLeaveApplicationList.size() - 1);
        assertThat(testScheduledEmployeeLeaveApplication.getLeaveApplicationId()).isEqualTo(UPDATED_LEAVE_APPLICATION_ID);
        assertThat(testScheduledEmployeeLeaveApplication.getExecutedOn()).isEqualTo(UPDATED_EXECUTED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingScheduledEmployeeLeaveApplication() throws Exception {
        int databaseSizeBeforeUpdate = scheduledEmployeeLeaveApplicationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScheduledEmployeeLeaveApplicationMockMvc.perform(put("/api/scheduled-employee-leave-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scheduledEmployeeLeaveApplication)))
            .andExpect(status().isBadRequest());

        // Validate the ScheduledEmployeeLeaveApplication in the database
        List<ScheduledEmployeeLeaveApplication> scheduledEmployeeLeaveApplicationList = scheduledEmployeeLeaveApplicationRepository.findAll();
        assertThat(scheduledEmployeeLeaveApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScheduledEmployeeLeaveApplication() throws Exception {
        // Initialize the database
        scheduledEmployeeLeaveApplicationRepository.saveAndFlush(scheduledEmployeeLeaveApplication);

        int databaseSizeBeforeDelete = scheduledEmployeeLeaveApplicationRepository.findAll().size();

        // Delete the scheduledEmployeeLeaveApplication
        restScheduledEmployeeLeaveApplicationMockMvc.perform(delete("/api/scheduled-employee-leave-applications/{id}", scheduledEmployeeLeaveApplication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScheduledEmployeeLeaveApplication> scheduledEmployeeLeaveApplicationList = scheduledEmployeeLeaveApplicationRepository.findAll();
        assertThat(scheduledEmployeeLeaveApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
