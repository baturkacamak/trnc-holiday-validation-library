import { HolidayValidator } from '../HolidayValidator';

describe('HolidayValidator', () => {
  let validator: HolidayValidator;

  beforeEach(() => {
    validator = new HolidayValidator(false);
  });

  it('should recognize fixed holidays', () => {
    expect(validator.isHoliday(new Date('2024-04-23'))).toBe(true);
  });

  it('should recognize Islamic holidays', () => {
    expect(validator.isHoliday(new Date('2024-04-10'))).toBe(true);
  });
});
