package software.cstl.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import io.github.jhipster.service.filter.LocalDateFilter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.*;
import software.cstl.domain.enumeration.*;
import software.cstl.repository.EmployeeRepository;
import software.cstl.repository.LeaveBalanceRepository;
import software.cstl.service.dto.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
@Transactional
public class LeaveBalanceReportService {

    private final Logger log = LoggerFactory.getLogger(LeaveBalanceReportService.class);

    private final LeaveBalanceQueryService leaveBalanceQueryService;

    public static Font mBoldFont = new Font(Font.FontFamily.TIMES_ROMAN, 10f, Font.BOLD, BaseColor.BLACK);

    static Font TIMES_ROMAN_9 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 9);
    static Font TIMES_BOLD_9 = FontFactory.getFont(FontFactory.TIMES_BOLD, 9);
    static Font TIMES_ROMAN_10 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
    static Font TIMES_BOLD_10 = FontFactory.getFont(FontFactory.TIMES_BOLD, 10);
    static Font TIMES_ROMAN_12 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12);
    static Font TIMES_BOLD_12 = FontFactory.getFont(FontFactory.TIMES_BOLD, 12);
    static Font TIMES_ROMAN_14 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
    static Font TIMES_BOLD_14 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14);
    static Font TIMES_BOLD_16 = FontFactory.getFont(FontFactory.TIMES_BOLD, 16);

    public LeaveBalanceReportService(LeaveBalanceQueryService leaveBalanceQueryService) {
        this.leaveBalanceQueryService = leaveBalanceQueryService;
    }

    public ByteArrayInputStream download(LeaveBalanceCriteria criteria) throws DocumentException {
        List<LeaveBalance> leaveBalances = leaveBalanceQueryService.findByCriteria(criteria);

        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(25, 25, 25, 25);
        document.addTitle("Attendance Summary Monthly Report");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new HeaderAndFooter());
        writer.setPageEvent(new Heading(String.valueOf(criteria.getAssessmentYear().getEquals())));
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;
        Phrase phrase = null;

        if (leaveBalances.size() > 0) {
            Map<Department, List<LeaveBalance>> leaveBalancesGroupByDepartment = leaveBalances.stream()
                .collect(Collectors.groupingBy(LeaveBalance::getDepartment));

            int leaveBalancesGroupByDepartmentSize = leaveBalancesGroupByDepartment.size();
            int counter1 = 0;
            for (Map.Entry<Department, List<LeaveBalance>> balancesGroupByDepartment : leaveBalancesGroupByDepartment.entrySet()) {
                Map<Designation, List<LeaveBalance>> leaveBalancesGroupByDesignation = balancesGroupByDepartment.getValue().stream()
                    .collect(Collectors.groupingBy(LeaveBalance::getDesignation));
                counter1++;
                int leaveBalancesGroupByDesignationSize = leaveBalancesGroupByDesignation.size();
                int counter2 = 0;
                for (Map.Entry<Designation, List<LeaveBalance>> balancesGroupByDesignation : leaveBalancesGroupByDesignation.entrySet()) {
                    Map<Employee, List<LeaveBalance>> leaveBalancesGroupByEmployee = balancesGroupByDesignation.getValue().stream()
                        .collect(Collectors.groupingBy(LeaveBalance::getEmployee));
                    counter2++;
                    int leaveBalancesGroupByEmployeeSize = leaveBalancesGroupByEmployee.size();
                    int counter3 = 0;
                    for (Map.Entry<Employee, List<LeaveBalance>> balancesGroupByEmployee : leaveBalancesGroupByEmployee.entrySet()) {
                        counter3++;

                        contentHeading(document, balancesGroupByEmployee);
                        oneLineBreak(document);
                        content(document, balancesGroupByEmployee);
                        if (counter3 < leaveBalancesGroupByEmployeeSize || counter2 < leaveBalancesGroupByDesignationSize || counter1 < leaveBalancesGroupByDepartmentSize) {
                            document.newPage();
                        }
                    }
                }
            }
        } else {
            paragraph = new Paragraph("No record(s) found.", TIMES_BOLD_12);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
        }
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void content(Document document, Map.Entry<Employee, List<LeaveBalance>> leaveBalancesGroupByEmployee) throws DocumentException {
        PdfPTable pdfPTable;
        PdfPCell pdfPCell;
        pdfPTable = new PdfPTable(7);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{8f, 21f, 12f, 12f, 14f, 19f, 14f});

        pdfPCell = new PdfPCell(new Paragraph("SL NO", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("LEAVE NAME", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("FROM", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("TO", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("TOTAL DAYS", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("REMAINING DAYS", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("STATUS", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        int serial = 0;
        for (LeaveBalance balance : leaveBalancesGroupByEmployee.getValue()) {
            serial++;
            pdfPCell = new PdfPCell(new Paragraph(String.valueOf(serial), TIMES_ROMAN_10));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(balance.getLeaveType() == null ? "" : balance.getLeaveType().getName().name(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(balance.getFrom() == null ? "" : balance.getFrom().toString(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(balance.getTo() == null ? "" : balance.getTo().toString(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(balance.getTotalDays() == null ? "" : balance.getTotalDays().toString(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(balance.getRemainingDays() == null ? "" : balance.getRemainingDays().toString(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(balance.getStatus() == null ? "" : balance.getStatus().name(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);
        }
        document.add(pdfPTable);
        oneLineBreak(document);
    }

    private void contentHeading(Document document, Map.Entry<Employee, List<LeaveBalance>> leaveBalancesGroupByEmployee) throws DocumentException {
        PdfPTable pdfPTable;
        PdfPCell pdfPCell;
        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph("-------------------------------------------------------------------------------------------------------------", TIMES_ROMAN_12));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(6);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{15f, 2f, 33f, 10f, 2f, 38f});

        pdfPCell = new PdfPCell(new Paragraph("Employee ID", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(leaveBalancesGroupByEmployee.getKey().getEmpId(), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Name", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(leaveBalancesGroupByEmployee.getKey().getName(), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Business Unit", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(leaveBalancesGroupByEmployee.getKey().getDepartment() == null ? "" : leaveBalancesGroupByEmployee.getKey().getDepartment().getName(), TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Job Title", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(leaveBalancesGroupByEmployee.getKey().getDesignation() == null ? "" : leaveBalancesGroupByEmployee.getKey().getDesignation().getName(), TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Line", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(leaveBalancesGroupByEmployee.getKey().getLine() == null ? "" : leaveBalancesGroupByEmployee.getKey().getLine().getName(), TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Join Date", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(leaveBalancesGroupByEmployee.getKey().getJoiningDate() == null ? "" : leaveBalancesGroupByEmployee.getKey().getJoiningDate().toString(), TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);

        pdfPTable = new PdfPTable(1);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{100f});

        pdfPCell = new PdfPCell(new Paragraph("-------------------------------------------------------------------------------------------------------------", TIMES_ROMAN_12));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        document.add(pdfPTable);
    }

    static class HeaderAndFooter extends PdfPageEventHelper {

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now();
            PdfContentByte cb = writer.getDirectContent();
            Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.BOLDITALIC, BaseColor.BLACK);
            DateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
            Paragraph header = new Paragraph("Generated on " + CommonService.parseDate(zonedDateTime) + " at " + CommonService.parseTime(zonedDateTime), headerFont);
            header.setAlignment(Element.ALIGN_RIGHT);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(header),
                document.right(), document.top() + 10, 0);

        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            String text = String.format("Page %s", writer.getCurrentPageNumber());
            Paragraph paragraph = new Paragraph(text, mBoldFont);
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, new Phrase(paragraph), (document.right() - document.left()) + document.leftMargin(), document.bottom() - 10, 0);
        }
    }

    static class Heading extends PdfPageEventHelper {
        private String text;

        Heading() {
        }

        Heading(String text) {
            this.text = text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @SneakyThrows
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            Paragraph paragraph;
            PdfPTable pdfPTable;
            PdfPCell pdfPCell;
            Phrase phrase;

            paragraph = new Paragraph("Good Day Apparels Ltd.", TIMES_BOLD_16);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("House-79, Block-D,", TIMES_ROMAN_10);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Int'l Airport Road, Banani, Dhaka", TIMES_ROMAN_10);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

            pdfPTable = new PdfPTable(1);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setTotalWidth(new float[]{100f});

            phrase = new Phrase("Leave Balance Report", TIMES_BOLD_14);
            pdfPCell = new PdfPCell(new Paragraph(phrase));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);

            pdfPTable = new PdfPTable(1);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setTotalWidth(new float[]{100f});

            phrase = new Phrase("Year: ", TIMES_BOLD_10);
            phrase.add(new Phrase(text, TIMES_ROMAN_10));
            pdfPCell = new PdfPCell(new Paragraph(phrase));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);
        }
    }


    void oneLineBreak(Document document) throws DocumentException {
        Paragraph p = new Paragraph();
        for (int i = 0; i < 1; i++) {
            p.add(new Paragraph(" "));
        }
        document.add(p);
    }

    void twoLineBreak(Document document) throws DocumentException {
        Paragraph p = new Paragraph();
        for (int i = 0; i < 2; i++) {
            p.add(new Paragraph(" "));
        }
        document.add(p);
    }
}
