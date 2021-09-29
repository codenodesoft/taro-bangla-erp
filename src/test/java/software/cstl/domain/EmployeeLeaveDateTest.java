package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class EmployeeLeaveDateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeLeaveDate.class);
        EmployeeLeaveDate employeeLeaveDate1 = new EmployeeLeaveDate();
        employeeLeaveDate1.setId(1L);
        EmployeeLeaveDate employeeLeaveDate2 = new EmployeeLeaveDate();
        employeeLeaveDate2.setId(employeeLeaveDate1.getId());
        assertThat(employeeLeaveDate1).isEqualTo(employeeLeaveDate2);
        employeeLeaveDate2.setId(2L);
        assertThat(employeeLeaveDate1).isNotEqualTo(employeeLeaveDate2);
        employeeLeaveDate1.setId(null);
        assertThat(employeeLeaveDate1).isNotEqualTo(employeeLeaveDate2);
    }
}
