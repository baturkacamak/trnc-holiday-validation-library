using System;
using Xunit;

namespace HolidayChecker.Tests
{
    public class HolidayValidatorTests
    {
        private HolidayValidator validator;

        public HolidayValidatorTests()
        {
            validator = new HolidayValidator(false) {};
        }

        [Fact]
        public void TestFixedHolidays()
        {
            Assert.True(validator.IsHoliday(new DateTime(2024, 4, 23)));
        }

        [Fact]
        public void TestWeekends()
        {
            Assert.True(validator.IsHoliday(new DateTime(2024, 4, 28)));

            validator = new HolidayValidator(true) {};
            Assert.True(validator.IsHoliday(new DateTime(2024, 4, 27)));
        }

        [Fact]
        public void TestCustomHolidays()
        {
            var customDate = new DateTime(2024, 12, 25);
            validator.AddCustomHoliday(customDate, true, false);
            Assert.True(validator.IsHoliday(customDate));
        }
    }
}
