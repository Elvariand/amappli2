package isika.p3.amappli.entities.tenancy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import jakarta.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class PickUpSchedule {

    private DayOfWeek dayOfWeek;
    private LocalTime startHour;
    private LocalTime endHour;

    public LocalDateTime[] getPreviousPickUpLocalDateTimes() {
        LocalDate now = LocalDate.now();
        LocalDate previousDate = now.with(TemporalAdjusters.previousOrSame(dayOfWeek));
        if (previousDate.equals(now) && LocalTime.now().isBefore(startHour)) {
            // Move back one week if today is the day, but current time is before the start time
            previousDate = previousDate.minusDays(7);
        }
        LocalDateTime start = LocalDateTime.of(previousDate, startHour);
        LocalDateTime end = LocalDateTime.of(previousDate, endHour);
        return new LocalDateTime[] {start, end};
    }

    public LocalDateTime[] getNextPickUpLocalDateTimes() {
        LocalDate now = LocalDate.now();
        LocalDate nextDate = now.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        if (nextDate.equals(now) && LocalTime.now().isAfter(startHour)) {
            // Move forward one week if today is the day, but current time is after the start time
            nextDate = nextDate.plusDays(7);
        }
        LocalDateTime start = LocalDateTime.of(nextDate, startHour);
        LocalDateTime end = LocalDateTime.of(nextDate, endHour);
        return new LocalDateTime[] {start, end};
    }
    
    public String getLocalizedDayOfWeek() {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.FRENCH);
    }
}