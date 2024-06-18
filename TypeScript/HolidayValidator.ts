/**
 * Interface representing a holiday.
 */
interface Holiday {
  name: string;
  date: Date;
  days: number;
}

/**
 * Interface representing a custom holiday.
 */
interface CustomHoliday {
  date: Date;
  recurring: boolean;
  isIslamic: boolean;
}

/**
 * HolidayValidator
 *
 * This base class is used to determine if a given date is a holiday.
 * It supports fixed holidays, dynamic Islamic holidays, weekends, and custom holidays.
 */
export abstract class HolidayValidator {
  protected static fixedHolidays: string[] = [
    '01-01', // Yeni Yıl
    '04-23', // Ulusal Egemenlik ve Çocuk Bayramı
    '05-01', // İşçi Bayramı
    '05-19', // Atatürk'ü Anma, Gençlik ve Spor Bayramı
    '07-20', // Barış ve Özgürlük Bayramı
    '08-30', // Zafer Bayramı
    '10-29', // Cumhuriyet Bayramı
  ];

  protected static baseDates: Holiday[] = [
    { name: 'ramazanBayrami', date: new Date('2024-04-10'), days: 3 },
    { name: 'kurbanBayrami', date: new Date('2024-06-28'), days: 4 },
  ];

  private static islamicYearDays: number = 354.36667;
  private static baseYear: number = 2024;

  protected includeSaturday: boolean;
  protected customHolidays: CustomHoliday[] = [];

  /**
   * Constructor
   *
   * @param includeSaturday - Include Saturdays as holidays
   */
  constructor(includeSaturday: boolean = false) {
    this.includeSaturday = includeSaturday;
  }

  /**
   * Check if the given date is a holiday.
   *
   * @param date - The date to check
   * @returns True if the date is a holiday, false otherwise
   */
  public isHoliday(date: Date): boolean {
    return this.isWeekend(date) || this.isFixedHoliday(date) || this.isIslamicHoliday(date) || this.isCustomHoliday(date);
  }

  /**
   * Add a custom holiday.
   *
   * @param date - The date of the custom holiday
   * @param recurring - Whether the holiday recurs every year
   * @param isIslamic - Whether the holiday should be considered as an Islamic holiday
   */
  public addCustomHoliday(date: Date, recurring: boolean = false, isIslamic: boolean = false): void {
    this.customHolidays.push({ date, recurring, isIslamic });
  }

  /**
   * Check if the given date is a weekend.
   *
   * @param date - The date to check
   * @returns True if the date is a weekend, false otherwise
   */
  private isWeekend(date: Date): boolean {
    const dayOfWeek = date.getDay();
    return dayOfWeek === 0 || (this.includeSaturday && dayOfWeek === 6);
  }

  /**
   * Check if the given date is a fixed holiday.
   *
   * @param date - The date to check
   * @returns True if the date is a fixed holiday, false otherwise
   */
  protected isFixedHoliday(date: Date): boolean {
    const monthDay = date.toISOString().slice(5, 10);
    return HolidayValidator.fixedHolidays.includes(monthDay);
  }

  /**
   * Check if the given date is an Islamic holiday.
   *
   * @param date - The date to check
   * @returns True if the date is an Islamic holiday, false otherwise
   */
  private isIslamicHoliday(date: Date): boolean {
    const islamicHolidays = this.calculateIslamicHolidays(date.getFullYear());
    return islamicHolidays.some(holidayDates => holidayDates.some(holidayDate => date.toISOString().slice(0, 10) === holidayDate.toISOString().slice(0, 10)));
  }

  /**
   * Check if the given date is a custom holiday.
   *
   * @param date - The date to check
   * @returns True if the date is a custom holiday, false otherwise
   */
  private isCustomHoliday(date: Date): boolean {
    return this.customHolidays.some(holiday => {
      if (holiday.recurring) {
        return date.toISOString().slice(5, 10) === holiday.date.toISOString().slice(5, 10);
      } else {
        return date.toISOString().slice(0, 10) === holiday.date.toISOString().slice(0, 10);
      }
    });
  }

  /**
   * Calculate Islamic holidays for a given year.
   *
   * @param year - The year to calculate the holidays for
   * @returns An array of arrays of Islamic holidays
   */
  private calculateIslamicHolidays(year: number): Date[][] {
    const yearDifference = year - HolidayValidator.baseYear;
    const holidays: Date[][] = [];

    for (const baseDate of HolidayValidator.baseDates) {
      const startDate = this.calculateIslamicDate(baseDate.date, yearDifference);
      holidays.push(this.generateHolidayDates(startDate, baseDate.days));
    }

    return holidays;
  }

  /**
   * Calculate the date of an Islamic holiday.
   *
   * @param baseDate - The base date of the holiday
   * @param yearDifference - The difference in years from the base year
   * @returns The calculated date of the holiday
   */
  private calculateIslamicDate(baseDate: Date, yearDifference: number): Date {
    const date = new Date(baseDate);
    date.setDate(date.getDate() + Math.round(yearDifference * HolidayValidator.islamicYearDays));
    return date;
  }

  /**
   * Generate an array of holiday dates.
   *
   * @param startDate - The start date of the holiday
   * @param days - The number of days the holiday lasts
   * @returns An array of holiday dates
   */
  private generateHolidayDates(startDate: Date, days: number): Date[] {
    return Array.from({ length: days }, (_, i) => {
      const date = new Date(startDate);
      date.setDate(startDate.getDate() + i);
      return date;
    });
  }
}
