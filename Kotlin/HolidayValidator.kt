package holidaychecker

import java.time.LocalDate

/**
 * HolidayValidator
 *
 * This base class is used to determine if a given date is a holiday.
 * It supports fixed holidays, dynamic Islamic holidays, weekends, and custom holidays.
 */
open class HolidayValidator(private val includeSaturday: Boolean) {
    companion object {
        val fixedHolidays = listOf(
            "01-01", // Yeni Yıl
            "04-23", // Ulusal Egemenlik ve Çocuk Bayramı
            "05-01", // İşçi Bayramı
            "05-19", // Atatürk'ü Anma, Gençlik ve Spor Bayramı
            "07-20", // Barış ve Özgürlük Bayramı
            "08-30", // Zafer Bayramı
            "10-29"  // Cumhuriyet Bayramı
        )

        val baseDates = listOf(
            Holiday("ramazanBayrami", LocalDate.of(2024, 4, 10), 3),
            Holiday("kurbanBayrami", LocalDate.of(2024, 6, 28), 4)
        )

        const val islamicYearDays = 354.36667
        const val baseYear = 2024
    }

    private val customHolidays = mutableListOf<CustomHoliday>()

    /**
     * Check if the given date is a holiday
     *
     * @param date The date to check
     * @return True if the date is a holiday, false otherwise
     */
    fun isHoliday(date: LocalDate): Boolean {
        return isWeekend(date) || isFixedHoliday(date) || isIslamicHoliday(date) || isCustomHoliday(date)
    }

    /**
     * Add a custom holiday
     *
     * @param date The date of the custom holiday
     * @param recurring Whether the holiday recurs every year
     * @param isIslamic Whether the holiday should be considered as an Islamic holiday
     */
    fun addCustomHoliday(date: LocalDate, recurring: Boolean = false, isIslamic: Boolean = false) {
        customHolidays.add(CustomHoliday(date, recurring, isIslamic))
    }

    /**
     * Check if the given date is a weekend
     *
     * @param date The date to check
     * @return True if the date is a weekend, false otherwise
     */
    private fun isWeekend(date: LocalDate): Boolean {
        val dayOfWeek = date.dayOfWeek.value
        return dayOfWeek == 7 || (includeSaturday && dayOfWeek == 6)
    }

    /**
     * Check if the given date is a fixed holiday
     *
     * @param date The date to check
     * @return True if the date is a fixed holiday, false otherwise
     */
    private fun isFixedHoliday(date: LocalDate): Boolean {
        val monthDay = date.toString().substring(5)
        return fixedHolidays.contains(monthDay)
    }

    /**
     * Check if the given date is an Islamic holiday
     *
     * @param date The date to check
     * @return True if the date is an Islamic holiday, false otherwise
     */
    private fun isIslamicHoliday(date: LocalDate): Boolean {
        val islamicHolidays = calculateIslamicHolidays(date.year)
        return islamicHolidays.contains(date)
    }

    /**
     * Check if the given date is a custom holiday
     *
     * @param date The date to check
     * @return True if the date is a custom holiday, false otherwise
     */
    private fun isCustomHoliday(date: LocalDate): Boolean {
        return customHolidays.any { holiday ->
            if (holiday.recurring) {
                date.toString().substring(5) == holiday.date.toString().substring(5)
            } else {
                date == holiday.date
            }
        }
    }

    /**
     * Calculate Islamic holidays for a given year
     *
     * @param year The year to calculate the holidays for
     * @return List of Islamic holiday dates
     */
    private fun calculateIslamicHolidays(year: Int): List<LocalDate> {
        val yearDifference = year - baseYear
        return baseDates.flatMap { baseDate ->
            generateHolidayDates(calculateIslamicDate(baseDate.date, yearDifference), baseDate.days)
        }
    }

    /**
     * Calculate the date of an Islamic holiday
     *
     * @param baseDate The base date of the holiday
     * @param yearDifference The difference in years from the base year
     * @return The calculated date of the holiday
     */
    private fun calculateIslamicDate(baseDate: LocalDate, yearDifference: Int): LocalDate {
        val daysToAdd = (yearDifference * islamicYearDays).toInt()
        return baseDate.plusDays(daysToAdd.toLong())
    }

    /**
     * Generate a list of holiday dates starting from a given date
     *
     * @param startDate The start date of the holiday
     * @param days The number of days the holiday lasts
     * @return List of holiday dates
     */
    private fun generateHolidayDates(startDate: LocalDate, days: Int): List<LocalDate> {
        return (0 until days).map { startDate.plusDays(it.toLong()) }
    }

    /**
     * Holiday class representing a holiday with a name, date, and duration in days
     */
    data class Holiday(val name: String, val date: LocalDate, val days: Int)

    /**
     * CustomHoliday class representing a user-defined holiday with additional options for recurrence and Islamic holiday check
     */
    data class CustomHoliday(val date: LocalDate, val recurring: Boolean, val isIslamic: Boolean)
}
