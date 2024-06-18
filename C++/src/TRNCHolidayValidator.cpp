#include "TRNCHolidayValidator.h"
#include <algorithm>

const std::vector<std::string> TRNCHolidayValidator::trncFixedHolidays = {
    "08-01", // TMT Günü
    "11-15"  // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
};

const std::vector<std::tuple<std::string, std::tm, int>> TRNCHolidayValidator::trncBaseDates = {
    {"mevlidKandili", {0, 0, 0, 15, 8, 124}, 1} // 2024-09-15
};

TRNCHolidayValidator::TRNCHolidayValidator(bool includeSaturday) : HolidayValidator(includeSaturday) {}

bool TRNCHolidayValidator::isFixedHoliday(const std::tm &date) {
    char buffer[6];
    std::strftime(buffer, sizeof(buffer), "%m-%d", &date);
    return std::find(trncFixedHolidays.begin(), trncFixedHolidays.end(), buffer) != trncFixedHolidays.end() ||
           HolidayValidator::isFixedHoliday(date);
}

std::vector<std::tuple<std::string, std::tm, int>> TRNCHolidayValidator::getBaseDates() {
    auto baseDates = HolidayValidator::baseDates;
    baseDates.insert(baseDates.end(), trncBaseDates.begin(), trncBaseDates.end());
    return baseDates;
}
