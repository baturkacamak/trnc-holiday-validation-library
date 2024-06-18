const assert = require('assert');

describe('TRNCHolidayValidator', function() {
    it('should recognize fixed holidays', function() {
        const validator = new TRNCHolidayValidator();
        const date = new Date('2024-01-01');
        assert.strictEqual(validator.isHoliday(date), true);
    });

    it('should recognize weekends', function() {
        const validator = new TRNCHolidayValidator();
        const date = new Date('2024-07-07'); // Sunday
        assert.strictEqual(validator.isHoliday(date), true);
    });

    it('should recognize custom holidays', function() {
        const validator = new TRNCHolidayValidator();
        const date = new Date('2024-12-25');
        validator.addCustomHoliday(date);
        assert.strictEqual(validator.isHoliday(date), true);
    });

    it('should recognize recurring custom holidays', function() {
        const validator = new TRNCHolidayValidator();
        const date = new Date('2024-12-25');
        validator.addCustomHoliday(date, true);
        const dateNextYear = new Date('2025-12-25');
        assert.strictEqual(validator.isHoliday(dateNextYear), true);
    });

    it('should recognize Islamic holidays', function() {
        const validator = new TRNCHolidayValidator();
        const date = new Date('2024-04-10'); // Ramazan Bayramı
        assert.strictEqual(validator.isHoliday(date), true);
    });
});
