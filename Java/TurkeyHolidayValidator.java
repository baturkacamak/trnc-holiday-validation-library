package HolidayChecker.Turkey;

import HolidayChecker.HolidayValidator;

/**
 * TurkeyHolidayValidator
 *
 * This class is used to determine if a given date is a holiday in Turkey.
 */
public class TurkeyHolidayValidator extends HolidayValidator {
    public TurkeyHolidayValidator(boolean includeSaturday) {
        super(includeSaturday);
    }
}
