package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class AttendanceSynchronizationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceSynchronization.class);
        AttendanceSynchronization attendanceSynchronization1 = new AttendanceSynchronization();
        attendanceSynchronization1.setId(1L);
        AttendanceSynchronization attendanceSynchronization2 = new AttendanceSynchronization();
        attendanceSynchronization2.setId(attendanceSynchronization1.getId());
        assertThat(attendanceSynchronization1).isEqualTo(attendanceSynchronization2);
        attendanceSynchronization2.setId(2L);
        assertThat(attendanceSynchronization1).isNotEqualTo(attendanceSynchronization2);
        attendanceSynchronization1.setId(null);
        assertThat(attendanceSynchronization1).isNotEqualTo(attendanceSynchronization2);
    }
}
