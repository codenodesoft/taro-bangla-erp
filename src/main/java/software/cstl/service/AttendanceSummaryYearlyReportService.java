package software.cstl.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import io.github.jhipster.service.filter.LocalDateFilter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.AttendanceSummary;
import software.cstl.domain.Department;
import software.cstl.domain.Designation;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.AttendanceStatus;
import software.cstl.domain.enumeration.AttendanceType;
import software.cstl.service.dto.AttendanceSummaryCriteria;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendanceSummaryYearlyReportService {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryYearlyReportService.class);

    private final AttendanceSummaryQueryService attendanceSummaryQueryService;

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

    public AttendanceSummaryYearlyReportService(AttendanceSummaryQueryService attendanceSummaryQueryService) {
        this.attendanceSummaryQueryService = attendanceSummaryQueryService;
    }

    public ByteArrayInputStream download(AttendanceSummaryCriteria criteria, int yearId) throws DocumentException {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(25, 25, 25, 25);
        document.addTitle("Attendance Summary Report");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new HeaderAndFooter());
        Heading heading = new Heading();
        writer.setPageEvent(heading);
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;
        Phrase phrase = null;

        for (Month month: Month.values()) {
            heading.setText(month.name() + ", " + yearId);
            CommonService commonService = new CommonService();
            LocalDate firstDayOfMonth = commonService.getFirstDayOfTheMonth(yearId, month);
            LocalDate lastDayOfMonth = commonService.getLastDayOfTheMonth(yearId, month);
            LocalDateFilter localDateFilter = new LocalDateFilter();
            localDateFilter.setGreaterThanOrEqual(firstDayOfMonth);
            localDateFilter.setLessThanOrEqual(lastDayOfMonth);
            criteria.setAttendanceDate(localDateFilter);
            List<AttendanceSummary> summaries = attendanceSummaryQueryService.findByCriteria(criteria);

            if (summaries.size() > 0) {
                Map<Department, List<AttendanceSummary>> attendanceSummariesGroupByDepartment = summaries.stream()
                    .collect(Collectors.groupingBy(AttendanceSummary::getDepartment));

                int attendanceSummariesGroupByDepartmentSize = attendanceSummariesGroupByDepartment.size();
                int counter1 = 0;
                for (Map.Entry<Department, List<AttendanceSummary>> summariesGroupByDepartment : attendanceSummariesGroupByDepartment.entrySet()) {
                    Map<Designation, List<AttendanceSummary>> attendanceSummariesGroupByDesignation = summariesGroupByDepartment.getValue().stream()
                        .collect(Collectors.groupingBy(AttendanceSummary::getDesignation));
                    counter1++;
                    int attendanceSummariesGroupByDesignationSize = attendanceSummariesGroupByDepartment.size();
                    int counter2 = 0;
                    for (Map.Entry<Designation, List<AttendanceSummary>> summariesGroupByDesignation : attendanceSummariesGroupByDesignation.entrySet()) {
                        Map<Employee, List<AttendanceSummary>> attendanceSummariesGroupByEmployee = summariesGroupByDesignation.getValue().stream()
                            .collect(Collectors.groupingBy(AttendanceSummary::getEmployee));
                        counter2++;
                        int attendanceSummariesGroupByEmployeeSize = attendanceSummariesGroupByDepartment.size();
                        int counter3 = 0;
                        for (Map.Entry<Employee, List<AttendanceSummary>> summariesGroupByEmployee : attendanceSummariesGroupByEmployee.entrySet()) {
                            counter3++;
                            contentHeading(document, summariesGroupByEmployee);
                            oneLineBreak(document);
                            content(document, summariesGroupByEmployee);
                            if (counter3 < attendanceSummariesGroupByEmployeeSize || counter2 < attendanceSummariesGroupByDesignationSize || counter1 < attendanceSummariesGroupByDepartmentSize) {
                                document.newPage();
                            }
                        }
                    }
                }
            }
            else {
                paragraph = new Paragraph("No record(s) found.", TIMES_BOLD_12);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                document.add(paragraph);
                document.newPage();
            }
        }
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void content(Document document, Map.Entry<Employee, List<AttendanceSummary>> summariesGroupByEmployee) throws DocumentException {
        PdfPTable pdfPTable;
        PdfPCell pdfPCell;
        pdfPTable = new PdfPTable(7);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setTotalWidth(new float[]{10f, 15f, 15f, 15f, 15f, 15f, 15f});

        pdfPCell = new PdfPCell(new Paragraph("SL NO", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("DATE", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("IN TIME", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("OUT TIME", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("OT HR", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("TYPE", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("STATUS", TIMES_BOLD_10));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(pdfPCell);

        int serial = 0;
        int totalDays = 0;
        int totalPresent = 0;
        int totalAbsent = 0;
        int totalWeekend = 0;
        int totalHoliday = 0;
        int totalLeave = 0;
        Duration totalOvertime = Duration.ZERO;
        for (AttendanceSummary summary : summariesGroupByEmployee.getValue()) {
            serial++;
            pdfPCell = new PdfPCell(new Paragraph(String.valueOf(serial), TIMES_ROMAN_10));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(summary.getAttendanceDate() == null ? "" : summary.getAttendanceDate().toString(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(summary.getInTime() == null ? "" : CommonService.parseTime(summary.getInTime()), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(summary.getOutTime() == null ? "" : CommonService.parseTime(summary.getOutTime()), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(summary.getOvertime() == null ? "" : summary.getOvertime().toString(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(summary.getAttendanceType() == null ? "" : summary.getAttendanceType().name(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(summary.getAttendanceStatus() == null ? "" : summary.getAttendanceStatus().equals(AttendanceStatus.NOT_APPLICABLE) ? "N/A" : summary.getAttendanceStatus().name(), TIMES_ROMAN_10));
            pdfPTable.addCell(pdfPCell);

            totalDays += 1;
            totalPresent += summary.getAttendanceStatus() == null ? 0 : summary.getAttendanceStatus().equals(AttendanceStatus.PRESENT) ? 1 : 0;
            totalAbsent += summary.getAttendanceStatus() == null ? 0 : summary.getAttendanceStatus().equals(AttendanceStatus.ABSENT) ? 1 : 0;
            totalWeekend += summary.getAttendanceType() == null ? 0 : summary.getAttendanceType().equals(AttendanceType.WEEKEND) ? 1 : 0;
            totalHoliday += summary.getAttendanceType() == null ? 0 : summary.getAttendanceType().equals(AttendanceType.HOLIDAY) ? 1 : 0;
            totalLeave += summary.getAttendanceType() == null ? 0 : summary.getAttendanceType().equals(AttendanceType.LEAVE) ? 1 : 0;
            totalOvertime = totalOvertime.plus(summary.getOvertime() == null ? Duration.ZERO : summary.getOvertime());
        }
        document.add(pdfPTable);
        contentFooter(document, totalDays, totalPresent, totalAbsent, totalWeekend, totalHoliday, totalLeave, totalOvertime);
    }

    private void contentFooter(Document document, int totalDays, int totalPresent, int totalAbsent, int totalWeekend, int totalHoliday, int totalLeave, Duration totalOvertime) throws DocumentException {
        PdfPCell pdfPCell;
        PdfPTable pdfPTable;
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
        pdfPTable.setTotalWidth(new float[]{25f, 2f, 23f, 25f, 2f, 23f});

        pdfPCell = new PdfPCell(new Paragraph("Total Days", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(String.valueOf(totalDays), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Weekend", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(String.valueOf(totalWeekend), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Present", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(String.valueOf(totalPresent), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Holiday", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(String.valueOf(totalHoliday), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Absent", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(String.valueOf(totalAbsent), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Leave", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(String.valueOf(totalLeave), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Total Overtime", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(totalOvertime.toString(), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("", TIMES_BOLD_10));
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

    private void contentHeading(Document document, Map.Entry<Employee, List<AttendanceSummary>> summariesGroupByEmployee) throws DocumentException {
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

        pdfPCell = new PdfPCell(new Paragraph(summariesGroupByEmployee.getKey().getEmpId(), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Name", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(summariesGroupByEmployee.getKey().getName(), TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Business Unit", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(summariesGroupByEmployee.getKey().getDepartment() == null ? "" : summariesGroupByEmployee.getKey().getDepartment().getName(), TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Job Title", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(summariesGroupByEmployee.getKey().getDesignation() == null ? "" : summariesGroupByEmployee.getKey().getDesignation().getName(), TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Line", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(summariesGroupByEmployee.getKey().getLine() == null ? "" : summariesGroupByEmployee.getKey().getLine().getName(), TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph("Join Date", TIMES_BOLD_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(":", TIMES_ROMAN_10));
        pdfPCell.setBorder(Rectangle.NO_BORDER);
        pdfPTable.addCell(pdfPCell);

        pdfPCell = new PdfPCell(new Paragraph(summariesGroupByEmployee.getKey().getJoiningDate() == null ? "" : summariesGroupByEmployee.getKey().getJoiningDate().toString(), TIMES_ROMAN_10));
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

        Heading() {}

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

            phrase = new Phrase("Attendance Yearly Report", TIMES_BOLD_14);
            pdfPCell = new PdfPCell(new Paragraph(phrase));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, new Phrase("Report Date: " + text, TIMES_BOLD_10), 565, 750, 0);
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
