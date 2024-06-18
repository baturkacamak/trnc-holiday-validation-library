#ifndef TURKEYHOLIDAYVALIDATOR_H
#define TURKEYHOLIDAYVALIDATOR_H

#include "HolidayValidator.h"

/**
 * TurkeyHolidayValidator
 *
 * This class is used to determine if a given date is a holiday in Turkey.
 */
class TurkeyHolidayValidator : public HolidayValidator {
public:
    TurkeyHolidayValidator(bool includeSaturday = false);
};

#endif // TURKEYHOLIDAYVALIDATOR_H
