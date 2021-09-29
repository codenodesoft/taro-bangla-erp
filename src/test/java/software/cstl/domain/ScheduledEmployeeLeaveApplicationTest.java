package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class ScheduledEmployeeLeaveApplicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduledEmployeeLeaveApplication.class);
        ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication1 = new ScheduledEmployeeLeaveApplication();
        scheduledEmployeeLeaveApplication1.setId(1L);
        ScheduledEmployeeLeaveApplication scheduledEmployeeLeaveApplication2 = new ScheduledEmployeeLeaveApplication();
        scheduledEmployeeLeaveApplication2.setId(scheduledEmployeeLeaveApplication1.getId());
        assertThat(scheduledEmployeeLeaveApplication1).isEqualTo(scheduledEmployeeLeaveApplication2);
        scheduledEmployeeLeaveApplication2.setId(2L);
        assertThat(scheduledEmployeeLeaveApplication1).isNotEqualTo(scheduledEmployeeLeaveApplication2);
        scheduledEmployeeLeaveApplication1.setId(null);
        assertThat(scheduledEmployeeLeaveApplication1).isNotEqualTo(scheduledEmployeeLeaveApplication2);
    }
}
