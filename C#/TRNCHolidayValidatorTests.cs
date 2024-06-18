[TestFixture]
public class TRNCHolidayValidatorTests
{
    [Test]
    public void TestFixedHoliday()
    {
        var validator = new TRNCHolidayValidator(false);
        var date = new DateTime(2024, 1, 1);
        Assert.IsTrue(validator.IsHoliday(date));
    }

    [Test]
    public void TestWeekendHoliday()
    {
        var validator = new TRNCHolidayValidator(false);
        var date = new DateTime(2024, 7, 7); // Sunday
        Assert.IsTrue(validator.IsHoliday(date));
    }

    [Test]
    public void TestCustomHoliday()
    {
        var validator = new TRNCHolidayValidator(false);
        var date = new DateTime(2024, 12, 25);
        validator.AddCustomHoliday(date);
        Assert.IsTrue(validator.IsHoliday(date));
    }

    [Test]
    public void TestRecurringCustomHoliday()
    {
        var validator = new TRNCHolidayValidator(false);
        var date = new DateTime(2024, 12, 25);
        validator.AddCustomHoliday(date, true);
        var dateNextYear = new DateTime(2025, 12, 25);
        Assert.IsTrue(validator.IsHoliday(dateNextYear));
    }

    [Test]
    public void TestIslamicHoliday()
    {
        var validator = new TRNCHolidayValidator(false);
        var date = new DateTime(2024, 4, 10); // Ramazan Bayramı
        Assert.IsTrue(validator.IsHoliday(date));
    }
}