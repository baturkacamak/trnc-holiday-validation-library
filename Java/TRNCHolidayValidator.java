package HolidayChecker.TRNC;

import HolidayChecker.HolidayValidator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * TRNCHolidayValidator
 *
 * This class is used to determine if a given date is a holiday in TRNC.
 */
public class TRNCHolidayValidator extends HolidayValidator {
    private static final List<String> TRNC_FIXED_HOLIDAYS = List.of(
        "08-01", // TMT Günü
        "11-15"  // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
    );

    private static final List<Holiday> TRNC_BASE_DATES = List.of(
        new Holiday("mevlidKandili", LocalDate.of(2024, 9, 15), 1)
    );

    /**
     * Constructor for TRNCHolidayValidator
     *
     * @param includeSaturday Boolean flag indicating whether to include Saturdays as holidays
     */
    public TRNCHolidayValidator(boolean includeSaturday) {
        super(includeSaturday);
    }

    /**
     * Check if the given date is a fixed holiday in TRNC
     *
     * @param date The date to check
     * @return True if the date is a fixed holiday in TRNC, false otherwise
     */
    @Override
    protected boolean isFixedHoliday(LocalDate date) {
        return super.isFixedHoliday(date) || TRNC_FIXED_HOLIDAYS.contains(date.format(DateTimeFormatter.ofPattern("MM-dd")));
    }

    /**
     * Get the base dates for Islamic holidays in TRNC
     *
     * @return A list of base dates for Islamic holidays in TRNC
     */
    @Override
    protected List<Holiday> getBaseDates() {
        List<Holiday> allBaseDates = new ArrayList<>(super.getBaseDates());
        allBaseDates.addAll(TRNC_BASE_DATES);
        return allBaseDates;
    }
}
