class TRNCHolidayValidator {
    static FIXED_HOLIDAYS = [
        '01-01', // Yeni Yıl
        '04-23', // Ulusal Egemenlik ve Çocuk Bayramı
        '05-01', // İşçi Bayramı
        '05-19', // Atatürk'ü Anma, Gençlik ve Spor Bayramı
        '07-20', // Barış ve Özgürlük Bayramı
        '08-01', // TMT Günü
        '08-30', // Zafer Bayramı
        '10-29', // Cumhuriyet Bayramı
        '11-15', // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
    ];

    static BASE_DATES = [
        { name: 'ramazanBayrami', date: new Date('2024-04-10'), days: 3 },
        { name: 'kurbanBayrami', date: new Date('2024-06-28'), days: 4 },
        { name: 'mevlidKandili', date: new Date('2024-09-15'), days: 1 }
    ];

    static ISLAMIC_YEAR_DAYS = 354.36667;
    static BASE_YEAR = 2024;

    constructor(includeSaturday = false) {
        this.includeSaturday = includeSaturday;
        this.customHolidays = [];
    }

    isHoliday(date) {
        return this.isWeekend(date) || this.isFixedHoliday(date) || this.isIslamicHoliday(date) || this.isCustomHoliday(date);
    }

    addCustomHoliday(date, recurring = false, isIslamic = false) {
        this.customHolidays.push({ date, recurring, isIslamic });
    }

    isWeekend(date) {
        const dayOfWeek = date.getDay();
        return dayOfWeek === 0 || (this.includeSaturday && dayOfWeek === 6);
    }

    isFixedHoliday(date) {
        const monthDay = date.toISOString().substring(5, 10);
        return TRNCHolidayValidator.FIXED_HOLIDAYS.includes(monthDay);
    }

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

    isCustomHoliday(date) {
        return this.customHolidays.some(holiday => {
            if (holiday.recurring) {
                if (date.toISOString().substring(5, 10) === holiday.date.toISOString().substring(5, 10)) {
                    return true;
                }
            } else {
                if (date.toISOString().substring(0, 10) === holiday.date.toISOString().substring(0, 10)) {
                    return true;
                }
            }

            // Check if the custom holiday is an Islamic holiday
            if (holiday.isIslamic) {
                if (this.isIslamicHoliday(holiday.date)) {
                    return true;
                }
            }
        });
    }

    calculateIslamicHolidays(year) {
        const yearDifference = year - TRNCHolidayValidator.BASE_YEAR;

        const holidays = {};

        TRNCHolidayValidator.BASE_DATES.forEach(({ name, date, days }) => {
            const startDate = this.calculateIslamicDate(date, yearDifference);
            holidays[name] = this.generateHolidayDates(startDate, days);
        });

        return holidays;
    }

    calculateIslamicDate(baseDate, yearDifference) {
        const date = new Date(baseDate);
        date.setDate(date.getDate() + Math.round(yearDifference * TRNCHolidayValidator.ISLAMIC_YEAR_DAYS));
        return date;
    }

    generateHolidayDates(startDate, days) {
        return Array.from({ length: days }, (_, i) => {
            const date = new Date(startDate);
            date.setDate(startDate.getDate() + i);
            return date;
        });
    }
}
