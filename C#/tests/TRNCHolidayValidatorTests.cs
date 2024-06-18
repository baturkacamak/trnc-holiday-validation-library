using System;
using Xunit;

namespace HolidayChecker.Tests
{
    public class TRNCHolidayValidatorTests
    {
        private TRNCHolidayValidator validator;

        public TRNCHolidayValidatorTests()
        {
            validator = new TRNCHolidayValidator(false);
        }

        [Fact]
        public void TestTRNCFixedHolidays()
        {
            Assert.True(validator.IsHoliday(new DateTime(2024, 8, 1)));
        }

        [Fact]
        public void TestTRNCIslamicHolidays()
        {
            Assert.True(validator.IsHoliday(new DateTime(2024, 9, 15)));
        }
    }
}
