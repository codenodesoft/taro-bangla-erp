package software.cstl.web.rest;

import com.itextpdf.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.cstl.service.AttendanceSummaryDailyReportService;
import software.cstl.service.AttendanceSummaryDateWiseReportService;
import software.cstl.service.AttendanceSummaryMonthlyReportService;
import software.cstl.service.AttendanceSummaryYearlyReportService;
import software.cstl.service.dto.AttendanceSummaryCriteria;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api")
public class AttendanceSummaryReportResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryReportResource.class);

    private static final String ENTITY_NAME = "attendanceSummary";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceSummaryDateWiseReportService attendanceSummaryDateWiseReportService;

    private final AttendanceSummaryDailyReportService attendanceSummaryDailyReportService;

    private final AttendanceSummaryMonthlyReportService attendanceSummaryMonthlyReportService;

    private final AttendanceSummaryYearlyReportService attendanceSummaryYearlyReportService;

    public AttendanceSummaryReportResource(AttendanceSummaryDateWiseReportService attendanceSummaryDateWiseReportService,
                                           AttendanceSummaryDailyReportService attendanceSummaryDailyReportService,
                                           AttendanceSummaryMonthlyReportService attendanceSummaryMonthlyReportService,
                                           AttendanceSummaryYearlyReportService attendanceSummaryYearlyReportService) {
        this.attendanceSummaryDateWiseReportService = attendanceSummaryDateWiseReportService;
        this.attendanceSummaryDailyReportService = attendanceSummaryDailyReportService;
        this.attendanceSummaryMonthlyReportService = attendanceSummaryMonthlyReportService;
        this.attendanceSummaryYearlyReportService = attendanceSummaryYearlyReportService;
    }

    @GetMapping(value = "/attendance-summaries/report/dateToDate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAttendanceSummaries(AttendanceSummaryCriteria criteria) throws Exception, DocumentException {
        log.debug("REST request to get attendance summaries to download report {}", criteria);
        ByteArrayInputStream byteArrayInputStream = attendanceSummaryDateWiseReportService.download(criteria);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/attendance-summaries/report");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/attendance-summaries/report/daily", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getDailyAttendanceSummaries(AttendanceSummaryCriteria criteria) throws Exception, DocumentException {
        log.debug("REST request to get yearly attendance summaries to download report {}", criteria);
        ByteArrayInputStream byteArrayInputStream = attendanceSummaryDailyReportService.download(criteria);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/attendance-summaries/report/daily");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/attendance-summaries/report/month/monthly/{monthId}/year/{yearId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getMonthlyAttendanceSummaries(@PathVariable int monthId, @PathVariable int yearId, AttendanceSummaryCriteria criteria) throws Exception, DocumentException {
        log.debug("REST request to get monthly attendance summaries to download report {} {} {}", monthId, yearId, criteria);
        ByteArrayInputStream byteArrayInputStream = attendanceSummaryMonthlyReportService.download(criteria, monthId, yearId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/attendance-summaries/report/month/" + monthId + "/year/" + yearId);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }

    @GetMapping(value = "/attendance-summaries/report/yearly/year/{yearId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getMonthlyAttendanceSummaries(@PathVariable int yearId, AttendanceSummaryCriteria criteria) throws Exception, DocumentException {
        log.debug("REST request to get yearly attendance summaries to download report {} {}", yearId, criteria);
        ByteArrayInputStream byteArrayInputStream = attendanceSummaryYearlyReportService.download(criteria, yearId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/attendance-summaries/report/" + "/year/" + yearId);
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
