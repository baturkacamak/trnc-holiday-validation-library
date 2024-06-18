import { HolidayValidator } from './HolidayValidator';

/**
 * TRNCHolidayValidator
 *
 * This class is used to determine if a given date is a holiday in TRNC.
 * It extends the HolidayValidator class to include additional fixed holidays
 * and Islamic holidays specific to TRNC.
 */
export class TRNCHolidayValidator extends HolidayValidator {
  protected static fixedHolidays = [
    ...HolidayValidator.fixedHolidays,
    '08-01', // TMT Günü
    '11-15', // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
  ];

  protected static baseDates = [
    ...HolidayValidator.baseDates,
    { name: 'mevlidKandili', date: new Date('2024-09-15'), days: 1 },
  ];

  /**
   * Constructor
   *
   * @param includeSaturday - Include Saturdays as holidays
   */
  constructor(includeSaturday: boolean = false) {
    super(includeSaturday);
  }
}
