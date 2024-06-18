#ifndef HOLIDAYVALIDATOR_H
#define HOLIDAYVALIDATOR_H

#include <string>
#include <vector>
#include <set>
#include <chrono>
#include <ctime>

/**
 * HolidayValidator
 *
 * This base class is used to determine if a given date is a holiday.
 * It supports fixed holidays, dynamic Islamic holidays, weekends, and custom holidays.
 */
class HolidayValidator {
protected:
    static const std::vector<std::string> fixedHolidays;
    static const std::vector<std::tuple<std::string, std::tm, int>> baseDates;
    bool includeSaturday;
    std::set<std::tuple<std::tm, bool, bool>> customHolidays;

public:
    HolidayValidator(bool includeSaturday = false);
    bool isHoliday(const std::tm &date);
    void addCustomHoliday(const std::tm &date, bool recurring = false, bool isIslamic = false);

protected:
    bool isWeekend(const std::tm &date);
    bool isFixedHoliday(const std::tm &date);
    bool isIslamicHoliday(const std::tm &date);
    bool isCustomHoliday(const std::tm &date);
    std::vector<std::tm> calculateIslamicHolidays(int year);
    std::tm calculateIslamicDate(const std::tm &baseDate, int yearDifference);
    std::vector<std::tm> generateHolidayDates(const std::tm &startDate, int days);
};

#endif // HOLIDAYVALIDATOR_H
