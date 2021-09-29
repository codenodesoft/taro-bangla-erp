package software.cstl.service.reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.cstl.domain.Employee;
import software.cstl.domain.enumeration.EmployeeStatus;
import software.cstl.repository.extended.EmployeeExtRepository;
import software.cstl.utils.CodeNodeErpUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class EmployeeReportGenerator {
    private final EmployeeExtRepository employeeExtRepository;

    public ByteArrayInputStream createEmployeeReport(EmployeeStatus status) throws DocumentException{

        List<Employee> employeeList = employeeExtRepository.findAllByStatusOrderByDepartment_Id(status);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        PdfWriter.getInstance(document, out);
        document.open();

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Font semiHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10f);
        Font bodyFontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

        Paragraph paragraph = new Paragraph("Taro Bangla", headerFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        paragraph = new Paragraph("Employee Report of Status: "+status.name(), semiHeaderFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(10f);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell();
        paragraph = new Paragraph("ID", bodyFontBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        table.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("Name", bodyFontBold);
        cell.addElement(paragraph);
        table.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("Department", bodyFontBold);
        cell.addElement(paragraph);
        table.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("Designation", bodyFontBold);
        cell.addElement(paragraph);
        table.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("Joining Date", bodyFontBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        table.addCell(cell);

        cell = new PdfPCell();
        paragraph = new Paragraph("STATUS", bodyFontBold);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        table.addCell(cell);

        table.setHeaderRows(1);

        for(Employee employee: employeeList){
            cell = new PdfPCell();
            paragraph = new Paragraph(employee.getEmpId(), bodyFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            table.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(employee.getName(), bodyFont);
            cell.addElement(paragraph);
            table.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(employee.getDepartment().getName(), bodyFont);
            cell.addElement(paragraph);
            table.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(employee.getDesignation().getName(), bodyFont);
            cell.addElement(paragraph);
            table.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(employee.getJoiningDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), bodyFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            table.addCell(cell);

            cell = new PdfPCell();
            paragraph = new Paragraph(status.name(), bodyFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(paragraph);
            table.addCell(cell);
        }

        document.add(table);

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
