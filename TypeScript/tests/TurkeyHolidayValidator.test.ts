import { TurkeyHolidayValidator } from '../TurkeyHolidayValidator';

describe('TurkeyHolidayValidator', () => {
  let validator: TurkeyHolidayValidator;

  beforeEach(() => {
    validator = new TurkeyHolidayValidator(false);
  });

  it('should recognize Turkey fixed holidays', () => {
    expect(validator.isHoliday(new Date('2024-04-23'))).toBe(true);
  });

  it('should recognize Turkey Islamic holidays', () => {
    expect(validator.isHoliday(new Date('2024-04-10'))).toBe(true);
  });
});
