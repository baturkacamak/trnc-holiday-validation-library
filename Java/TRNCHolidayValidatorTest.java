import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TRNCHolidayValidatorTest {

    @Test
    public void testFixedHoliday() {
        TRNCHolidayValidator validator = new TRNCHolidayValidator(false);
        LocalDate date = LocalDate.of(2024, 1, 1);
        assertTrue(validator.isHoliday(date));
    }

    @Test
    public void testWeekendHoliday() {
        TRNCHolidayValidator validator = new TRNCHolidayValidator(false);
        LocalDate date = LocalDate.of(2024, 7, 7); // Sunday
        assertTrue(validator.isHoliday(date));
    }

    @Test
    public void testCustomHoliday() {
        TRNCHolidayValidator validator = new TRNCHolidayValidator(false);
        LocalDate date = LocalDate.of(2024, 12, 25);
        validator.addCustomHoliday(date);
        assertTrue(validator.isHoliday(date));
    }

    @Test
    public void testRecurringCustomHoliday() {
        TRNCHolidayValidator validator = new TRNCHolidayValidator(false);
        LocalDate date = LocalDate.of(2024, 12, 25);
        validator.addCustomHoliday(date, true, false);
        LocalDate dateNextYear = LocalDate.of(2025, 12, 25);
        assertTrue(validator.isHoliday(dateNextYear));
    }

    @Test
    public void testIslamicHoliday() {
        TRNCHolidayValidator validator = new TRNCHolidayValidator(false);
        LocalDate date = LocalDate.of(2024, 4, 10); // Ramazan Bayramı
        assertTrue(validator.isHoliday(date));
    }
}