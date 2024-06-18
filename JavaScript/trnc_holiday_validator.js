const HolidayValidator = require('../HolidayValidator');

/**
 * TRNCHolidayValidator
 *
 * This class is used to determine if a given date is a holiday in TRNC.
 */
class TRNCHolidayValidator extends HolidayValidator {
    static FIXED_HOLIDAYS = [
        ...HolidayValidator.FIXED_HOLIDAYS,
        '08-01', // TMT Günü
        '11-15', // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
    ];

    static BASE_DATES = [
        ...HolidayValidator.BASE_DATES,
        { name: 'mevlidKandili', date: new Date('2024-09-15'), days: 1 }
    ];
}

module.exports = TRNCHolidayValidator;
