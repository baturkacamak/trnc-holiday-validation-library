import { TRNCHolidayValidator } from '../TRNCHolidayValidator';

describe('TRNCHolidayValidator', () => {
  let validator: TRNCHolidayValidator;

  beforeEach(() => {
    validator = new TRNCHolidayValidator(false);
  });

  it('should recognize TRNC fixed holidays', () => {
    expect(validator.isHoliday(new Date('2024-08-01'))).toBe(true);
  });

  it('should recognize TRNC Islamic holidays', () => {
    expect(validator.isHoliday(new Date('2024-09-15'))).toBe(true);
  });
});
