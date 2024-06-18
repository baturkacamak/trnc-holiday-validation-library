using System;
using Xunit;

namespace HolidayChecker.Tests
{
    public class TurkeyHolidayValidatorTests
    {
        private TurkeyHolidayValidator validator;

        public TurkeyHolidayValidatorTests()
        {
            validator = new TurkeyHolidayValidator(false);
        }

        [Fact]
        public void TestTurkeyFixedHolidays()
        {
            Assert.True(validator.IsHoliday(new DateTime(2024, 4, 23)));
        }
    }
}
