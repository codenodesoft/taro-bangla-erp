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
public class AttendanceSummaryDailyReportService {

    private final Logger log = LoggerFactory.getLogger(AttendanceSummaryDailyReportService.class);

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

    public AttendanceSummaryDailyReportService(AttendanceSummaryQueryService attendanceSummaryQueryService) {
        this.attendanceSummaryQueryService = attendanceSummaryQueryService;
    }

    public ByteArrayInputStream download(AttendanceSummaryCriteria criteria) throws DocumentException {
        LocalDate localDate = criteria.getAttendanceDate().getEquals();
        AttendanceStatus status = criteria.getAttendanceStatus().getEquals();
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        document.setMargins(25, 25, 25, 25);
        document.addTitle("Attendance Summary Report");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        writer.setPageEvent(new HeaderAndFooter());
        writer.setPageEvent(new Heading(localDate.toString()));
        document.open();

        Chunk chunk = null;
        Paragraph paragraph = null;
        PdfPTable pdfPTable = null;
        PdfPCell pdfPCell = null;
        Phrase phrase = null;

        List<AttendanceSummary> summaries = attendanceSummaryQueryService.findByCriteria(criteria);

        if (summaries.size() > 0) {
            pdfPTable = new PdfPTable(9);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setTotalWidth(new float[]{7f, 11f, 21f, 10f, 9f, 9f, 11f, 11f, 11f});

            pdfPCell = new PdfPCell(new Paragraph("#", TIMES_BOLD_10));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("ID", TIMES_BOLD_10));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("NAME", TIMES_BOLD_10));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("DATE", TIMES_BOLD_10));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("IN", TIMES_BOLD_10));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph("OUT", TIMES_BOLD_10));
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
            int totalPresent = 0;
            int totalAbsent = 0;
            int totalNotApplicable = 0;
            for (AttendanceSummary summary : summaries) {
                serial++;
                pdfPCell = new PdfPCell(new Paragraph(String.valueOf(serial), TIMES_ROMAN_10));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(summary.getEmployee().getEmpId(), TIMES_ROMAN_10));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPTable.addCell(pdfPCell);

                pdfPCell = new PdfPCell(new Paragraph(summary.getEmployee().getName(), TIMES_ROMAN_10));
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

                totalPresent += summary.getAttendanceStatus() == null ? 0 : summary.getAttendanceStatus().equals(AttendanceStatus.PRESENT) ? 1 : 0;
                totalAbsent += summary.getAttendanceStatus() == null ? 0 : summary.getAttendanceStatus().equals(AttendanceStatus.ABSENT) ? 1 : 0;
                totalNotApplicable += summary.getAttendanceStatus() == null ? 0 : summary.getAttendanceStatus().equals(AttendanceStatus.NOT_APPLICABLE) ? 1 : 0;
            }
            document.add(pdfPTable);
            oneLineBreak(document);
            contentFooter(document, totalPresent, totalAbsent, totalNotApplicable, status);
        } else {
            paragraph = new Paragraph("No record(s) found.", TIMES_BOLD_12);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
        }
        document.close();
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private void contentFooter(Document document, int totalPresent, int totalAbsent, int totalNotApplicable, AttendanceStatus status) throws DocumentException {
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

        pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(50);
        pdfPTable.setTotalWidth(new float[]{50f, 10f, 40f,});

        if (status.equals(AttendanceStatus.PRESENT)) {
            pdfPCell = new PdfPCell(new Paragraph("Total Present", TIMES_BOLD_10));
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(String.valueOf(totalPresent), TIMES_BOLD_10));
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(pdfPCell);
        }

        if (status.equals(AttendanceStatus.ABSENT)) {
            pdfPCell = new PdfPCell(new Paragraph("Total Absent", TIMES_BOLD_10));
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(String.valueOf(totalAbsent), TIMES_BOLD_10));
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
        }

        if (status.equals(AttendanceStatus.NOT_APPLICABLE)) {
            pdfPCell = new PdfPCell(new Paragraph("Total N/A", TIMES_BOLD_10));
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(":", TIMES_BOLD_10));
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);

            pdfPCell = new PdfPCell(new Paragraph(String.valueOf(totalNotApplicable), TIMES_BOLD_10));
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
        }

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

            phrase = new Phrase("Attendance Daily Report", TIMES_BOLD_14);
            pdfPCell = new PdfPCell(new Paragraph(phrase));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);

            pdfPTable = new PdfPTable(1);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setTotalWidth(new float[]{100f});

            phrase = new Phrase("Report Date: ", TIMES_BOLD_10);
            phrase.add(new Phrase(text, TIMES_ROMAN_10));
            pdfPCell = new PdfPCell(new Paragraph(phrase));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pdfPCell.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(pdfPCell);
            document.add(pdfPTable);

            paragraph = new Paragraph();
            for (int i = 0; i < 1; i++) {
                paragraph.add(new Paragraph(" "));
            }
            document.add(paragraph);
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
