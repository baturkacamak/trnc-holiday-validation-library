using System;
using System.Collections.Generic;
using System.Linq;

namespace HolidayChecker
{
    /// <summary>
    /// HolidayValidator
    ///
    /// This base class is used to determine if a given date is a holiday.
    /// It supports fixed holidays, dynamic Islamic holidays, weekends, and custom holidays.
    /// </summary>
    public abstract class HolidayValidator
    {
        protected static readonly List<string> FixedHolidays = new List<string>
        {
            "01-01", // Yeni Yıl
            "04-23", // Ulusal Egemenlik ve Çocuk Bayramı
            "05-01", // İşçi Bayramı
            "05-19", // Atatürk'ü Anma, Gençlik ve Spor Bayramı
            "07-20", // Barış ve Özgürlük Bayramı
            "08-30", // Zafer Bayramı
            "10-29"  // Cumhuriyet Bayramı
        };

        protected static readonly List<Holiday> BaseDates = new List<Holiday>
        {
            new Holiday("ramazanBayrami", new DateTime(2024, 4, 10), 3),
            new Holiday("kurbanBayrami", new DateTime(2024, 6, 28), 4)
        };

        private const double IslamicYearDays = 354.36667;
        private const int BaseYear = 2024;

        protected bool IncludeSaturday;
        protected readonly List<CustomHoliday> CustomHolidays = new List<CustomHoliday>();

        /// <summary>
        /// Constructor for the HolidayValidator class
        /// </summary>
        /// <param name="includeSaturday">Boolean flag indicating whether to include Saturdays as holidays</param>
        protected HolidayValidator(bool includeSaturday)
        {
            IncludeSaturday = includeSaturday;
        }

        /// <summary>
        /// Check if the given date is a holiday
        /// </summary>
        /// <param name="date">The date to check</param>
        /// <returns>True if the date is a holiday, false otherwise</returns>
        public bool IsHoliday(DateTime date)
        {
            return IsWeekend(date) || IsFixedHoliday(date) || IsIslamicHoliday(date) || IsCustomHoliday(date);
        }

        /// <summary>
        /// Add a custom holiday
        /// </summary>
        /// <param name="date">The date of the custom holiday</param>
        /// <param name="recurring">Whether the holiday recurs every year</param>
        /// <param name="isIslamic">Whether the holiday should be considered as an Islamic holiday</param>
        public void AddCustomHoliday(DateTime date, bool recurring = false, bool isIslamic = false)
        {
            CustomHolidays.Add(new CustomHoliday(date, recurring, isIslamic));
        }

        /// <summary>
        /// Check if the given date is a weekend
        /// </summary>
        /// <param name="date">The date to check</param>
        /// <returns>True if the date is a weekend, false otherwise</returns>
        private bool IsWeekend(DateTime date)
        {
            var dayOfWeek = (int)date.DayOfWeek;
            return dayOfWeek == 0 || (IncludeSaturday && dayOfWeek == 6);
        }

        /// <summary>
        /// Check if the given date is a fixed holiday
        /// </summary>
        /// <param name="date">The date to check</param>
        /// <returns>True if the date is a fixed holiday, false otherwise</returns>
        protected virtual bool IsFixedHoliday(DateTime date)
        {
            return FixedHolidays.Contains(date.ToString("MM-dd"));
        }

        /// <summary>
        /// Check if the given date is an Islamic holiday
        /// </summary>
        /// <param name="date">The date to check</param>
        /// <returns>True if the date is an Islamic holiday, false otherwise</returns>
        private bool IsIslamicHoliday(DateTime date)
        {
            var islamicHolidays = CalculateIslamicHolidays(date.Year);
            return islamicHolidays.Contains(date);
        }

        /// <summary>
        /// Check if the given date is a custom holiday
        /// </summary>
        /// <param name="date">The date to check</param>
        /// <returns>True if the date is a custom holiday, false otherwise</returns>
        private bool IsCustomHoliday(DateTime date)
        {
            return CustomHolidays.Any(holiday =>
            {
                if (holiday.Recurring)
                {
                    return date.ToString("MM-dd") == holiday.Date.ToString("MM-dd");
                }
                return date.Date == holiday.Date.Date;
            });
        }

        /// <summary>
        /// Calculate Islamic holidays for a given year
        /// </summary>
        /// <param name="year">The year to calculate the holidays for</param>
        /// <returns>A list of Islamic holidays</returns>
        private List<DateTime> CalculateIslamicHolidays(int year)
        {
            var yearDifference = year - BaseYear;
            var holidays = new List<DateTime>();

            foreach (var baseDate in BaseDates)
            {
                var startDate = CalculateIslamicDate(baseDate.Date, yearDifference);
                holidays.AddRange(GenerateHolidayDates(startDate, baseDate.Days));
            }

            return holidays;
        }

        /// <summary>
        /// Calculate the date of an Islamic holiday
        /// </summary>
        /// <param name="baseDate">The base date of the holiday</param>
        /// <param name="yearDifference">The difference in years from the base year</param>
        /// <returns>The calculated date of the holiday</returns>
        private DateTime CalculateIslamicDate(DateTime baseDate, int yearDifference)
        {
            return baseDate.AddDays(Math.Round(yearDifference * IslamicYearDays));
        }

        /// <summary>
        /// Generate a list of holiday dates
        /// </summary>
        /// <param name="startDate">The start date of the holiday</param>
        /// <param name="days">The number of days the holiday lasts</param>
        /// <returns>A list of holiday dates</returns>
        private IEnumerable<DateTime> GenerateHolidayDates(DateTime startDate, int days)
        {
            return Enumerable.Range(0, days).Select(i => startDate.AddDays(i));
        }

        /// <summary>
        /// Holiday class representing a holiday with a name, date, and duration
        /// </summary>
        protected class Holiday
        {
            public string Name { get; }
            public DateTime Date { get; }
            public int Days { get; }

            /// <summary>
            /// Constructor for the Holiday class
            /// </summary>
            /// <param name="name">The name of the holiday</param>
            /// <param name="date">The date of the holiday</param>
            /// <param name="days">The number of days the holiday lasts</param>
            public Holiday(string name, DateTime date, int days)
            {
                Name = name;
                Date = date;
                Days = days;
            }
        }

        /// <summary>
        /// CustomHoliday class representing a custom holiday with a date, recurrence flag, and Islamic flag
        /// </summary>
        protected class CustomHoliday
        {
            public DateTime Date { get; }
            public bool Recurring { get; }
            public bool IsIslamic { get; }

            /// <summary>
            /// Constructor for the CustomHoliday class
            /// </summary>
            /// <param name="date">The date of the custom holiday</param>
            /// <param name="recurring">Whether the holiday recurs every year</param>
            /// <param name="isIslamic">Whether the holiday is considered an Islamic holiday</param>
            public CustomHoliday(DateTime date, bool recurring, bool isIslamic)
            {
                Date = date;
                Recurring = recurring;
                IsIslamic = isIslamic;
            }
        }
    }
}
