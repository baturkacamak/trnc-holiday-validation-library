const TRNCHolidayValidator = require('../trnc_holiday_validator');

describe('TRNCHolidayValidator', () => {
    let validator;

    beforeEach(() => {
        validator = new TRNCHolidayValidator();
    });

    test('should identify TRNC-specific fixed holidays', () => {
        const date = new Date('2024-08-01');
        expect(validator.isHoliday(date)).toBe(true);
    });

    test('should identify TRNC-specific Islamic holidays', () => {
        const mevlidKandili = new Date('2024-09-15');
        expect(validator.isHoliday(mevlidKandili)).toBe(true);
    });
});
