package software.cstl.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import software.cstl.web.rest.TestUtil;

public class AttendanceSummaryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceSummary.class);
        AttendanceSummary attendanceSummary1 = new AttendanceSummary();
        attendanceSummary1.setId(1L);
        AttendanceSummary attendanceSummary2 = new AttendanceSummary();
        attendanceSummary2.setId(attendanceSummary1.getId());
        assertThat(attendanceSummary1).isEqualTo(attendanceSummary2);
        attendanceSummary2.setId(2L);
        assertThat(attendanceSummary1).isNotEqualTo(attendanceSummary2);
        attendanceSummary1.setId(null);
        assertThat(attendanceSummary1).isNotEqualTo(attendanceSummary2);
    }
}
