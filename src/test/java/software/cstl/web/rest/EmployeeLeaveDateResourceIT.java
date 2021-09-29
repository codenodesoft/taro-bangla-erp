package software.cstl.web.rest;

import software.cstl.CodeNodeErpApp;
import software.cstl.domain.EmployeeLeaveDate;
import software.cstl.repository.EmployeeLeaveDateRepository;

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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmployeeLeaveDateResource} REST controller.
 */
@SpringBootTest(classes = CodeNodeErpApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeLeaveDateResourceIT {

    private static final LocalDate DEFAULT_LEAVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LEAVE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmployeeLeaveDateRepository employeeLeaveDateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeLeaveDateMockMvc;

    private EmployeeLeaveDate employeeLeaveDate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeLeaveDate createEntity(EntityManager em) {
        EmployeeLeaveDate employeeLeaveDate = new EmployeeLeaveDate()
            .leaveDate(DEFAULT_LEAVE_DATE);
        return employeeLeaveDate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeLeaveDate createUpdatedEntity(EntityManager em) {
        EmployeeLeaveDate employeeLeaveDate = new EmployeeLeaveDate()
            .leaveDate(UPDATED_LEAVE_DATE);
        return employeeLeaveDate;
    }

    @BeforeEach
    public void initTest() {
        employeeLeaveDate = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeLeaveDate() throws Exception {
        int databaseSizeBeforeCreate = employeeLeaveDateRepository.findAll().size();
        // Create the EmployeeLeaveDate
        restEmployeeLeaveDateMockMvc.perform(post("/api/employee-leave-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLeaveDate)))
            .andExpect(status().isCreated());

        // Validate the EmployeeLeaveDate in the database
        List<EmployeeLeaveDate> employeeLeaveDateList = employeeLeaveDateRepository.findAll();
        assertThat(employeeLeaveDateList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeLeaveDate testEmployeeLeaveDate = employeeLeaveDateList.get(employeeLeaveDateList.size() - 1);
        assertThat(testEmployeeLeaveDate.getLeaveDate()).isEqualTo(DEFAULT_LEAVE_DATE);
    }

    @Test
    @Transactional
    public void createEmployeeLeaveDateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeLeaveDateRepository.findAll().size();

        // Create the EmployeeLeaveDate with an existing ID
        employeeLeaveDate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeLeaveDateMockMvc.perform(post("/api/employee-leave-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLeaveDate)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLeaveDate in the database
        List<EmployeeLeaveDate> employeeLeaveDateList = employeeLeaveDateRepository.findAll();
        assertThat(employeeLeaveDateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeLeaveDates() throws Exception {
        // Initialize the database
        employeeLeaveDateRepository.saveAndFlush(employeeLeaveDate);

        // Get all the employeeLeaveDateList
        restEmployeeLeaveDateMockMvc.perform(get("/api/employee-leave-dates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeLeaveDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].leaveDate").value(hasItem(DEFAULT_LEAVE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeeLeaveDate() throws Exception {
        // Initialize the database
        employeeLeaveDateRepository.saveAndFlush(employeeLeaveDate);

        // Get the employeeLeaveDate
        restEmployeeLeaveDateMockMvc.perform(get("/api/employee-leave-dates/{id}", employeeLeaveDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeLeaveDate.getId().intValue()))
            .andExpect(jsonPath("$.leaveDate").value(DEFAULT_LEAVE_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmployeeLeaveDate() throws Exception {
        // Get the employeeLeaveDate
        restEmployeeLeaveDateMockMvc.perform(get("/api/employee-leave-dates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeLeaveDate() throws Exception {
        // Initialize the database
        employeeLeaveDateRepository.saveAndFlush(employeeLeaveDate);

        int databaseSizeBeforeUpdate = employeeLeaveDateRepository.findAll().size();

        // Update the employeeLeaveDate
        EmployeeLeaveDate updatedEmployeeLeaveDate = employeeLeaveDateRepository.findById(employeeLeaveDate.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeLeaveDate are not directly saved in db
        em.detach(updatedEmployeeLeaveDate);
        updatedEmployeeLeaveDate
            .leaveDate(UPDATED_LEAVE_DATE);

        restEmployeeLeaveDateMockMvc.perform(put("/api/employee-leave-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeLeaveDate)))
            .andExpect(status().isOk());

        // Validate the EmployeeLeaveDate in the database
        List<EmployeeLeaveDate> employeeLeaveDateList = employeeLeaveDateRepository.findAll();
        assertThat(employeeLeaveDateList).hasSize(databaseSizeBeforeUpdate);
        EmployeeLeaveDate testEmployeeLeaveDate = employeeLeaveDateList.get(employeeLeaveDateList.size() - 1);
        assertThat(testEmployeeLeaveDate.getLeaveDate()).isEqualTo(UPDATED_LEAVE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeLeaveDate() throws Exception {
        int databaseSizeBeforeUpdate = employeeLeaveDateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeLeaveDateMockMvc.perform(put("/api/employee-leave-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employeeLeaveDate)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeLeaveDate in the database
        List<EmployeeLeaveDate> employeeLeaveDateList = employeeLeaveDateRepository.findAll();
        assertThat(employeeLeaveDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeLeaveDate() throws Exception {
        // Initialize the database
        employeeLeaveDateRepository.saveAndFlush(employeeLeaveDate);

        int databaseSizeBeforeDelete = employeeLeaveDateRepository.findAll().size();

        // Delete the employeeLeaveDate
        restEmployeeLeaveDateMockMvc.perform(delete("/api/employee-leave-dates/{id}", employeeLeaveDate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeLeaveDate> employeeLeaveDateList = employeeLeaveDateRepository.findAll();
        assertThat(employeeLeaveDateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
