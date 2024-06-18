#ifndef TRNCHOLIDAYVALIDATOR_H
#define TRNCHOLIDAYVALIDATOR_H

#include "HolidayValidator.h"

/**
 * TRNCHolidayValidator
 *
 * This class is used to determine if a given date is a holiday in TRNC.
 */
class TRNCHolidayValidator : public HolidayValidator {
private:
    static const std::vector<std::string> trncFixedHolidays;
    static const std::vector<std::tuple<std::string, std::tm, int>> trncBaseDates;

public:
    TRNCHolidayValidator(bool includeSaturday = false);

protected:
    bool isFixedHoliday(const std::tm &date) override;
    std::vector<std::tuple<std::string, std::tm, int>> getBaseDates() override;
};

#endif // TRNCHOLIDAYVALIDATOR_H
