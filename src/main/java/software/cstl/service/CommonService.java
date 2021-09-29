package software.cstl.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class CommonService {

    private final Logger log = LoggerFactory.getLogger(CommonService.class);

    public CommonService() {}

    public String[] getStringArrayBySeparatingStringContentUsingSeparator(String content, String separator) {
        log.debug("Request to separate a string content : {} using given separator : {}", content, separator);
        return content.split(separator);
    }

    public String getByteArrayToString(byte[] bytes) {
        log.debug("Request to convert byte array to string : {} ", bytes);
        return new String(bytes);
    }

    public LocalDate getFirstDayOfTheYear(int year) {
        log.debug("Request to get the first day of the year : {} ", year);
        return getFirstDayOfTheMonth(year, Month.JANUARY);
    }

    public LocalDate getLastDayOfTheYear(int year) {
        log.debug("Request to get the last day of the year : {} ", year);
        return getLastDayOfTheMonth(year, Month.DECEMBER);
    }

    public LocalDate getFirstDayOfTheMonth(int year, Month month) {
        log.debug("Request to get the first day of the month : {} {}", year, month);
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.atDay(1);
    }

    public LocalDate getFirstDayOfTheMonth(int year, int monthId) {
        log.debug("Request to get the first day of the month : {} {}", year, monthId);
        Month month = Month.of(monthId);
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.atDay(1);
    }

    public LocalDate getLastDayOfTheMonth(int year, Month month) {
        log.debug("Request to get the last day of the month : {} {}", year, month);
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.atEndOfMonth();
    }

    public LocalDate getLastDayOfTheMonth(int year, int monthId) {
        log.debug("Request to get the last day of the month : {} {}", year, monthId);
        Month month = Month.of(monthId);
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.atEndOfMonth();
    }

    public int getLengthOfYear(int year) {
        log.debug("Request to get the total number of days in a year : {}", year);
        YearMonth yearMonth = YearMonth.of(year, Month.FEBRUARY);
        return yearMonth.lengthOfYear();
    }
    public static String parseTime(ZonedDateTime dateTime) {
        ZonedDateTime time = dateTime.withZoneSameInstant(ZoneId.of("Asia/Dhaka"));
        LocalDateTime localDateTime = time.toLocalDateTime();
        String hour = localDateTime.getHour() < 10 ? "0" + localDateTime.getHour() : localDateTime.getHour() + "";
        String minute = localDateTime.getMinute() < 10 ? "0" + localDateTime.getMinute() : localDateTime.getMinute() + "";
        String second = localDateTime.getSecond() < 10 ? "0" + localDateTime.getSecond() : localDateTime.getSecond() + "";
        return hour + ":" + minute + ":" + second;
    }

    public static String parseDate(ZonedDateTime dateTime) {
        ZonedDateTime date = dateTime.withZoneSameInstant(ZoneId.of("Asia/Dhaka"));
        LocalDateTime localDateTime = date.toLocalDateTime();
        String day = localDateTime.getDayOfMonth() < 10 ? "0" + localDateTime.getDayOfMonth() : localDateTime.getDayOfMonth() + "";
        String month = localDateTime.getMonth().name();
        String year = String.valueOf(localDateTime.getYear());
        return  month + " " + day + ", " + year;
    }
}
