package holidaychecker

import java.time.LocalDate

/**
 * TRNCHolidayValidator
 *
 * This class is used to determine if a given date is a holiday in TRNC.
 * It extends the HolidayValidator class and includes TRNC-specific holidays.
 */
class TRNCHolidayValidator(includeSaturday: Boolean) : HolidayValidator(includeSaturday) {
    companion object {
        /**
         * List of fixed holidays specific to TRNC.
         * It includes common fixed holidays and TRNC-specific holidays.
         */
        val trncFixedHolidays = fixedHolidays + listOf(
            "08-01", // TMT Günü
            "11-15"  // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
        )

        /**
         * List of base dates for Islamic holidays including TRNC-specific holidays.
         */
        val trncBaseDates = baseDates + listOf(
            Holiday("mevlidKandili", LocalDate.of(2024, 9, 15), 1)
        )
    }

    /**
     * Check if the given date is a fixed holiday in TRNC.
     *
     * @param date The date to check
     * @return True if the date is a fixed holiday in TRNC, false otherwise
     */
    override fun isFixedHoliday(date: LocalDate): Boolean {
        val monthDay = date.toString().substring(5)
        return super.isFixedHoliday(date) || trncFixedHolidays.contains(monthDay)
    }
}
