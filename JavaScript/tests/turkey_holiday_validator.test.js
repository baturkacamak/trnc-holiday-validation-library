const TurkeyHolidayValidator = require('../turkey_holiday_validator');

describe('TurkeyHolidayValidator', () => {
    let validator;

    beforeEach(() => {
        validator = new TurkeyHolidayValidator();
    });

    test('should identify Turkey-specific fixed holidays', () => {
        const date = new Date('2024-04-23');
        expect(validator.isHoliday(date)).toBe(true);
    });
});
