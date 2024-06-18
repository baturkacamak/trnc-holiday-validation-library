import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TRNCHolidayValidator {
    private static final List<String> FIXED_HOLIDAYS = List.of(
        "01-01", // Yeni Yıl
        "04-23", // Ulusal Egemenlik ve Çocuk Bayramı
        "05-01", // İşçi Bayramı
        "05-19", // Atatürk'ü Anma, Gençlik ve Spor Bayramı
        "07-20", // Barış ve Özgürlük Bayramı
        "08-01", // TMT Günü
        "08-30", // Zafer Bayramı
        "10-29", // Cumhuriyet Bayramı
        "11-15"  // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
    );

    private static final List<Holiday> BASE_DATES = List.of(
        new Holiday("ramazanBayrami", LocalDate.of(2024, 4, 10), 3),
        new Holiday("kurbanBayrami", LocalDate.of(2024, 6, 28), 4),
        new Holiday("mevlidKandili", LocalDate.of(2024, 9, 15), 1)
    );

    private static final double ISLAMIC_YEAR_DAYS = 354.36667;
    private static final int BASE_YEAR = 2024;

    private final boolean includeSaturday;
    private final Set<CustomHoliday> customHolidays = new HashSet<>();

    public TRNCHolidayValidator(boolean includeSaturday) {
        this.includeSaturday = includeSaturday;
    }

    public boolean isHoliday(LocalDate date) {
        return isWeekend(date) || isFixedHoliday(date) || isIslamicHoliday(date) || isCustomHoliday(date);
    }

    public void addCustomHoliday(LocalDate date, boolean recurring, boolean isIslamic) {
        customHolidays.add(new CustomHoliday(date, recurring, isIslamic));
    }

    private boolean isWeekend(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek == 7 || (includeSaturday && dayOfWeek == 6);
    }

    private boolean isFixedHoliday(LocalDate date) {
        String monthDay = date.format(DateTimeFormatter.ofPattern("MM-dd"));
        return FIXED_HOLIDAYS.contains(monthDay);
    }

    private boolean isIslamicHoliday(LocalDate date) {
        List<LocalDate> islamicHolidays = calculateIslamicHolidays(date.getYear());
        return islamicHolidays.contains(date);
    }

    private boolean isCustomHoliday(LocalDate date) {
        for (CustomHoliday holiday : customHolidays) {
            if (holiday.recurring) {
                if (date.format(DateTimeFormatter.ofPattern("MM-dd")).equals(holiday.date.format(DateTimeFormatter.ofPattern("MM-dd")))) {
                    return true;
                }
            } else {
                if (date.equals(holiday.date)) {
                    return true;
                }
            }

            // Check if the custom holiday is an Islamic holiday
            if (holiday.isIslamic) {
                if (isIslamicHoliday(holiday.date)) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<LocalDate> calculateIslamicHolidays(int year) {
        int yearDifference = year - BASE_YEAR;

        List<LocalDate> holidays = new ArrayList<>();

        for (Holiday holiday : BASE_DATES) {
            LocalDate startDate = calculateIslamicDate(holiday.date, yearDifference);
            holidays.addAll(generateHolidayDates(startDate, holiday.days));
        }

        return holidays;
    }

    private LocalDate calculateIslamicDate(LocalDate baseDate, int yearDifference) {
        long daysToAdd = Math.round(yearDifference * ISLAMIC_YEAR_DAYS);
        return baseDate.plusDays(daysToAdd);
    }

    private List<LocalDate> generateHolidayDates(LocalDate startDate, int days) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            dates.add(startDate.plusDays(i));
        }
        return dates;
    }

    private static class Holiday {
        String name;
        LocalDate date;
        int days;

        Holiday(String name, LocalDate date, int days) {
            this.name = name;
            this.date = date;
            this.days = days;
        }
    }

    private static class CustomHoliday {
        LocalDate date;
        boolean recurring;
        boolean isIslamic;

        CustomHoliday(LocalDate date, boolean recurring, boolean isIslamic) {
            this.date = date;
            this.recurring = recurring;
            this.isIslamic = isIslamic;
        }
    }
}