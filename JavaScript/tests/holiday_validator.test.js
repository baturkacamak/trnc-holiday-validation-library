const HolidayValidator = require('../holiday_validator');

describe('HolidayValidator', () => {
    let validator;

    beforeEach(() => {
        validator = new HolidayValidator();
    });

    test('should identify fixed holidays', () => {
        const date = new Date('2024-04-23');
        expect(validator.isHoliday(date)).toBe(true);
    });

    test('should identify weekends', () => {
        const sunday = new Date('2024-04-28');
        expect(validator.isHoliday(sunday)).toBe(true);

        validator = new HolidayValidator(true);
        const saturday = new Date('2024-04-27');
        expect(validator.isHoliday(saturday)).toBe(true);
    });

    test('should identify custom holidays', () => {
        const customDate = new Date('2024-12-25');
        validator.addCustomHoliday(customDate, true, false);
        expect(validator.isHoliday(customDate)).toBe(true);
    });
});
