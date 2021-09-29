package software.cstl.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import software.cstl.domain.enumeration.WeekDay;
import software.cstl.domain.enumeration.WeekendStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link software.cstl.domain.Weekend} entity. This class is used
 * in {@link software.cstl.web.rest.WeekendResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /weekends?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WeekendCriteria implements Serializable, Criteria {
    /**
     * Class for filtering WeekDay
     */
    public static class WeekDayFilter extends Filter<WeekDay> {

        public WeekDayFilter() {
        }

        public WeekDayFilter(WeekDayFilter filter) {
            super(filter);
        }

        @Override
        public WeekDayFilter copy() {
            return new WeekDayFilter(this);
        }

    }
    /**
     * Class for filtering WeekendStatus
     */
    public static class WeekendStatusFilter extends Filter<WeekendStatus> {

        public WeekendStatusFilter() {
        }

        public WeekendStatusFilter(WeekendStatusFilter filter) {
            super(filter);
        }

        @Override
        public WeekendStatusFilter copy() {
            return new WeekendStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private WeekDayFilter day;

    private WeekendStatusFilter status;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private StringFilter remarks;

    public WeekendCriteria() {
    }

    public WeekendCriteria(WeekendCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.day = other.day == null ? null : other.day.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
    }

    @Override
    public WeekendCriteria copy() {
        return new WeekendCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public WeekDayFilter getDay() {
        return day;
    }

    public void setDay(WeekDayFilter day) {
        this.day = day;
    }

    public WeekendStatusFilter getStatus() {
        return status;
    }

    public void setStatus(WeekendStatusFilter status) {
        this.status = status;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WeekendCriteria that = (WeekendCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(day, that.day) &&
            Objects.equals(status, that.status) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        day,
        status,
        startDate,
        endDate,
        remarks
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WeekendCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (day != null ? "day=" + day + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
            "}";
    }

}
