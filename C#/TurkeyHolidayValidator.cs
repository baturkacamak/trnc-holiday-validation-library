using System;
using HolidayChecker;

namespace HolidayChecker.Turkey
{
    /// <summary>
    /// TurkeyHolidayValidator
    ///
    /// This class is used to determine if a given date is a holiday in Turkey.
    /// </summary>
    public class TurkeyHolidayValidator : HolidayValidator
    {
        public TurkeyHolidayValidator(bool includeSaturday) : base(includeSaturday)
        {
        }
    }
}
