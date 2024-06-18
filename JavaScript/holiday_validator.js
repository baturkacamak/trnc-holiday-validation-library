/**
 * HolidayValidator
 *
 * This base class is used to determine if a given date is a holiday.
 * It supports fixed holidays, dynamic Islamic holidays, weekends, and custom holidays.
 */
class HolidayValidator {
    static FIXED_HOLIDAYS = [
        '01-01', // Yeni Yıl
        '04-23', // Ulusal Egemenlik ve Çocuk Bayramı
        '05-01', // İşçi Bayramı
        '05-19', // Atatürk'ü Anma, Gençlik ve Spor Bayramı
        '07-20', // Barış ve Özgürlük Bayramı
        '08-30', // Zafer Bayramı
        '10-29', // Cumhuriyet Bayramı
    ];

    static BASE_DATES = [
        { name: 'ramazanBayrami', date: new Date('2024-04-10'), days: 3 },
        { name: 'kurbanBayrami', date: new Date('2024-06-28'), days: 4 },
    ];

    static ISLAMIC_YEAR_DAYS = 354.36667;
    static BASE_YEAR = 2024;

    constructor(includeSaturday = false) {
        this.includeSaturday = includeSaturday;
        this.customHolidays = [];
    }

    /**
     * Check if the given date is a holiday
     * @param {Date} date The date to check
     * @returns {boolean} True if the date is a holiday, false otherwise
     */
    isHoliday(date) {
        return this.isWeekend(date) || this.isFixedHoliday(date) || this.isIslamicHoliday(date) || this.isCustomHoliday(date);
    }

    /**
     * Add a custom holiday
     * @param {Date} date The date of the custom holiday
     * @param {boolean} [recurring=false] Whether the holiday recurs every year
     * @param {boolean} [isIslamic=false] Whether the holiday should be considered as an Islamic holiday
     */
    addCustomHoliday(date, recurring = false, isIslamic = false) {
        this.customHolidays.push({ date, recurring, isIslamic });
    }

    /**
     * Check if the given date is a weekend
     * @param {Date} date The date to check
     * @returns {boolean} True if the date is a weekend, false otherwise
     */
    isWeekend(date) {
        const dayOfWeek = date.getDay();
        return dayOfWeek === 0 || (this.includeSaturday && dayOfWeek === 6);
    }

    /**
     * Check if the given date is a fixed holiday
     * @param {Date} date The date to check
     * @returns {boolean} True if the date is a fixed holiday, false otherwise
     */
    isFixedHoliday(date) {
        const monthDay = date.toISOString().substring(5, 10);
        return HolidayValidator.FIXED_HOLIDAYS.includes(monthDay);
    }

    /**
     * Check if the given date is an Islamic holiday
     * @param {Date} date The date to check
     * @returns {boolean} True if the date is an Islamic holiday, false otherwise
     */
    isIslamicHoliday(date) {
        const year = date.getFullYear();
        const islamicHolidays = this.calculateIslamicHolidays(year);
        for (const holidays of Object.values(islamicHolidays)) {
            for (const holiday of holidays) {
                if (date.toISOString().substring(0, 10) === holiday.toISOString().substring(0, 10)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the given date is a custom holiday
     * @param {Date} date The date to check
     * @returns {boolean} True if the date is a custom holiday, false otherwise
     */
    isCustomHoliday(date) {
        return this.customHolidays.some(holiday => {
            if (holiday.recurring) {
                return date.toISOString().substring(5, 10) === holiday.date.toISOString().substring(5, 10);
            }
            return date.toISOString().substring(0, 10) === holiday.date.toISOString().substring(0, 10);
        });
    }

    /**
     * Calculate Islamic holidays for a given year
     * @param {number} year The year to calculate the holidays for
     * @returns {Object} An object containing arrays of Islamic holiday dates
     */
    calculateIslamicHolidays(year) {
        const yearDifference = year - HolidayValidator.BASE_YEAR;

        const holidays = {};

        HolidayValidator.BASE_DATES.forEach(({ name, date, days }) => {
            const startDate = this.calculateIslamicDate(date, yearDifference);
            holidays[name] = this.generateHolidayDates(startDate, days);
        });

        return holidays;
    }

    /**
     * Calculate the date of an Islamic holiday
     * @param {Date} baseDate The base date of the holiday
     * @param {number} yearDifference The difference in years from the base year
     * @returns {Date} The calculated date of the holiday
     */
    calculateIslamicDate(baseDate, yearDifference) {
        const date = new Date(baseDate);
        date.setDate(date.getDate() + Math.round(yearDifference * HolidayValidator.ISLAMIC_YEAR_DAYS));
        return date;
    }

    /**
     * Generate an array of holiday dates
     * @param {Date} startDate The start date of the holiday
     * @param {number} days The number of days the holiday lasts
     * @returns {Date[]} An array of holiday dates
     */
    generateHolidayDates(startDate, days) {
        return Array.from({ length: days }, (_, i) => {
            const date = new Date(startDate);
            date.setDate(startDate.getDate() + i);
            return date;
        });
    }
}

module.exports = HolidayValidator;
