package software.cstl.web.rest;

import com.itextpdf.text.DocumentException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import software.cstl.domain.AttendanceSummary;
import software.cstl.domain.LeaveBalance;
import software.cstl.service.LeaveBalanceQueryService;
import software.cstl.service.LeaveBalanceReportService;
import software.cstl.service.LeaveBalanceService;
import software.cstl.service.dto.AttendanceSummaryCriteria;
import software.cstl.service.dto.LeaveBalanceCriteria;
import software.cstl.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LeaveBalanceReportResource {

    private final Logger log = LoggerFactory.getLogger(LeaveBalanceReportResource.class);

    private static final String ENTITY_NAME = "leaveBalance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeaveBalanceReportService leaveBalanceReportService;

    public LeaveBalanceReportResource(LeaveBalanceReportService leaveBalanceReportService) {
        this.leaveBalanceReportService = leaveBalanceReportService;
    }

    @GetMapping(value = "/leave-balances/report", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAttendanceSummaries(LeaveBalanceCriteria criteria) throws Exception, DocumentException {
        log.debug("REST request to get leave balances to download report {}", criteria);
        ByteArrayInputStream byteArrayInputStream = leaveBalanceReportService.download(criteria);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "/leave-balances/report");
        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(byteArrayInputStream));
    }
}
