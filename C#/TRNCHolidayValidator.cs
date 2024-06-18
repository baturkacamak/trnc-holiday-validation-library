using System;
using System.Collections.Generic;
using HolidayChecker;

namespace HolidayChecker.TRNC
{
    /// <summary>
    /// TRNCHolidayValidator
    ///
    /// This class is used to determine if a given date is a holiday in TRNC.
    /// </summary>
    public class TRNCHolidayValidator : HolidayValidator
    {
        private static readonly List<string> TrncFixedHolidays = new List<string>
        {
            "08-01", // TMT Günü
            "11-15"  // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
        };

        private static readonly List<Holiday> TrncBaseDates = new List<Holiday>
        {
            new Holiday("mevlidKandili", new DateTime(2024, 9, 15), 1)
        };

        /// <summary>
        /// Constructor for TRNCHolidayValidator
        /// </summary>
        /// <param name="includeSaturday">Boolean flag indicating whether to include Saturdays as holidays</param>
        public TRNCHolidayValidator(bool includeSaturday) : base(includeSaturday)
        {
        }

        /// <summary>
        /// Check if the given date is a fixed holiday in TRNC
        /// </summary>
        /// <param name="date">The date to check</param>
        /// <returns>True if the date is a fixed holiday in TRNC, false otherwise</returns>
        protected override bool IsFixedHoliday(DateTime date)
        {
            return base.IsFixedHoliday(date) || TrncFixedHolidays.Contains(date.ToString("MM-dd"));
        }

        /// <summary>
        /// Get the base dates for Islamic holidays in TRNC
        /// </summary>
        /// <returns>A list of base dates for Islamic holidays in TRNC</returns>
        protected override List<Holiday> GetBaseDates()
        {
            var allBaseDates = new List<Holiday>(base.GetBaseDates());
            allBaseDates.AddRange(TrncBaseDates);
            return allBaseDates;
        }
    }
}