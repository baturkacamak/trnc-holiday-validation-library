package HolidayChecker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * HolidayValidator
 *
 * This base class is used to determine if a given date is a holiday.
 * It supports fixed holidays, dynamic Islamic holidays, weekends, and custom holidays.
 */
public abstract class HolidayValidator {
    protected static final List<String> FIXED_HOLIDAYS = List.of(
        "01-01", // Yeni Yıl
        "04-23", // Ulusal Egemenlik ve Çocuk Bayramı
        "05-01", // İşçi Bayramı
        "05-19", // Atatürk'ü Anma, Gençlik ve Spor Bayramı
        "07-20", // Barış ve Özgürlük Bayramı
        "08-30", // Zafer Bayramı
        "10-29"  // Cumhuriyet Bayramı
    );

    protected static final List<Holiday> BASE_DATES = List.of(
        new Holiday("ramazanBayrami", LocalDate.of(2024, 4, 10), 3),
        new Holiday("kurbanBayrami", LocalDate.of(2024, 6, 28), 4)
    );

    private static final double ISLAMIC_YEAR_DAYS = 354.36667;
    private static final int BASE_YEAR = 2024;

    protected final boolean includeSaturday;
    protected final Set<CustomHoliday> customHolidays = new HashSet<>();

    /**
     * Constructor for the HolidayValidator class
     *
     * @param includeSaturday Boolean flag indicating whether to include Saturdays as holidays
     */
    public HolidayValidator(boolean includeSaturday) {
        this.includeSaturday = includeSaturday;
    }

    /**
     * Check if the given date is a holiday
     *
     * @param date The date to check
     * @return True if the date is a holiday, false otherwise
     */
    public boolean isHoliday(LocalDate date) {
        return isWeekend(date) || isFixedHoliday(date) || isIslamicHoliday(date) || isCustomHoliday(date);
    }

    /**
     * Add a custom holiday
     *
     * @param date The date of the custom holiday
     * @param recurring Whether the holiday recurs every year
     * @param isIslamic Whether the holiday should be considered as an Islamic holiday
     */
    public void addCustomHoliday(LocalDate date, boolean recurring, boolean isIslamic) {
        customHolidays.add(new CustomHoliday(date, recurring, isIslamic));
    }

    /**
     * Check if the given date is a weekend
     *
     * @param date The date to check
     * @return True if the date is a weekend, false otherwise
     */
    private boolean isWeekend(LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek == 7 || (includeSaturday && dayOfWeek == 6);
    }

    /**
     * Check if the given date is a fixed holiday
     *
     * @param date The date to check
     * @return True if the date is a fixed holiday, false otherwise
     */
    protected boolean isFixedHoliday(LocalDate date) {
        String monthDay = date.format(DateTimeFormatter.ofPattern("MM-dd"));
        return FIXED_HOLIDAYS.contains(monthDay);
    }

    /**
     * Check if the given date is an Islamic holiday
     *
     * @param date The date to check
     * @return True if the date is an Islamic holiday, false otherwise
     */
    private boolean isIslamicHoliday(LocalDate date) {
        List<LocalDate> islamicHolidays = calculateIslamicHolidays(date.getYear());
        return islamicHolidays.contains(date);
    }

    /**
     * Check if the given date is a custom holiday
     *
     * @param date The date to check
     * @return True if the date is a custom holiday, false otherwise
     */
    private boolean isCustomHoliday(LocalDate date) {
        for (CustomHoliday holiday : customHolidays) {
            if (holiday.isRecurring()) {
                if (date.format(DateTimeFormatter.ofPattern("MM-dd")).equals(holiday.getDate().format(DateTimeFormatter.ofPattern("MM-dd")))) {
                    return true;
                }
            } else {
                if (date.equals(holiday.getDate())) {
                    return true;
                }
            }

            if (holiday.isIslamic()) {
                if (isIslamicHoliday(holiday.getDate())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculate Islamic holidays for a given year
     *
     * @param year The year to calculate the holidays for
     * @return A list of Islamic holidays
     */
    private List<LocalDate> calculateIslamicHolidays(int year) {
        int yearDifference = year - BASE_YEAR;

        List<LocalDate> holidays = new ArrayList<>();

        for (Holiday holiday : BASE_DATES) {
            LocalDate startDate = calculateIslamicDate(holiday.getDate(), yearDifference);
            holidays.addAll(generateHolidayDates(startDate, holiday.getDays()));
        }

        return holidays;
    }

    /**
     * Calculate the date of an Islamic holiday
     *
     * @param baseDate The base date of the holiday
     * @param yearDifference The difference in years from the base year
     * @return The calculated date of the holiday
     */
    private LocalDate calculateIslamicDate(LocalDate baseDate, int yearDifference) {
        long daysToAdd = Math.round(yearDifference * ISLAMIC_YEAR_DAYS);
        return baseDate.plus(daysToAdd, ChronoUnit.DAYS);
    }

    /**
     * Generate a list of holiday dates
     *
     * @param startDate The start date of the holiday
     * @param days The number of days the holiday lasts
     * @return A list of holiday dates
     */
    private List<LocalDate> generateHolidayDates(LocalDate startDate, int days) {
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            dates.add(startDate.plusDays(i));
        }
        return dates;
    }

    /**
     * Holiday class representing a holiday with a name, date, and duration
     */
    protected static class Holiday {
        private final String name;
        private final LocalDate date;
        private final int days;

        /**
         * Constructor for the Holiday class
         *
         * @param name The name of the holiday
         * @param date The date of the holiday
         * @param days The number of days the holiday lasts
         */
        public Holiday(String name, LocalDate date, int days) {
            this.name = name;
            this.date = date;
            this.days = days;
        }

        /**
         * Get the name of the holiday
         *
         * @return The name of the holiday
         */
        public String getName() {
            return name;
        }

        /**
         * Get the date of the holiday
         *
         * @return The date of the holiday
         */
        public LocalDate getDate() {
            return date;
        }

        /**
         * Get the number of days the holiday lasts
         *
         * @return The number of days the holiday lasts
         */
        public int getDays() {
            return days;
        }
    }

    /**
     * CustomHoliday class representing a custom holiday with a date, recurrence flag, and Islamic flag
     */
    protected static class CustomHoliday {
        private final LocalDate date;
        private final boolean recurring;
        private final boolean isIslamic;

        /**
         * Constructor for the CustomHoliday class
         *
         * @param date The date of the custom holiday
         * @param recurring Whether the holiday recurs every year
         * @param isIslamic Whether the holiday is considered an Islamic holiday
         */
        public CustomHoliday(LocalDate date, boolean recurring, boolean isIslamic) {
            this.date = date;
            this.recurring = recurring;
            this.isIslamic = isIslamic;
        }

        /**
         * Get the date of the custom holiday
         *
         * @return The date of the custom holiday
         */
        public LocalDate getDate() {
            return date;
        }

        /**
         * Check if the custom holiday recurs every year
         *
         * @return True if the holiday recurs every year, false otherwise
         */
        public boolean isRecurring() {
            return recurring;
        }

        /**
         * Check if the custom holiday is considered an Islamic holiday
         *
         * @return True if the holiday is considered an Islamic holiday, false otherwise
         */
        public boolean isIslamic() {
            return isIslamic;
        }
    }
}
