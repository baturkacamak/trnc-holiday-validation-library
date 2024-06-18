#include "HolidayValidator.h"
#include <algorithm>
#include <cmath>

const std::vector<std::string> HolidayValidator::fixedHolidays = {
    "01-01", // Yeni Yıl
    "04-23", // Ulusal Egemenlik ve Çocuk Bayramı
    "05-01", // İşçi Bayramı
    "05-19", // Atatürk'ü Anma, Gençlik ve Spor Bayramı
    "07-20", // Barış ve Özgürlük Bayramı
    "08-30", // Zafer Bayramı
    "10-29", // Cumhuriyet Bayramı
};

const std::vector<std::tuple<std::string, std::tm, int>> HolidayValidator::baseDates = {
    {"ramazanBayrami", {0, 0, 0, 10, 3, 124}, 3}, // 2024-04-10
    {"kurbanBayrami", {0, 0, 0, 28, 5, 124}, 4}   // 2024-06-28
};

HolidayValidator::HolidayValidator(bool includeSaturday) : includeSaturday(includeSaturday) {}

bool HolidayValidator::isHoliday(const std::tm &date) {
    return isWeekend(date) || isFixedHoliday(date) || isIslamicHoliday(date) || isCustomHoliday(date);
}

void HolidayValidator::addCustomHoliday(const std::tm &date, bool recurring, bool isIslamic) {
    customHolidays.emplace(date, recurring, isIslamic);
}

bool HolidayValidator::isWeekend(const std::tm &date) {
    int dayOfWeek = date.tm_wday;
    return dayOfWeek == 0 || (includeSaturday && dayOfWeek == 6);
}

bool HolidayValidator::isFixedHoliday(const std::tm &date) {
    char buffer[6];
    std::strftime(buffer, sizeof(buffer), "%m-%d", &date);
    return std::find(fixedHolidays.begin(), fixedHolidays.end(), buffer) != fixedHolidays.end();
}

bool HolidayValidator::isIslamicHoliday(const std::tm &date) {
    auto islamicHolidays = calculateIslamicHolidays(date.tm_year + 1900);
    for (const auto &holidayDates : islamicHolidays) {
        for (const auto &holidayDate : holidayDates) {
            if (std::difftime(std::mktime(&date), std::mktime(&holidayDate)) == 0) {
                return true;
            }
        }
    }
    return false;
}

bool HolidayValidator::isCustomHoliday(const std::tm &date) {
    for (const auto &holiday : customHolidays) {
        const auto &[holidayDate, recurring, isIslamic] = holiday;
        if (recurring) {
            char buffer1[6], buffer2[6];
            std::strftime(buffer1, sizeof(buffer1), "%m-%d", &date);
            std::strftime(buffer2, sizeof(buffer2), "%m-%d", &holidayDate);
            if (std::strcmp(buffer1, buffer2) == 0) {
                return true;
            }
        } else {
            if (std::difftime(std::mktime(&date), std::mktime(&holidayDate)) == 0) {
                return true;
            }
        }

        if (isIslamic && isIslamicHoliday(holidayDate)) {
            return true;
        }
    }
    return false;
}

std::vector<std::tm> HolidayValidator::calculateIslamicHolidays(int year) {
    int yearDifference = year - 2024;
    std::vector<std::tm> holidays;

    for (const auto &baseDate : baseDates) {
        const auto &[name, date, days] = baseDate;
        auto startDate = calculateIslamicDate(date, yearDifference);
        auto holidayDates = generateHolidayDates(startDate, days);
        holidays.insert(holidays.end(), holidayDates.begin(), holidayDates.end());
    }

    return holidays;
}

std::tm HolidayValidator::calculateIslamicDate(const std::tm &baseDate, int yearDifference) {
    std::tm date = baseDate;
    date.tm_mday += static_cast<int>(std::round(yearDifference * 354.36667));
    std::mktime(&date);
    return date;
}

std::vector<std::tm> HolidayValidator::generateHolidayDates(const std::tm &startDate, int days) {
    std::vector<std::tm> dates;
    for (int i = 0; i < days; ++i) {
        std::tm date = startDate;
        date.tm_mday += i;
        std::mktime(&date);
        dates.push_back(date);
    }
    return dates;
}
